package aitkenably.aurelius.controllers;

import aitkenably.aurelius.domain.CardRepository;
import aitkenably.aurelius.domain.Deck;
import aitkenably.aurelius.domain.DeckRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest(DeckController.class)
public class DeckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DeckRepository deckRepo;

    @MockitoBean
    private CardRepository cardRepo;

    @Test
    public void contextLoads() { }

    @Test
    public void showDeckScreen_noUser() throws Exception {
        mockMvc.perform(get("/decks/1"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "user")
    @Test
    public void showDeckScreen_invalidId() throws Exception {
        when(deckRepo.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/decks/1"))
                .andExpect(status().isNotFound());

    }

    @WithMockUser(value = "user")
    @Test
    public void showDeckScreen_validId() throws Exception {
        when(deckRepo.findById(1L)).thenReturn(Optional.of(new Deck(1L)));
        mockMvc.perform(get("/decks/1"))
                .andExpect(status().isOk());
    }
}
