package com.letscode.cardgame.service.impl;

import com.letscode.cardgame.dto.PlayerLoginDTO;
import com.letscode.cardgame.entity.Player;
import com.letscode.cardgame.exception.EntityNotFoundException;
import com.letscode.cardgame.repository.PlayerRepository;
import com.letscode.cardgame.service.PlayerService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> getAll(){
        return playerRepository.findAll();
    }

    @SneakyThrows
    @Override
    public Player getPlayer(final String name) {
        return playerRepository.findByUsername(name);
    }
}
