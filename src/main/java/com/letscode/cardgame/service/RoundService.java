package com.letscode.cardgame.service;

import com.letscode.cardgame.dto.RankingDTO;
import com.letscode.cardgame.dto.RoundDTO;

import java.util.List;

public interface RoundService {
    RoundDTO startGame(String player);
    RoundDTO finishGame(Long roundId, String playerName);
    RoundDTO play(Long roundId, Long option, String playerName);

    List<RankingDTO> getRanking();
}
