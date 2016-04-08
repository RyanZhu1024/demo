package com.example.controllers;

import com.example.controllers.wrappers.DeckWithCards;
import com.example.services.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by RyanZhu on 4/7/16.
 */
@RestController
@RequestMapping("decks")
public class DeckController {
    @Autowired
    DeckService deckService;
    @RequestMapping("{id}")
    public DeckWithCards getDeckById(@PathVariable("id") Integer id){
        return deckService.getDeckById(id);
    }

}
