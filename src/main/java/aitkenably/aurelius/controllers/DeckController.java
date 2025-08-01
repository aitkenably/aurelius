package aitkenably.aurelius.controllers;

import aitkenably.aurelius.NewDeckDTO;
import aitkenably.aurelius.UpdateCardDTO;
import aitkenably.aurelius.domain.Card;
import aitkenably.aurelius.domain.CardRepository;
import aitkenably.aurelius.domain.Deck;
import aitkenably.aurelius.domain.DeckRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

// TODO: Add card endpoints
// TODO: Is there a better way to list endpoints in the documentation?

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
    private final CardRepository cardRepo;

    DeckController(DeckRepository deckRepo, CardRepository cardRepo) {
        this.deckRepo = deckRepo;
        this.cardRepo = cardRepo;
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
        // TODO: Use orElseThrow
        var optionalDeck = deckRepo.findById(did);
        if(optionalDeck.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        var deck = optionalDeck.get();
        List<Card> cardList = cardRepo.findByDeck(deck);

        ModelAndView mav = new ModelAndView("decks/show");
        mav.addObject("deck", deck);
        mav.addObject("cards", cardList);
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
        // TODO: Use orElse
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
        // TODO: Pass UpdateDeckDTO (NewDeckDTO to use content)
        deckRepo.save(deck);
        return "redirect:/decks/" + deck.getId();
    }

    // TODO: Comment
    // TODO: Rename {id} to did
    @GetMapping("/{id}/cards/new")
    public ModelAndView newCardForm(@PathVariable("id") Long did) {
        ModelAndView mav = new ModelAndView("decks/cards/new");
        mav.addObject("deckId", did);
        return mav;
    }

    // TODO: Comment
    // TODO: Rename {id} to did
    @GetMapping("/{id}/cards/{cid}/edit")
    public ModelAndView editCardForm(@PathVariable("id") Long did, @PathVariable("cid") Long cid) {
        Card card = cardRepo.findById(cid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // TODO: Create better way of creating DTOs
        UpdateCardDTO dto = new UpdateCardDTO();
        dto.setQuestion(card.getQuestion());
        dto.setAnswer(card.getAnswer());

        ModelAndView mav = new ModelAndView("decks/cards/edit");
        mav.addObject("deckId", did);
        mav.addObject("cardId", cid);
        mav.addObject("cardDto", dto);
        return mav;

    }

    // TODO: Comment
    // TODO: Rename {id} to did
    // TODO: Accept a CardDTO instead of Card
    @PostMapping("/{id}/cards")
    public String createCard(@PathVariable("id") Long did, Card card) {
        card.setId(null);
        Deck deck = deckRepo.findById(did).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        card.setDeck(deck);

        cardRepo.save(card);
        return "redirect:/decks/" + did;
    }

    // TODO: Comment
    @DeleteMapping("/{id}/cards/{cid}")
    public String deleteCard(@PathVariable("id") Long did, @PathVariable("cid") Long cid) {
        cardRepo.deleteById(cid);
        return "redirect:/decks/" + did;
    }

    // TODO: Comment
    @PutMapping("/{id}/cards/{cid}")
    public String updateCard(@PathVariable("id") Long did, @PathVariable("cid") Long cid, UpdateCardDTO dto) {
        Card card = cardRepo.findById(cid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // TODO: Is there a better way to do this? (referencing Decks)
        if(!card.getDeck().getId().equals(did)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        card.setQuestion(dto.getQuestion());
        card.setAnswer(dto.getAnswer());

        cardRepo.save(card);

        return "redirect:/decks/" + did;
    }

}
