package com.example.controllers.wrappers;

import com.example.models.Deck;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by RyanZhu on 4/7/16.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DecksCollection extends CollectionRes {
    private List<Deck> decks;
}
