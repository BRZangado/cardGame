package com.letscode.cardgame.template;

import com.letscode.cardgame.entity.Card;
import com.letscode.cardgame.entity.Movie;
import com.letscode.cardgame.entity.Player;
import com.letscode.cardgame.entity.Round;

import java.util.ArrayList;

public class RoundTemplates {

    public static Player getPlayer(){
        return Player.builder()
                .id(1L)
                .name("Bruno")
                .username("bruno")
                .password("1234")
                .build();
    }

    public static Card getCard(){
        Movie movie = Movie.builder()
                .id(1L)
                .score(10.0)
                .name("Laranja Mecânica")
                .build();
        Movie movieb = Movie.builder()
                .id(2L)
                .score(11.0)
                .name("O Auto da compadecida")
                .build();

        return Card.builder()
                .id(1L)
                .movieA(movie)
                .movieB(movieb)
                .build();
    }

    public static Card getCardVariant(){
        Movie movie = Movie.builder()
                .id(1L)
                .score(10.0)
                .name("Laranja Mecânica")
                .build();
        Movie movieb = Movie.builder()
                .id(2L)
                .score(11.0)
                .name("O poderoso chefão")
                .build();

        return Card.builder()
                .id(1L)
                .movieA(movie)
                .movieB(movieb)
                .build();
    }

    public static Round getRound(){
        return Round.builder()
                .currentCard(getCard())
                .finished(false)
                .player(getPlayer())
                .id(1L)
                .retries(0)
                .playerScore(0)
                .previousCards(new ArrayList<>())
                .build();
    }
}
