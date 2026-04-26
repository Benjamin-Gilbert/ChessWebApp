package com.chess.chess_poker.BoardInfo;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
public class Game {
    private final String gameID;
    private BoardState board;
    private List<String> moveHistory;
    private GameStatus status;
    private Move lastMove;
    //private BoardState replayBoard; may be needed to go back moves.

    //Will add clock mechanics, player ids and data, win-loss-draw mechanics, and database functionality later.

    public Game(){
        this.board = new BoardState("white");
        this.gameID = UUID.randomUUID().toString();
        this.moveHistory = new ArrayList<String>();
        this.status = GameStatus.START;
        this.lastMove = null;
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
    public void addMoveToHistory(String m){
        moveHistory.add(m);
    }
    public void setLastMove(Move m){
        lastMove = m;
    }
    public void setStatus(GameStatus s){
        status = s;
    }
}
