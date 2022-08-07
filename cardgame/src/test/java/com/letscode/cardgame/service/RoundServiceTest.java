package com.letscode.cardgame.service;

import com.letscode.cardgame.dto.RoundDTO;
import com.letscode.cardgame.entity.Player;
import com.letscode.cardgame.exception.EntityNotFoundException;
import com.letscode.cardgame.exception.UnauthorizedException;
import com.letscode.cardgame.repository.RoundRepository;
import com.letscode.cardgame.service.impl.RoundServiceImpl;
import com.letscode.cardgame.template.RoundTemplates;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class RoundServiceTest{
    
    @InjectMocks
    private RoundServiceImpl roundService;

    @Mock
    private RoundRepository roundRepository;

    @Mock
    private PlayerService playerService;

    @Mock
    private CardService cardService;

    @Test
    public void shouldStartGame() {
        when(playerService.getPlayer(any())).thenReturn(RoundTemplates.getPlayer());
        when(cardService.getCards()).thenReturn(new ArrayList<>(List.of(RoundTemplates.getCard())));

        RoundDTO roundDTO = roundService.startGame(RoundTemplates.getPlayer().getUsername());

        Assert.assertEquals(roundDTO.getPlayer(), RoundTemplates.getPlayer().getUsername());
        Assert.assertEquals(RoundTemplates.getCard().getMovieA().getName() + " id: " + RoundTemplates.getCard().getMovieA().getId(),
                roundDTO.getMovieA());
    }

    @Test
    public void shouldFinishGame() {
        when(playerService.getPlayer(any())).thenReturn(RoundTemplates.getPlayer());
        when(roundRepository.findById(any())).thenReturn(Optional.of(RoundTemplates.getRound()));

        RoundDTO roundDTO = roundService.finishGame(1L, "bruno");

        Assert.assertEquals(roundDTO.getPlayer(), RoundTemplates.getPlayer().getUsername());
    }

    @Test(expected = UnauthorizedException.class)
    public void shouldNotFinishGameAuthError() {
        Player player = RoundTemplates.getPlayer();
        player.setUsername("teste");
        when(playerService.getPlayer(any())).thenReturn(player);
        when(roundRepository.findById(any())).thenReturn(Optional.of(RoundTemplates.getRound()));

        roundService.finishGame(1L, "bruno");
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldNotFinishGame404Error() {
        Player player = RoundTemplates.getPlayer();
        player.setUsername("teste");
        when(playerService.getPlayer(any())).thenReturn(player);
        when(roundRepository.findById(any())).thenReturn(Optional.empty());

        roundService.finishGame(1L, "bruno");
    }

    @Test
    public void shouldPlayAndWinRound(){
        when(playerService.getPlayer(any())).thenReturn(RoundTemplates.getPlayer());
        when(roundRepository.findById(any())).thenReturn(Optional.of(RoundTemplates.getRound()));
        when(cardService.getCards()).thenReturn(
                new ArrayList<>(List.of(RoundTemplates.getCard(), RoundTemplates.getCardVariant())));

        RoundDTO roundDTO = roundService.play(1L, 2L, "teste");
        Assert.assertEquals(roundDTO.getRoundMessage(), "ACERTOU!!! PARABÉNS! Gerando novo card");
    }

    @Test
    public void shouldPlayAndFailRound(){
        when(playerService.getPlayer(any())).thenReturn(RoundTemplates.getPlayer());
        when(roundRepository.findById(any())).thenReturn(Optional.of(RoundTemplates.getRound()));
        when(cardService.getCards()).thenReturn(
                new ArrayList<>(List.of(RoundTemplates.getCard(), RoundTemplates.getCardVariant())));

        RoundDTO roundDTO = roundService.play(1L, 1L, "teste");
        Assert.assertEquals(roundDTO.getRoundMessage(), "EERROU!!! tente novamente...");
    }

    @Test
    public void shouldPlayAndWinGame(){
        when(playerService.getPlayer(any())).thenReturn(RoundTemplates.getPlayer());
        when(roundRepository.findById(any())).thenReturn(Optional.of(RoundTemplates.getRound()));

        RoundDTO roundDTO = roundService.play(1L, 2L, "teste");
        Assert.assertEquals(roundDTO.getRoundMessage(), "A rodada foi finalizada! Total de pontos: 1");
    }

    @Test(expected = UnauthorizedException.class)
    public void shouldNotPlayGameAuthError(){
        Player player = RoundTemplates.getPlayer();
        player.setUsername("teste");
        when(playerService.getPlayer(any())).thenReturn(player);
        when(roundRepository.findById(any())).thenReturn(Optional.of(RoundTemplates.getRound()));

        RoundDTO roundDTO = roundService.play(1L, 2L, "teste");
        Assert.assertEquals(roundDTO.getRoundMessage(), "A rodada foi finalizada! Total de pontos: 1");
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldNotPlayGame404Error(){
        Player player = RoundTemplates.getPlayer();
        player.setUsername("teste");
        when(playerService.getPlayer(any())).thenReturn(player);
        when(roundRepository.findById(any())).thenReturn(Optional.empty());

        RoundDTO roundDTO = roundService.play(1L, 2L, "teste");
        Assert.assertEquals(roundDTO.getRoundMessage(), "A rodada foi finalizada! Total de pontos: 1");
    }
}
