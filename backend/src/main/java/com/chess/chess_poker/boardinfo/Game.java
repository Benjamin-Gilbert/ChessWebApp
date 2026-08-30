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
    private String playerWhite;
    private String playerBlack;
    private int playerWhiteRating;
    private int playerBlackRating;
    private String lastMoveNotated;
    private String gameMode;
    private int minutes;
    private int seconds;
    private String color;
    //private BoardState replayBoard; may be needed to go back moves.

    //Will add clock mechanics, player ids and data, win-loss-draw mechanics, and database functionality later.

    public Game(){
        this.board = new BoardState("None");
        this.gameID = UUID.randomUUID().toString();
        this.moveHistory = new ArrayList<String>();
        this.status = GameStatus.START;
        this.lastMove = null;
        this.lastMoveNotated = null;
        this.moveList = new ArrayList<NotatedMove>();
        //this.replayBoard = this.board;
    }
    public Game(String w, String b, int wr, int br){
        this.board = new BoardState("None");
        this.gameID = UUID.randomUUID().toString();
        this.moveHistory = new ArrayList<String>();
        this.status = GameStatus.START;
        this.lastMove = null;
        this.lastMoveNotated = null;
        this.moveList = new ArrayList<NotatedMove>();
        playerWhite = w;
        playerBlack = b;
        playerWhiteRating = wr;
        playerBlackRating = br;
        //this.replayBoard = this.board;
    }
    public Game(String w, int wr, String gm, int m, int s, String c){
        this.board = new BoardState("None");
        this.gameID = UUID.randomUUID().toString();
        this.moveHistory = new ArrayList<String>();
        this.status = GameStatus.START;
        this.lastMove = null;
        this.lastMoveNotated = null;
        this.moveList = new ArrayList<NotatedMove>();
        playerWhite = w;
        playerWhiteRating = wr;
        gameMode = gm;
        minutes = m;
        seconds = s;
        color = c;
        playerBlack = "waiting";
        playerBlackRating = 0;
        
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
    public String getGameMode(){return gameMode;}
    public int getMinutes(){return minutes;}
    public int getSeconds(){return seconds;}
    public String getColor(){return color;}
    public void setGameMode(String gm){gameMode = gm;}
    public void setMinutes(int m){minutes = m;}
    public void setSeconds(int s){seconds = s;}
    public void setColor(String c){color = c;}
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
        return new GameResponse(gameID, playerWhite, playerBlack, playerWhiteRating, playerBlackRating, this.getBoard().getBoard(), this.getBoard().getTurn(), this.getMoveList());
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
    public void startGame(){
        this.board.setTurn("white");
        //clock logic.
    }
    public void joinGame(String username, int rating){
        playerBlack = username;
        playerBlackRating = rating;
        this.startGame();
    }
}
