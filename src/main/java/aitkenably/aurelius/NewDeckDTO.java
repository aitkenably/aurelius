package aitkenably.aurelius;

import aitkenably.aurelius.domain.Deck;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class NewDeckDTO {

    @NotBlank(message = "Title is required")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Deck toDeck() {
        var d = new Deck();
        d.setTitle(title);
        return d;
    }

}
