package com.example;

import com.example.caches.CacheService;
import com.example.models.Card;
import com.example.models.Deck;
import com.example.repositories.CardRepository;
import com.example.repositories.DeckRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(CardRepository cardRepository,
                                  DeckRepository deckRepository,
                                  CacheService cacheService) {
        return (args) -> {
            for (int i = 0; i < 100; i++) {
                Deck deck = new Deck();
                deck.setDesc("abc");
                deck.setUserName("ryan");
                Deck saved = deckRepository.save(deck);
                for (int j = 0; j < 1000; j++) {
                    Card card = new Card();
                    card.setDeckId(saved.getId());
                    card.setTitle("abc");
                    card.setPayload("abc");
                    cardRepository.save(card);
                    cacheService.addDeckCard(deck.getId(),card);
                }
            }
        };
    }
}
