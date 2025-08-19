package aitkenably.aurelius.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_seq")
    @SequenceGenerator(name = "card_seq", sequenceName = "card_id_seq", allocationSize = 1)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "deck_id", nullable = false)
    private Deck deck;

    private String question;
    private String answer;
    private int numReviews;
    private int numReviewsCorrect;
    private LocalDate nextReview;
    private int repetitionNumber;
    private int interval;
    private double easiness;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(int numReviews) {
        this.numReviews = numReviews;
    }

    public int getNumReviewsCorrect() {
        return numReviewsCorrect;
    }

    public void setNumReviewsCorrect(int numReviewsCorrect) {
        this.numReviewsCorrect = numReviewsCorrect;
    }

    public LocalDate getNextReview() {
        return nextReview;
    }

    public void setNextReview(LocalDate nextReview) {
        this.nextReview = nextReview;
    }

    public int getRepetitionNumber() {
        return repetitionNumber;
    }

    public void setRepetitionNumber(int repetitionNumber) {
        this.repetitionNumber = repetitionNumber;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public double getEasiness() {
        return easiness;
    }

    public void setEasiness(double easiness) {
        this.easiness = easiness;
    }

}
