package com.example.services;

import com.example.caches.CacheService;
import com.example.controllers.wrappers.CollectionRes;
import com.example.controllers.wrappers.DeckWithCardCollection;
import com.example.controllers.wrappers.DeckWithCards;
import com.example.controllers.wrappers.DecksCollection;
import com.example.models.Card;
import com.example.models.Deck;
import com.example.repositories.CardRepository;
import com.example.repositories.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.contants.SystemConstants.DB_MAX_TRY;
import static com.example.contants.SystemConstants.OPTIMIZED;

/**
 * Created by RyanZhu on 4/7/16.
 */
@Service
public class DeckService {

    @Autowired
    CacheService cacheService;

    @Autowired
    TokenService tokenService;

    @Autowired
    DeckRepository deckRepository;

    @Autowired
    CardRepository cardRepository;

    /**
     * suppose username always exist and is valid
     *
     * @param username
     * @param nextPageToken
     * @return
     */
    public CollectionRes getDecksByUserName(String username, String nextPageToken) {
        TokenService.PageTokenClass pagetoken = null;
        if (nextPageToken == null || nextPageToken.isEmpty()) {
            pagetoken = new TokenService.PageTokenClass(0, 10);
        } else {
            pagetoken = tokenService.parsePageToken(nextPageToken);
        }
        DecksCollection decksCollection = new DecksCollection();
        decksCollection.setDecks(deckRepository.findByUserName(username, new PageRequest(pagetoken.getStart(), pagetoken.getLength())));
        decksCollection.setNextPageToken(tokenService.genPageToken(pagetoken.getStart() + 1,
                pagetoken.getLength()));
        decksCollection.setResultSizeEstimate(deckRepository.countByUserName(username));
        return decksCollection;
    }

    public DeckWithCards getDeckById(Integer id) {
        DeckWithCards deckWithCards = new DeckWithCards();
        Deck deck = deckRepository.findOne(id);
        deckWithCards.setId(deck.getId());
        deckWithCards.setDesc(deck.getDesc());
        deckWithCards.setCards(fetchCardsByDeckId(id, DB_MAX_TRY));
        return deckWithCards;
    }

    private List<Card> fetchCardsByDeckId(Integer id, Integer maxTry) {
        try {
            return cardRepository.findByDeckId(id);
        } catch (DataAccessException ex) {
            /**
             * handle query timeout exception
             * the strategy is let it try 3 times if get timeout
             * TODO what if timeout happens in the network connection
             * if the a timeout happens between the client side and server side,
             * that must be because of some issue of the network, like bandwidth,
             * ping or network traffic, for these circumstances, a well accepted
             * approach is by send a kind message notifying the client side to try again
             * with no other data sent back. so that would need some frontend code to be written
             * as for the server side, there is no extra code needed.
             */
            System.err.println(ex.getMessage());
            return maxTry == 0 ? new ArrayList<>() : fetchCardsByDeckId(id, maxTry - 1);
        }
    }

    private List<DeckWithCards> genDecksDetailNaive(DecksCollection decks) {
        System.out.println("no cache read");
        List<DeckWithCards> list = new ArrayList<>();
        for (Deck deck : decks.getDecks()) {
            DeckWithCards dwc = new DeckWithCards();
            dwc.setId(deck.getId());
            dwc.setDesc(deck.getDesc());
            dwc.setCards(fetchCardsByDeckId(deck.getId(), DB_MAX_TRY));
            list.add(dwc);
        }
        return list;
    }

    private List<DeckWithCards> genDeckCards(List<Deck> decks) {
        List<DeckWithCards> list = new ArrayList<>();
        for (Deck deck : decks) {
            DeckWithCards dwc = new DeckWithCards();
            dwc.setId(deck.getId());
            dwc.setDesc(deck.getDesc());
            List<Card> cards = cacheService.fetchCardByDeckId(deck.getId());
            dwc.setCards(cards == null ? fetchCardsByDeckId(deck.getId(), DB_MAX_TRY) : cards);
            list.add(dwc);
        }
        return list;
    }

    private List<DeckWithCards> genDecksDetailOptimized(DecksCollection decks) {
        System.out.println("reading from cache first");
        return genDeckCards(decks.getDecks());
    }

    public CollectionRes getDecksDetailByUserName(String userName, String nextPageToken, String optimized) {
        DecksCollection decks = (DecksCollection) getDecksByUserName(userName, nextPageToken);
        List<DeckWithCards> list;
        switch (optimized) {
            case OPTIMIZED:
                list = genDecksDetailOptimized(decks);
                break;
            default:
                list = genDecksDetailNaive(decks);
                break;
        }
        DeckWithCardCollection deckWithCardCollection = new DeckWithCardCollection();
        deckWithCardCollection.setDecks(list);
        deckWithCardCollection.setResultSizeEstimate(decks.getResultSizeEstimate());
        deckWithCardCollection.setNextPageToken(decks.getNextPageToken());
        return deckWithCardCollection;
    }
}
