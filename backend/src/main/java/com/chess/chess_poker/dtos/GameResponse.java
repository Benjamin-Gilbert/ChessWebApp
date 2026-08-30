package com.chess.chess_poker.dtos;
import com.chess.chess_poker.boardinfo.*;
import java.util.ArrayList;
import java.util.List;
public class GameResponse {
    private String gameID;
    private String playerWhite;
    private String playerBlack;
    private int playerWhiteRating;
    private int playerBlackRating;
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
    public GameResponse(String g, String w, String b, int wr, int br, Piece[][] mb){
        gameID = g;
        playerWhite = w;
        playerBlack = b;
        playerWhiteRating = wr;
        playerBlackRating = br;
        mirrorBoard = mb;
        
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
    public GameResponse(String g, String w, String b, int wr, int br, Piece[][] mb, String t, List<NotatedMove> ml){
        gameID = g;
        playerWhite = w;
        playerBlack = b;
        playerWhiteRating = wr;
        playerBlackRating = br;
        mirrorBoard = mb;
        turn = t;
        moveList = ml;
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
    public String getPlayerWhite(){
        return playerWhite;
    }
    public String getPlayerBlack(){
        return playerBlack;
    }
    public int getPlayerWhiteRating(){
        return playerWhiteRating;
    }
    public int getPlayerBlackRating(){
        return playerBlackRating;
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
