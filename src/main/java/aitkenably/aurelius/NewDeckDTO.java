package aitkenably.aurelius;

import aitkenably.aurelius.domain.Deck;

public class NewDeckDTO {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static Deck toDeck(NewDeckDTO dto) {
        var d = new Deck();
        d.setTitle(dto.title);
        return d;
    }

}
