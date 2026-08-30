package com.chess.chess_poker.dtos;

public class JoinGameRequest {
    private String gameID;
    public JoinGameRequest(String id){
        gameID = id;
    }
    public String getGameID(){return gameID;}
    public void setGameID(String g){gameID = g;}
}
