package com.example.caches;

import com.example.models.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by RyanZhu on 4/7/16.
 */
@Service
public class CacheService {
    @Autowired
    DeckCardCache deckCardCache;

    public void addDeckCard(Integer deckId, Card card){
        deckCardCache.addDeckCard(deckId,card);
    }

    public List<Card> fetchCardByDeckId(Integer deckId){
        return deckCardCache.fetchCardsByDeckId(deckId);
    }
}
