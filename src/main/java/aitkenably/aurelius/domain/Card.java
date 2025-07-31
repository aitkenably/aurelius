package aitkenably.aurelius.domain;

import jakarta.persistence.*;

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
}
