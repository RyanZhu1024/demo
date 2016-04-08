package com.example.caches;

import com.example.models.Card;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by RyanZhu on 4/7/16.
 * now the deckCardCache only supports add and read for this prototype
 */
@Component
class DeckCardCache {
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private static final Map<Integer, List<Card>> deckCardCache = new HashMap<>();

    void addDeckCard(Integer deckId, Card card) {
        try {
            lock.writeLock().lock();
            if (deckCardCache.get(deckId) != null) {
                deckCardCache.get(deckId).add(card);
            } else {
                List<Card> cards = new LinkedList<>();
                cards.add(card);
                deckCardCache.put(deckId, cards);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    List<Card> fetchCardsByDeckId(Integer deckId) {
        try {
            lock.readLock().lock();
            return deckCardCache.get(deckId);
        } finally {
            lock.readLock().unlock();
        }
    }
}
