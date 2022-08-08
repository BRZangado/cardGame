package com.letscode.cardgame.service;

import com.letscode.cardgame.dto.PlayerLoginDTO;
import com.letscode.cardgame.entity.Player;

import java.util.List;

public interface PlayerService {

    List<Player> getAll();
    Player getPlayer(final String name);
}
