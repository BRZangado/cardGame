package com.letscode.cardgame.repository;

import com.letscode.cardgame.entity.Card;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardRepository extends CrudRepository<Card, Long> {
    List<Card> findAll();
}
