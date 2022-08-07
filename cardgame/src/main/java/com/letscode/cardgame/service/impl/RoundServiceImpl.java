package com.letscode.cardgame.service.impl;

import com.letscode.cardgame.dto.RoundDTO;
import com.letscode.cardgame.entity.Card;
import com.letscode.cardgame.entity.Movie;
import com.letscode.cardgame.entity.Player;
import com.letscode.cardgame.entity.Round;
import com.letscode.cardgame.exception.EntityNotFoundException;
import com.letscode.cardgame.exception.UnauthorizedException;
import com.letscode.cardgame.repository.RoundRepository;
import com.letscode.cardgame.service.CardService;
import com.letscode.cardgame.service.MoviesService;
import com.letscode.cardgame.service.PlayerService;
import com.letscode.cardgame.service.RoundService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoundServiceImpl implements RoundService {

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private CardService cardService;

    @Override
    public RoundDTO startGame(String playerName) {
        Player player = playerService.getPlayer(playerName);

        Round round = Round.builder()
                .player(player)
                .currentCard(cardService.getCards().get(0))
                .build();

        roundRepository.save(round);

        return RoundDTO.builder()
                .roundId(round.getId())
                .player(round.getPlayer().getUsername())
                .movieA(round.getCurrentCard().getMovieA().getName()
                        + " id: " + round.getCurrentCard().getMovieA().getId())
                .movieB(round.getCurrentCard().getMovieB().getName()
                        + " id: " + round.getCurrentCard().getMovieB().getId())
                .roundMessage("A rodada começou! Envie o id do filme que acha que tem maior nota")
                .build();
    }

    @SneakyThrows
    @Override
    public RoundDTO finishGame(Long roundId, String playerName) {
        Player player = playerService.getPlayer(playerName);
        Round round = getRound(roundId);

        if(!round.getPlayer().getUsername().equals(player.getUsername()))
            throw new UnauthorizedException();

        return finishRound(round.getId());
    }

    @SneakyThrows
    private RoundDTO finishRound(Long roundId) {
        Round round = getRound(roundId);
        round.setFinished(true);
        roundRepository.save(round);

        return RoundDTO.builder()
                .roundId(round.getId())
                .player(round.getPlayer().getUsername())
                .roundMessage("A rodada foi finalizada! Total de pontos: " + round.getPlayerScore())
                .build();
    }

    @SneakyThrows
    @Override
    public RoundDTO play(Long roundId, Long movieId, String playerName) {
        Player player = playerService.getPlayer(playerName);
        Round round = getRound(roundId);

        if(!round.getPlayer().getUsername().equals(player.getUsername()))
            throw new UnauthorizedException();

        Card card = round.getCurrentCard();
        String roundMessage = "";
        if(round.isFinished()) return finishRound(roundId);
        if(round.getRetries() == 3){
            //Pegando novo card
            List<Card> cards = updateCardsAndGetRemaining(round);

            if(cards.isEmpty()) return finishRound(roundId);

            round.setCurrentCard(cards.get(0));

            round.setRetries(0);
            roundRepository.save(round);

            roundMessage = "Tentativas pare esse card acabaram! Novo Card gerado";
        } else{
            Movie winningMovie = card.getMovieA().getScore() > card.getMovieB().getScore()
                    ? card.getMovieA()
                    : card.getMovieB();

            if(winningMovie.getId().equals(movieId)){
                round.addScore();
                List<Card> cards = updateCardsAndGetRemaining(round);
                if(cards.isEmpty()) return finishRound(roundId);
                round.setCurrentCard(cards.get(0));
                round.setRetries(0);
                roundRepository.save(round);
                roundMessage = "ACERTOU!!! PARABÉNS! Gerando novo card";
            } else{
                round.addRetry();
                roundRepository.save(round);
                roundMessage = "EERROU!!! tente novamente...";
            }
        }

        return RoundDTO.builder()
                .roundId(round.getId())
                .player(round.getPlayer().getUsername())
                .movieA(round.getCurrentCard().getMovieA().getName()
                        + " id: " + round.getCurrentCard().getMovieA().getId())
                .movieB(round.getCurrentCard().getMovieB().getName()
                        + " id: " + round.getCurrentCard().getMovieB().getId())
                .roundMessage(roundMessage)
                .build();
    }

    @SneakyThrows
    private Round getRound(Long roundId){
        Optional<Round> optionalRound = roundRepository.findById(roundId);
        if(optionalRound.isEmpty()) throw new EntityNotFoundException("round");
        return optionalRound.get();
    }

    private List<Card> updateCardsAndGetRemaining(Round round){
        round.getPreviousCards().add(round.getCurrentCard());
        List<Card> cards = cardService.getCards();
        cards.removeAll(round.getPreviousCards());
        return cards;
    }
}
