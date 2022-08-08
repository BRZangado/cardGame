package com.letscode.cardgame.controller;

import com.letscode.cardgame.dto.RankingDTO;
import com.letscode.cardgame.dto.RoundDTO;
import com.letscode.cardgame.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(RoundController.MAPPING_BASE_URL)
public class RoundController {

    public static final String MAPPING_BASE_URL = "/api/v1/round";

    @Autowired
    private RoundService roundService;

    @PostMapping(path = "/start", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RoundDTO> startRound(HttpServletRequest request){
        return ResponseEntity.ok(roundService.startGame(request.getUserPrincipal().getName()));
    }

    @PostMapping(path = "/play", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RoundDTO> playRound(@RequestParam Long roundId,
                                              @RequestParam Long movieId,
                                              HttpServletRequest request){
        return ResponseEntity.ok(roundService.play(roundId, movieId, request.getUserPrincipal().getName()));
    }

    @PostMapping(path = "/finish", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RoundDTO> finishRound(@RequestParam Long roundId, HttpServletRequest request){
        return ResponseEntity.ok(roundService.finishGame(roundId, request.getUserPrincipal().getName()));
    }

    @GetMapping(path = "/ranking", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RankingDTO>> getRanking(){
        return ResponseEntity.ok().body(roundService.getRanking());
    }

}
