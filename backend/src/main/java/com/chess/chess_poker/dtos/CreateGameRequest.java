package com.chess.chess_poker.dtos;

public class CreateGameRequest {
    private String playerA;
    private String playerB;
    private int playerARating;
    private int playerBRating;
    public CreateGameRequest(String a, String b, int ar, int br){
        playerA = a;
        playerB = b;
        playerARating = ar;
        playerBRating = br;
    }
    public String getPlayerA(){
        return playerA;
    }
    public String getPlayerB(){
        return playerB;
    }
    public int getPlayerARating(){
        return playerARating;
    }
    public int getPlayerBRating(){
        return playerBRating;
    }
}
