package aitkenably.aurelius.algorithm;

import aitkenably.aurelius.domain.Card;

import java.time.LocalDate;

import static aitkenably.aurelius.Util.jitter;
import static aitkenably.aurelius.Util.randInt;

public class ModifiedSM2 implements SchedulingStrategy {

    /*
        0: "Total blackout", complete failure to recall the information.
        1: Incorrect response, but upon seeing the correct answer it felt familiar.
        2: Incorrect response, but upon seeing the correct answer it seemed easy to remember.
        3: Correct response, but required significant effort to recall.
        4: Correct response, after some hesitation.
        5: Correct response with perfect recall.
     */


    /*
    algorithm SM-2 is
    input:  user grade q
            repetition number n
            easiness factor EF
            interval I
    output: updated values of n, EF, and I

    if q ≥ 3 (correct response) then
        if n = 0 then
            I ← 1
        else if n = 1 then
            I ← 6
        else
            I ← round(I × EF)
        end if
        increment n
    else (incorrect response)
        n ← 0
        I ← 1
    end if

    EF ← EF + (0.1 − (5 − q) × (0.08 + (5 − q) × 0.02))
    if EF < 1.3 then
        EF ← 1.3
    end if

    return (n, EF, I)
     */





    @Override
    public void scheduleCard(Card card, int score) {
        // Score should be 0 - 5
        var Sm2Properties = new Sm2Properties(card.getRepetitionNumber(), card.getEasiness(), card.getInterval());

        var results = computeCardProperties(score, Sm2Properties);
        card.setRepetitionNumber(results.repetitionNumber);
        card.setEasiness(results.easinessFactor);
        card.setInterval(results.interval);

        card.setNextReview(card.getNextReview().plusDays(results.interval));
    }

    record Sm2Properties(int repetitionNumber, double easinessFactor, int interval) {}

    Sm2Properties computeCardProperties(int score, Sm2Properties cp) {
        int newRepetitionNumber = 0;
        double newEasinessFactor = 0.0;
        int newInterval = 1;

        if(score >= 3) { // Correct response
            if(cp.repetitionNumber == 0) {
                newInterval = randInt(1, 3);
            } else if(cp.repetitionNumber == 1) {
                newInterval = randInt(5, 7);
            } else {
                newInterval = (int) Math.round(cp.interval * cp.easinessFactor);
                newInterval = jitter(newInterval, 0.05);
            }
            newRepetitionNumber = cp.repetitionNumber + 1;
        }
        // Incorrect response: repetition = 0 and interval = 1

        newEasinessFactor = cp.easinessFactor + ((0.1 - (5 - score)) * (0.08 + (5 - score) * 0.02));
        if(newEasinessFactor < 1.3) {
            newEasinessFactor = 1.3;
        }

        return new Sm2Properties(newRepetitionNumber, newEasinessFactor, newInterval);
    }

}
