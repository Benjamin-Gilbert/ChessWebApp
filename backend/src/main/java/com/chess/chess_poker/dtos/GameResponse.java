package com.chess.chess_poker.dtos;
import com.chess.chess_poker.boardinfo.*;
import java.util.ArrayList;
import java.util.List;
public class GameResponse {
    private String gameID;
    private String playerA;
    private String playerB;
    private int playerARating;
    private int playerBRating;
    private String moveMadeOrNah;
    private Piece[][] mirrorBoard;
    private String lastMove;
    private List<NotatedMove> moveList;
    public GameResponse(){
        gameID = "Game not found";
    }
    public GameResponse(String g, String a, String b, int ar, int br){
        gameID = g;
        playerA = a;
        playerB = b;
        playerARating = ar;
        playerBRating = br;
    }
    public GameResponse(String s, Piece[][] mb, String lm){
        moveMadeOrNah = s;
        mirrorBoard = mb;
        lastMove = lm;
    }
    public GameResponse(String s, Piece[][] mb, String lm, List<NotatedMove> ml){
        moveMadeOrNah = s;
        mirrorBoard = mb;
        lastMove = lm;
        moveList = ml;
    }
    public String getGameID(){
        return gameID;
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
    public String getMoveMade(){
        return moveMadeOrNah;
    }
    public Piece[][] getMirrorBoard(){
        return mirrorBoard;
    }
    public String getLastMove(){
        return lastMove;
    }
    public List<NotatedMove> getMoveList(){
        return moveList;
    }
}
