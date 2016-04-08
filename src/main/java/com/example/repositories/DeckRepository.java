package com.example.repositories;

import com.example.models.Deck;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by RyanZhu on 4/7/16.
 */
@Repository
public interface DeckRepository extends CrudRepository<Deck, Integer> {
    List<Deck> findByUserName(String userName, Pageable pageable);

    Integer countByUserName(String userName);
}
