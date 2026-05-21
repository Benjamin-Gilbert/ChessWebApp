package com.chess.chess_poker.boardinfo;
import java.util.UUID;

import org.apache.naming.java.javaURLContextFactory;

import com.chess.chess_poker.dtos.GameResponse;

import java.util.List;
import java.util.ArrayList;
public class Game {
    private final String gameID;
    private BoardState board;
    private List<String> moveHistory;
    private List<NotatedMove> moveList;
    private GameStatus status;
    private Move lastMove;
    private String playerA;
    private String playerB;
    private int playerARating;
    private int playerBRating;
    private String lastMoveNotated;
    //private BoardState replayBoard; may be needed to go back moves.

    //Will add clock mechanics, player ids and data, win-loss-draw mechanics, and database functionality later.

    public Game(){
        this.board = new BoardState("white");
        this.gameID = UUID.randomUUID().toString();
        this.moveHistory = new ArrayList<String>();
        this.status = GameStatus.START;
        this.lastMove = null;
        this.lastMoveNotated = null;
        this.moveList = new ArrayList<NotatedMove>();
        //this.replayBoard = this.board;
    }
    public Game(String a, String b, int ar, int br){
        this.board = new BoardState("white");
        this.gameID = UUID.randomUUID().toString();
        this.moveHistory = new ArrayList<String>();
        this.status = GameStatus.START;
        this.lastMove = null;
        this.lastMoveNotated = null;
        this.moveList = new ArrayList<NotatedMove>();
        playerA = a;
        playerB = b;
        playerARating = ar;
        playerBRating = br;
        //this.replayBoard = this.board;
    }
    //Getters and setters:

    public BoardState getBoard(){
        return board;
    }
    public String getGameID(){
        return gameID;
    }
    public List<String> getMoveHistory(){
        return moveHistory;
    }
    public GameStatus getStatus(){
        return status;
    }
    public Move getLastMove(){
        return lastMove;
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
    public void addMoveToHistory(String m){
        moveHistory.add(m);
        this.lastMoveNotated = m;
        this.constructMoveList(moveHistory);
    }
    public void setLastMove(Move m){
        lastMove = m;
    }
    public void setStatus(GameStatus s){
        status = s;
    }
    public GameResponse returnAsResponse(){
        return new GameResponse(gameID, playerA, playerB, playerARating, playerBRating);
    }
    public String getLastMoveNotated(){
        return lastMoveNotated;
    }
    public List<NotatedMove> getMoveList(){
        return moveList;
    }
    public void constructMoveList(List<String> list){
        this.moveList.clear();
        int j = 1;
        for(int i = 0; i < list.size(); i+= 2){
            if(list.size() - i < 2){
                this.moveList.add(new NotatedMove(j, list.get(i)));
                j++;
            }
            else{
                this.moveList.add(new NotatedMove(j, list.get(i), list.get(i+1)));
                j++;
            }
        }
    }
}
