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
    private int[][] legalMoves;
    private String turn;
    private String message;
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
    public GameResponse(String s, Piece[][] mb, String lm, List<NotatedMove> ml, String t){
        moveMadeOrNah = s;
        mirrorBoard = mb;
        lastMove = lm;
        moveList = ml;
        turn = t;
    }
    public GameResponse(String m){
        message = m;
    }
    public GameResponse(int[][] m){
        legalMoves = m;
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
    public int[][] getLegalMoves(){
        return legalMoves;
    }
    public String getTurn(){
        return turn;
    }
    public String getMessage(){
        return message;
    }
}
