package com.example.controllers.wrappers;

import com.example.models.Card;
import lombok.Data;

import java.util.List;

/**
 * Created by RyanZhu on 4/7/16.
 */
@Data
public class DeckWithCards {
    private Integer id;
    private String desc;
    private List<Card> cards;
}
