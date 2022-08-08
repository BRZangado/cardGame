package com.letscode.cardgame.service;

import com.letscode.cardgame.entity.Card;
import com.letscode.cardgame.entity.Movie;

import java.util.List;

public interface CardService {
    void saveCards(final List<Movie> movies);
    List<Card> getCards();
}
