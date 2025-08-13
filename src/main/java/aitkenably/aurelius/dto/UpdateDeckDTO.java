package aitkenably.aurelius.dto;

import aitkenably.aurelius.domain.Deck;
import jakarta.validation.constraints.NotBlank;

public class UpdateDeckDTO {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

   public UpdateDeckDTO() {}

   public UpdateDeckDTO(Deck d) {
        this.id = d.getId();
        this.title = d.getTitle();
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

    public void updateDeck(Deck d) {
        d.setTitle(title);
    }
}
