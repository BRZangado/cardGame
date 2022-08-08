package com.letscode.cardgame.controller;

import com.letscode.cardgame.exception.EntityNotFoundException;
import com.letscode.cardgame.exception.UnauthorizedException;
import com.letscode.cardgame.service.RoundService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class RoundControllerTest {

    @InjectMocks
    private RoundController roundController;

    @Mock
    private RoundService roundService;

    private MockMvc mockMvc;

    private Principal principalMock;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(roundController)
                .setControllerAdvice(ControllerExceptionHandler.class)
                .setValidator(new LocalValidatorFactoryBean())
                .build();

        principalMock = mock(Principal.class);
    }

    @Test
    public void shouldStartGame() throws Exception {
        mockMvc.perform(post("/api/v1/round/start")
                        .principal(principalMock))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void shouldPlayGame() throws Exception {
        mockMvc.perform(post("/api/v1/round/play?roundId=29&movieId=7")
                        .principal(principalMock))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void shouldNotPlayGameNotFoundPlayer() throws Exception {

        when(roundService.play(any(), any(), any())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(post("/api/v1/round/play?roundId=29&movieId=7")
                        .principal(principalMock))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void shouldNotPlayGameNotAuthPlayer() throws Exception {

        when(roundService.play(any(), any(), any())).thenThrow(UnauthorizedException.class);

        mockMvc.perform(post("/api/v1/round/play?roundId=29&movieId=7")
                        .principal(principalMock))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void shouldFinishGame() throws Exception {
        mockMvc.perform(post("/api/v1/round/finish?roundId=29")
                        .principal(principalMock))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void shouldFinishNotFoundGame() throws Exception {

        when(roundService.finishGame(any(), any())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(post("/api/v1/round/finish?roundId=29")
                        .principal(principalMock))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void shouldFinishNotAuthGame() throws Exception {

        when(roundService.finishGame(any(), any())).thenThrow(UnauthorizedException.class);

        mockMvc.perform(post("/api/v1/round/finish?roundId=29")
                        .principal(principalMock))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }
}
