package com.chess.chess_poker.DTOs;

public class MoveRequest {
    private String gameID;
    private int fromRow;
    private int fromCol;
    private int toRow;
    private int toCol;
    public MoveRequest(String g, int fr, int fc, int tr, int tc){
        gameID = g;
        fromRow = fr;
        fromCol = fc;
        toRow = tr;
        toCol = tc;
    }
    public MoveRequest(){
        //required for some reason.
    }
    public String getGameID(){
        return gameID;
    }
    public void setGameID(String id){gameID = id;}
    public int getFromRow(){
        return fromRow;
    }
    public void setFromRow(int fr){fromRow = fr;}
    public int getFromCol(){
        return fromCol;
    }
    public void setFromCol(int fc){fromCol = fc;}
    public int getToRow(){
        return toRow;
    }
    public void setToRow(int tr){toRow = tr;}
    public int getToCol(){
        return toCol;
    }
    public void setToCol(int tc){toCol = tc;}
}
