package com.letscode.cardgame.service;

import com.letscode.cardgame.dto.RoundDTO;

public interface RoundService {
    RoundDTO startGame(String player);
    RoundDTO finishGame(Long roundId, String playerName);
    RoundDTO play(Long roundId, Long option, String playerName);
}
