package com.chess.chess_poker.gameStorage;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.Instant;

import jakarta.persistence.Column;

@Entity
@Table(name="games")
public class GameRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gameMode;
    private String playerWhite;
    private String playerBlack;
    private String result; // white, black, or draw
    private int whiteUpdatedRating;
    private int blackUpdatedRating;
    private Instant playedAt = Instant.now();

    @Column(columnDefinition = "TEXT")
    private String pgn;

    public Long getId(){return id;}
    public String getGameMode(){return gameMode;}
    public String getPlayerWhite(){return playerWhite;}
    public String getPlayerBlack(){return playerBlack;}
    public String getResult(){return result;}
    public int getWhiteUpdatedRating(){return whiteUpdatedRating;}
    public int getBlackUpdatedRating(){return blackUpdatedRating;}
    public Instant getPlayedAt(){return playedAt;}
    public String getPGN(){return pgn;}

    public void setId(Long i){id = i;}
    public void setGameMode(String g){gameMode = g;}
    public void setPlayerWhite(String w){playerWhite = w;}
    public void setPlayerBlack(String b){playerBlack = b;}
    public void setResult(String r){result = r;}
    public void setWhiteUpdatedRating(int w){whiteUpdatedRating = w;}
    public void setBlackUpdatedRating(int b){blackUpdatedRating = b;}
    public void setPlayedAt(Instant p){playedAt = p;}
    public void setPGN(String p){pgn = p;}
    
}