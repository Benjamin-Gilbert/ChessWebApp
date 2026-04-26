package com.chess.chess_poker.BoardInfo;

public class Piece {
    private String name;
    private String color;
    private boolean hasMoved;
    public Piece(String name, String color) {
        this.name = name;
        this.color = color;
        this.hasMoved = false;
    }
    public String getName(){
        return name;
    }
    public String getColor(){
        return color;
    }
    public boolean getHasMoved(){
        return hasMoved;
    }
    public void setHasMoved(boolean hasMoved){   // remember to change when moving a piece like pawn, king, rook.
        this.hasMoved = hasMoved;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setColor(String color){
        this.color = color;
    }
    public int[] getCurrPosition(BoardState board){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getBoard()[i][j] == this) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
    public String getSymbol(){
        switch(this.name){
            case "pawn":
                return "p";
            case "bishop":
                return "b";
            case "knight":
                return "n";
            case "rook":
                return "r";
            case "king":
                return "k";
            case "queen":
                return "q";
        }
        return "?";
    }
}
