package com.letscode.cardgame.repository;

import com.letscode.cardgame.entity.Round;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoundRepository extends CrudRepository<Round, Long> {
    @Query(value = "SELECT * FROM round WHERE finished = true ORDER BY player_score DESC", nativeQuery = true)
    List<Round> filterRoundsByScore();
}
