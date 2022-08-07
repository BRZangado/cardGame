package com.letscode.cardgame.service.impl;

import com.letscode.cardgame.client.MoviesClient;
import com.letscode.cardgame.dto.MovieDTO;
import com.letscode.cardgame.entity.Movie;
import com.letscode.cardgame.repository.MovieRepository;
import com.letscode.cardgame.service.CardService;
import com.letscode.cardgame.service.MoviesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

@Component
@Slf4j
public class MoviesServiceImpl implements MoviesService {

    @Autowired
    private MoviesClient client;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CardService cardService;

    @Value("${movies.api.key}")
    private String apiKey;

    @EventListener(ApplicationReadyEvent.class)
    public void getMovies(){
        log.info("Buscando filmes da api de filmes....");

        List<Movie> movies = movieRepository.findAll();
        movies.forEach(movie -> {
            log.info("Buscando informações do filme: " + movie.getImdbid());

            MovieDTO movieDto = client.getMovie(movie.getImdbid(), apiKey);

            log.info("Informações obtidas com sucesso " + movie.getImdbid());
            log.info("Título " + movieDto.getTitle());
            log.info("Nota " + movieDto.getImdbRating());
            log.info("Votos " + movieDto.getImdbVotes());

            NumberFormat format = NumberFormat.getInstance();

            movie.setName(movieDto.getTitle());
            try {
                movie.setScore(
                        format.parse(movieDto.getImdbRating()).doubleValue()
                                * format.parse(movieDto.getImdbVotes()).doubleValue());
            } catch (ParseException e) {
                //Em caso de falhas setando valor do score como 0
                movie.setScore(0.0);
            }

            movieRepository.save(movie);
        });

        cardService.saveCards(movies);
    }
}
