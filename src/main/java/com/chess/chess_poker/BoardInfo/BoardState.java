package com.chess.chess_poker.BoardInfo;
import java.util.List;
import java.util.ArrayList;

public class BoardState {
    private Piece[][] board;
    private String turn;
    //private String[] moveList; may be better if arraylist
    private String lastMove;
    private Piece capturedPiece;
    private List<Piece> whiteCapturedPieces;
    private List<Piece> blackCapturedPieces;
    public BoardState(String t) {
        board = new Piece[8][8];
        board[0][0] = new Piece("rook", "white");
        board[0][1] = new Piece ("knight", "white");
        board[0][2] = new Piece ("bishop", "white");
        board[0][3] = new Piece ("queen", "white");
        board[0][4] = new Piece ("king", "white");
        board[0][5] = new Piece ("bishop", "white");
        board[0][6] = new Piece ("knight", "white");
        board[0][7] = new Piece ("rook", "white");
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Piece("pawn", "white");
        }
        board[7][0] = new Piece("rook", "black");
        board[7][1] = new Piece ("knight", "black");
        board[7][2] = new Piece ("bishop", "black");
        board[7][3] = new Piece ("queen", "black");
        board[7][4] = new Piece ("king", "black");
        board[7][5] = new Piece ("bishop", "black");
        board[7][6] = new Piece ("knight", "black");
        board[7][7] = new Piece ("rook", "black");
        for (int i = 0; i < 8; i++) {
            board[6][i] = new Piece("pawn", "black");
        }
        turn = t;
        lastMove = null;
        capturedPiece = null;
        whiteCapturedPieces = new ArrayList<>();
        blackCapturedPieces = new ArrayList<>();
    }
    // methods to set pieces, move pieces, remove pieces, get turn, gamestate, etc.
    public void promote(Piece piece, String toPiece){
        piece.setName(toPiece);
    }
    public void makeMove(Move move, Piece piece){
        if(this.board[move.getToRow()][move.getToCol()] != null){
            capturedPiece = this.board[move.getToRow()][move.getToCol()];
        }
        this.board[move.getToRow()][move.getToCol()] = piece;
        this.board[move.getFromRow()][move.getFromCol()] = null;
        if (turn.equals("white")){
            turn = "black";
        }
        else{
            turn = "white";
        }
        //lastMove = move.notate(piece);
    }
    public void undoMove(Move move, Piece piece){
        this.board[move.getFromRow()][move.getFromCol()] = piece;
        if(capturedPiece != null){
            this.board[move.getToRow()][move.getToCol()] = capturedPiece;
            capturedPiece = null;
        }
        else{
            this.board[move.getToRow()][move.getToCol()] = null;
        }
        if (turn.equals("white")){
            turn = "black";
        }
        else{
            turn = "white";
        }
        //lastMove = null;
    }
    public void castle(Move move, Piece piece, String side){
        //Castle logic.
        if(side.equals("kingside")){
            this.board[move.getToRow()][move.getToCol()] = piece;
            this.board[move.getFromRow()][move.getFromCol()] = null;
            if(piece.getColor().equals("white")){
                this.board[move.getToRow()][move.getToCol() - 1] = findPieceByCoordinates(0, 7);
                removePiece(0, 7);
            }
            else if(piece.getColor().equals("black")){
                this.board[move.getToRow()][move.getToCol() - 1] = findPieceByCoordinates(7, 7);
                removePiece(7,7);
            }
        }
        else if(side.equals("queenside")){
            this.board[move.getToRow()][move.getToCol()] = piece;
            this.board[move.getFromRow()][move.getFromCol()] = null;
            if(piece.getColor().equals("white")){
                this.board[move.getToRow()][move.getToCol() + 1] = findPieceByCoordinates(0,0);
                removePiece(0, 0);
            }
            else if(piece.getColor().equals("black")){
                this.board[move.getToRow()][move.getToCol() + 1] = findPieceByCoordinates(7, 0);
                removePiece(7, 0);
            }
        }
        if(turn.equals("white")){
            turn = "black";
        }
        else{
            turn = "white";
        }
    }
    public void undoCastle(Move move, Piece piece, String side){
        if(side.equals("kingside")){
            this.board[move.getFromRow()][move.getFromCol()] = piece;
            this.board[move.getToRow()][move.getToCol()] = null;
            if(piece.getColor().equals("white")){
                this.board[0][7] = findPieceByCoordinates(move.getToRow(), move.getToCol() - 1);
                removePiece(move.getToRow(), move.getToCol() - 1);
            }
            else{
                this.board[7][7] = findPieceByCoordinates(move.getToRow(), move.getToCol() - 1);
                removePiece(move.getToRow(), move.getToCol() - 1);
            }
        }
        else if(side.equals("queenside")){
            this.board[move.getFromRow()][move.getFromCol()] = piece;
            this.board[move.getToRow()][move.getToCol()] = null;
            if(piece.getColor().equals("white")){
                this.board[0][0] = findPieceByCoordinates(move.getToRow(), move.getToCol() + 1);
                removePiece(move.getToRow(), move.getToCol() + 1);
            }
            else{
                this.board[7][0] = findPieceByCoordinates(move.getToRow(), move.getToCol() + 1);
                removePiece(move.getToRow(), move.getToCol() + 1);
            }
        }
        if(turn.equals("white")){
            turn = "black";
        }
        else{
            turn = "white";
        }
    }
    public void enPassant(Move move, Piece piece){
        this.board[move.getToRow()][move.getToCol()] = piece;
        this.board[move.getFromRow()][move.getFromCol()] = null;
        capturedPiece = this.board[move.getFromRow()][move.getToCol()];
        this.board[move.getFromRow()][move.getToCol()] = null;
        if(turn.equals("white")){
            turn = "black";
        }
        else{
            turn = "white";
        }
    }
    public void undoEnPassant(Move move, Piece piece){
        this.board[move.getFromRow()][move.getFromCol()] = piece;
        this.board[move.getToRow()][move.getToCol()] = null;
        this.board[move.getFromRow()][move.getToCol()] = capturedPiece;
        capturedPiece = null;
        if(turn.equals("white")){
            turn = "black";
        }
        else{
            turn = "white";
        }
    }
    public String getTurn(){
        return turn;
    }
    public Piece[][] getBoard(){
        return board;
    }
    public void removePiece(int row, int col){
        this.board[row][col] = null;
    }
    public String getLastMove(){
        return lastMove;
    }
    public void setLastMove(String l){
        lastMove = l;
    }
    public List<Piece> getWhiteCaptruedPieces(){
        return whiteCapturedPieces;
    }
    public List<Piece> getBlackCapturedPieces(){
        return blackCapturedPieces;
    }
    public void addPieceToWhiteCaptured(Piece p){
        whiteCapturedPieces.add(p);
    }
    public void addPieceToBlackCaptured(Piece p ){
        blackCapturedPieces.add(p);
    }
    public Piece getCapturedPiece(){
        return capturedPiece;
    }
    public void setCapturedPiece(Piece p){
        capturedPiece = p;
    }


    public int[] findPiece(String color, String name){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null && board[i][j].getName().equals(name) && board[i][j].getColor().equals(color)) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
    public Piece findPieceByCoordinates(int row, int col){
        return this.board[row][col];
    }
    public String boardToString(){
        StringBuilder sb = new StringBuilder();
        for(int row = 7; row > -1; row--){
            for(int col = 0; col < 8; col++){
                Piece piece = board[row][col];
                if(piece == null){
                    sb.append(". ");
                }
                else{
                    sb.append(piece.getSymbol()).append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
