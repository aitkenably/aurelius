package aitkenably.aurelius.controllers;

import aitkenably.aurelius.NewDeckDTO;
import aitkenably.aurelius.domain.Deck;
import aitkenably.aurelius.domain.DeckRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for the Decks.
 * Supported endpoints:
 * GET /decks - show all decks
 * GET /decks/{id} - show a single deck
 * GET /decks/new - form to create a new deck
 * GET /decks/{id}/edit - form to edit an existing deck
 * POST /decks/create - create a new deck
 * DELETE /decks/{id}/delete - delete an existing deck
 * PUT /decks/{id}/update - update an existing deck
 */
@Controller
@RequestMapping("/decks")
public class DeckController {

    // TODO: Unit tests for all endpoints

    private final DeckRepository deckRepo;

    DeckController(DeckRepository deckRepo) {
        this.deckRepo = deckRepo;
    }

    /**
     * Display a list of all decks order by title.
     * @return the view and a list of decks
     */
    @GetMapping
    public ModelAndView showAllDecksScreen() {
        ModelAndView mav = new ModelAndView("decks/index");
        mav.addObject("decks", deckRepo.findAll(Sort.by("title")));
        return mav;
    }

    /**
     * Display a single deck. Returns 404 if the deck does not exist.
     * @param did the id of the deck
     * @return the view and the deck
     */
    @GetMapping("/{id}")
    public ModelAndView showDeckScreen(@PathVariable("id") Long did) {
        var optionalDeck = deckRepo.findById(did);
        if(optionalDeck.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        ModelAndView mav = new ModelAndView("decks/show");
        mav.addObject("deck", optionalDeck.get());
        return mav;
    }

    /**
     * Display a form to create a new deck.
     * @return the view name
     */
    @GetMapping("new")
    public String newDeckForm() {
        return "decks/new";
    }

    /**
     * Display a form to edit an existing deck.
     * Returns 404 if the deck does not exist.
     * @param did the id of the deck
     * @return the view and the deck
     */
    @GetMapping("/{id}/edit")
    public ModelAndView editDeckForm(@PathVariable("id") Long did) {
        var optionalDeck = deckRepo.findById(did);
        if(optionalDeck.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        ModelAndView mav = new ModelAndView("decks/edit");
        mav.addObject("deck", optionalDeck.get());
        return mav;
    }

    /**
     * Create a new deck.
     * @param dto DTO containing the title of the deck
     * @return Redirect to the list of decks
     */
    @PostMapping
    public String createDeck(NewDeckDTO dto) {
        // TODO: Validate the data
        deckRepo.save(NewDeckDTO.toDeck(dto));
        return "redirect:/decks";
    }

    /**
     * Delete a deck.
     * @param did Deck id to delete
     * @return Redirect to the list of decks
     */
    @DeleteMapping("/{id}")
    public String deleteDeck(@PathVariable("id") Long did) {
        deckRepo.deleteById(did);
        return "redirect:/decks";
    }

    /**
     * Update a deck.
     * @param deck Deck to update
     * @return Redirect to the deck
     */
    @PutMapping("/{id}")
    public String updateDeck(Deck deck) {
        // TODO: Validate the data & id match
        deckRepo.save(deck);
        return "redirect:/decks/" + deck.getId();
    }

}
