package com.example.controllers.wrappers;

import lombok.Data;

import java.util.List;

/**
 * Created by RyanZhu on 4/7/16.
 */
@Data
public class DeckWithCardCollection extends CollectionRes {
    private List<DeckWithCards> decks;
}
