package aitkenably.aurelius.domain;

import jakarta.persistence.*;

import java.util.Set;

// TODO: Add null and unique constraints on title (update DB)

@Entity
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deck_seq")
    @SequenceGenerator(name = "deck_seq", sequenceName = "deck_id_seq", allocationSize = 1)
    @Column(nullable = false)
    private Long id;
    private String title;

    @OneToMany(mappedBy = "deck")
    private Set<Card> cards;

    public Deck(Long id) {
        this.id = id;
    }

    public Deck() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }
}
