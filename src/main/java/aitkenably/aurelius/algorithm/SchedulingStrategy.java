package aitkenably.aurelius.algorithm;

import aitkenably.aurelius.domain.Card;

public interface SchedulingStrategy {

    void scheduleCard(Card card, int score);

}
