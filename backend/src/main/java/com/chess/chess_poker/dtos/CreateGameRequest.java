package com.chess.chess_poker.dtos;


public class CreateGameRequest {
    private String gameMode;
    private int minutes;
    private int seconds;
    private String color;
    public CreateGameRequest(String gm, int m, int s, String c){
        gameMode = gm;
        minutes = m;
        seconds = s;
        color = c;
    }
    public String getGameMode(){
        return gameMode;
    }
    public int getMinutes(){
        return minutes;
    }
    public int getSeconds(){
        return seconds;
    }
    public String getColor(){
        return color;
    }
}
