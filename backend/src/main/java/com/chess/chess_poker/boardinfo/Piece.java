package com.chess.chess_poker.boardinfo;

public class Piece {
    private String name;
    private String color;
    private boolean hasMoved;
    private String frontendKey;
    public Piece(String name, String color) {
        this.name = name;
        this.color = color;
        this.hasMoved = false;
        this.frontendKey = this.setFrontendKey();
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
    public String getFrontendKey(){
        return frontendKey;
    }
    private String setFrontendKey(){
        if(this.name.equals("knight")){
            return this.color.substring(0,1) + "n";
        }
        else{
            return this.color.substring(0,1) + this.name.substring(0,1);
        }
    }
}
