package aitkenably.aurelius.controllers;

import aitkenably.aurelius.NewDeckDTO;
import aitkenably.aurelius.domain.Deck;
import aitkenably.aurelius.domain.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/decks")
public class DeckController {

    // TODO: Add comments on all methods
    // TODO: Document all endpoints

    @Autowired
    private DeckRepository deckRepo;

    /**
     * Display a list of all decks.
     * @return the view and a list of decks
     */
    @GetMapping
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("decks/index");
        mav.addObject("decks", deckRepo.findAll());
        return mav;
    }
    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable("id") Long did) {
        var optionalDeck = deckRepo.findById(did);
        if(optionalDeck.isEmpty()) {
            // TODO: Return a 404
            return new ModelAndView("redirect:/decks");
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
    public String newDeck() {
        // TODO: Rename function to something more appropriate
        return "decks/new";
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable("id") Long did) {
        var optionalDeck = deckRepo.findById(did);
        if(optionalDeck.isEmpty()) {
            // TODO: Return a 404
            return new ModelAndView("redirect:/decks");
        }

        ModelAndView mav = new ModelAndView("decks/edit");
        mav.addObject("deck", optionalDeck.get());
        return mav;
    }

    @PostMapping
    public String create(NewDeckDTO dto) {
        // TODO: Validate the data
        deckRepo.save(NewDeckDTO.toDeck(dto));
        return "redirect:/decks";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long did) {
        deckRepo.deleteById(did);
        return "redirect:/decks";
    }

    @PutMapping("/{id}")
    public String update(Deck deck) {
        // TODO: Validate the data & id match
        deckRepo.save(deck);
        return "redirect:/decks";
    }

}
