package com.chess.chess_poker.boardinfo;

public class NotatedMove {
    private String white;
    private String black;
    private int moveNum;
    public NotatedMove(int mn, String w){
        white = w;
        moveNum = mn;
        black = "";
    }
    public NotatedMove(int mn, String w, String b){
        white = w;
        moveNum = mn;
        black = b;
    }
    public String getWhite(){
        return white;
    }
    public String getBlack(){
        return black;
    }
    public int getMoveNum(){
        return moveNum;
    }
}
