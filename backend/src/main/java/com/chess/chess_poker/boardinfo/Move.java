package com.chess.chess_poker.boardinfo;
import com.chess.chess_poker.engine.*;

public class Move {
    private int fromRow;
    private int fromCol;
    private int toRow;
    private int toCol;
    private Piece piece;
    private boolean enPassant;
    public Move(int fromRow, int fromCol, int toRow, int toCol) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
    }
    public Move(int fromRow, int fromCol, int toRow, int toCol, Piece piece){
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
        this.piece = piece;
    }
    public Move(int fromRow, int fromCol, int toRow, int toCol, Piece piece, boolean e){
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
        this.piece = piece;
        this.enPassant = e;
    }
    //getters and setters.
    public int getFromRow() {
        return fromRow;
    }
    public int getFromCol() {
        return fromCol;
    }
    public int getToRow() {
        return toRow;
    }
    public int getToCol() {
        return toCol;
    }
    public Piece getPiece(){
        return piece;
    }
    public boolean getEnPassant(){
        return enPassant;
    }
    public void setFromRow(int r){
        fromRow = r;
    }
    public void setFromCol(int c){
        fromCol = c;
    }
    public void setToRow(int r){
        toRow = r;
    }
    public void setToCol(int c){
        toCol = c;
    }
    public void setPiece(Piece p){
        piece = p;
    }
    public String notate(Piece piece, String side, boolean enPassant, boolean isThereACapture, String promote, BoardState board){
        String capture = "";
        String check = "";
        String mate = "";
        //String fromRank;
        //String fromFile;
        String toFile;
        String toRank = String.valueOf(toRow + 1);
        String pieceAbbr = "";
        String color = "";
        String promotion = "";
        if(side.equals("kingside")){
            return "O-O";
        }
        else if(side.equals("queenside")){
            return "O-O-O";
        }
        if(piece.getColor().equals("white")){
            color = "black";
        }
        else{
            color = "white";
        }
        switch(toCol){
            case 0:
                toFile = "a";
                break;
            case 1:
                toFile = "b";
                break;
            case 2:
                toFile = "c";
                break;
            case 3:
                toFile = "d";
                break;
            case 4:
                toFile = "e";
                break;
            case 5:
                toFile = "f";
                break;
            case 6:
                toFile = "g";
                break;
            case 7:
                toFile = "h";
                break;
            default:
                toFile = "";
        }
        switch(piece.getName()){
            case "knight":
                pieceAbbr = "N";
                break;
            case "bishop":
                pieceAbbr = "B";
                break;
            case "rook":
                pieceAbbr = "R";
                break;
            case "queen":
                pieceAbbr = "Q";
                break;
            case "king":
                pieceAbbr = "K";
                break;
            default:
                pieceAbbr = "";
        }
        if(isThereACapture || enPassant){
            if(piece.getName().equals("pawn")){
                String fromFile = "";
                switch(fromCol){
            case 0:
                fromFile = "a";
                break;
            case 1:
                fromFile = "b";
                break;
            case 2:
                fromFile = "c";
                break;
            case 3:
                fromFile = "d";
                break;
            case 4:
                fromFile = "e";
                break;
            case 5:
                fromFile = "f";
                break;
            case 6:
                fromFile = "g";
                break;
            case 7:
                fromFile = "h";
                break;
            default:
                fromFile = "";
             }
             capture = fromFile + "x";
            }
            else{
                capture = "x";
            }
        }
        if(CheckDetector.detectCheckMate(board, board.findPieceByCoordinates(board.findPiece(color, "king")[0], board.findPiece(color, "king")[1]))){
            mate = "#";
        }
        else if(CheckDetector.detectCheck(board, board.findPieceByCoordinates(board.findPiece(color, "king")[0], board.findPiece(color, "king")[1]))){
            check = "+";
        }
        if(!promote.equals("")){
            switch(promote){
                case "knight":
                promotion = "=N";
                break;
            case "bishop":
                promotion = "=B";
                break;
            case "rook":
                promotion = "=R";
                break;
            case "queen":
                promotion = "=Q";
                break;
            default:
                promotion = "";
            }
            return capture + toFile + toRank + promotion + check + mate;
        }

        return pieceAbbr + capture + toFile + toRank + promotion + check + mate;
    }
    
}
