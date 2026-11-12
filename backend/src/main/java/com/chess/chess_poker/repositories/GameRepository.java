package com.chess.chess_poker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chess.chess_poker.gameStorage.GameRecord;

public interface GameRepository extends JpaRepository<GameRecord, Long>{
    @Query("SELECT g FROM GameRecord g " +
           "WHERE g.gameMode = :mode AND (g.playerWhite = :username OR g.playerBlack = :username) " +
           "ORDER BY g.playedAt ASC")
    List<GameRecord> findByUserAndMode(@Param("mode") String mode, @Param("username") String username);

}
