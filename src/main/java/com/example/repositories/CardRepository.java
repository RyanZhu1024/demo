package com.example.repositories;

import com.example.models.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by RyanZhu on 4/7/16.
 */
@Repository
public interface CardRepository extends CrudRepository<Card, Integer> {
    @Transactional(readOnly = true,timeout = 30)
    List<Card> findByDeckId(Integer deckId);

    List<Card> findByDeckIdIn(List<Integer> deckIds);
}
