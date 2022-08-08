package com.letscode.cardgame.service.impl;

import com.letscode.cardgame.entity.Card;
import com.letscode.cardgame.entity.Movie;
import com.letscode.cardgame.repository.CardRepository;
import com.letscode.cardgame.service.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public void saveCards(List<Movie> movies) {

        log.info("Salvando cards...");
        List<Card> s = new ArrayList<>();

        for (int i = 0; i < movies.size(); i++)
            for (Movie movie : movies) {
                Card card = Card.builder()
                        .movieA(movies.get(i))
                        .movieB(movie)
                        .build();

                Card cardAux = Card.builder()
                        .movieA(movie)
                        .movieB(movies.get(i))
                        .build();

                if(!movies.get(i).equals(movie) && !(s.contains(card) || s.contains(cardAux))){
                    s.add(card);
                }
            }

        cardRepository.saveAll(s);
        log.info("Cards salvos... jogo liberado");
    }

    @Override
    public List<Card> getCards() {
        List<Card> cards = cardRepository.findAll();
        Collections.shuffle(cards);
        return cards;
    }
}