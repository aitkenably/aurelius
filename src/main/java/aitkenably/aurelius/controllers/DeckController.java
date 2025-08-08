package aitkenably.aurelius.controllers;

import aitkenably.aurelius.NewDeckDTO;
import aitkenably.aurelius.UpdateCardDTO;
import aitkenably.aurelius.domain.Card;
import aitkenably.aurelius.domain.CardRepository;
import aitkenably.aurelius.domain.Deck;
import aitkenably.aurelius.domain.DeckRepository;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @GetMapping("/{did}")
    public ModelAndView showDeckScreen(@PathVariable("did") Long did) {
        var deck = deckRepo.findById(did).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        ModelAndView mav = new ModelAndView("decks/show");
        mav.addObject("deck", deck);
        return mav;
    }

    /**
     * Display a form to create a new deck.
     * @return the view name
     */
    @GetMapping("new")
    public String newDeckForm(Model model) {
        if(!model.containsAttribute("newDeckDto")) {
            model.addAttribute("newDeckDto", new NewDeckDTO());
        }

        return "decks/new";
    }

    /**
     * Create a new deck.
     * @param dto DTO containing the title of the deck
     * @return Redirect to the list of decks
     */
    @PostMapping
    public String createDeck(@Valid @ModelAttribute NewDeckDTO newDeckDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newDeckDto", bindingResult);
            redirectAttributes.addFlashAttribute("newDeckDto", newDeckDto);
            return "redirect:decks/new";
        } else {
            // TODO: Fix duplicate code
            try {
                deckRepo.save(newDeckDto.toDeck());
            } catch (DataIntegrityViolationException ex) {
                bindingResult.rejectValue("title", "error.deck", "A deck with that title already exists");
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newDeckDto", bindingResult);
                redirectAttributes.addFlashAttribute("newDeckDto", newDeckDto);
                return "redirect:decks/new";
            }
            return "redirect:/decks";
        }
    }


    /**
     * Display a form to edit an existing deck.
     * Returns 404 if the deck does not exist.
     * @param did the id of the deck
     * @return the view and the deck
     */
    @GetMapping("/{did}/edit")
    public ModelAndView editDeckForm(@PathVariable("did") Long did) {
        var deck = deckRepo.findById(did).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        ModelAndView mav = new ModelAndView("decks/edit");
        mav.addObject("deck", deck);
        return mav;
    }



    /**
     * Delete a deck.
     * @param did Deck id to delete
     * @return Redirect to the list of decks
     */
    @DeleteMapping("/{did}")
    public String deleteDeck(@PathVariable("did") Long did) {
        // TODO: Handle card deletion
        deckRepo.deleteById(did);
        return "redirect:/decks";
    }

    /**
     * Update a deck.
     * @param deck Deck to update
     * @return Redirect to the deck
     */
    @PutMapping("/{did}")
    public String updateDeck(Deck deck) {
        // TODO: Validate the data & id match
        // TODO: Pass UpdateDeckDTO (NewDeckDTO to use content)
        deckRepo.save(deck);
        return "redirect:/decks/" + deck.getId();
    }

    // TODO: Comment
    @GetMapping("/{did}/cards/new")
    public ModelAndView newCardForm(@PathVariable("did") Long did) {
        ModelAndView mav = new ModelAndView("decks/cards/new");
        mav.addObject("deckId", did);
        return mav;
    }

    // TODO: Comment
    @GetMapping("/{did}/cards/{cid}/edit")
    public ModelAndView editCardForm(@PathVariable("did") Long did, @PathVariable("cid") Long cid) {
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
    // TODO: Accept a CardDTO instead of Card
    @PostMapping("/{did}/cards")
    public String createCard(@PathVariable("did") Long did, Card card) {
        card.setId(null);
        Deck deck = deckRepo.findById(did).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        card.setDeck(deck);

        cardRepo.save(card);
        return "redirect:/decks/" + did;
    }

    // TODO: Comment
    @DeleteMapping("/{did}/cards/{cid}")
    public String deleteCard(@PathVariable("did") Long did, @PathVariable("cid") Long cid) {
        cardRepo.deleteById(cid);
        return "redirect:/decks/" + did;
    }

    // TODO: Comment
    @PutMapping("/{did}/cards/{cid}")
    public String updateCard(@PathVariable("did") Long did, @PathVariable("cid") Long cid, UpdateCardDTO dto) {
        Card card = cardRepo.findById(cid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!card.getDeck().getId().equals(did)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        card.setQuestion(dto.getQuestion());
        card.setAnswer(dto.getAnswer());

        cardRepo.save(card);

        return "redirect:/decks/" + did;
    }

}
