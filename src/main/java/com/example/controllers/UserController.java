package com.example.controllers;

import com.example.contants.SystemConstants;
import com.example.controllers.wrappers.CollectionRes;
import com.example.services.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by RyanZhu on 4/7/16.
 */
@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    DeckService deckService;

    @RequestMapping(value = "{username}/decks", method = RequestMethod.GET)
    public CollectionRes getDecksByUserName(@PathVariable("username") String username,
                                            @RequestParam(value = "nextPageToken", required = false) String nextPageToken) {
        return deckService.getDecksByUserName(username, nextPageToken);
    }

    @RequestMapping(value = "{username}/decksdetail", method = RequestMethod.GET)
    public CollectionRes getDecksDetailByUserName(@PathVariable("username") String username,
                                                  @RequestParam(value = "nextPageToken", required = false) String nextPageToken,
                                                  @RequestParam(value = "optimize", defaultValue = SystemConstants.OPTIMIZED, required = false) String optimized) {
        return deckService.getDecksDetailByUserName(username, nextPageToken, optimized);
    }

}
