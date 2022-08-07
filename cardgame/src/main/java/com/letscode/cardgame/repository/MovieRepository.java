package com.letscode.cardgame.repository;

import com.letscode.cardgame.entity.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Set;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    List<Movie> findAll();

    @Query(value = "select m1.id, m2.id from movie m1 inner join movie m2 on m1.id < m2.id",
            nativeQuery = true)
    List<Pair<Long, Long>> getMixed();
}
