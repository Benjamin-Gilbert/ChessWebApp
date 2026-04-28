package com.chess.chess_poker.engine;
import com.chess.chess_poker.boardinfo.BoardState;
import com.chess.chess_poker.boardinfo.Move;
import com.chess.chess_poker.boardinfo.Piece;

public class MoveValidator {
    private static boolean isEnPassant = false;
    public static boolean validatePawn(Piece piece, Move move, BoardState board){
        int currRow = piece.getCurrPosition(board)[0];
        int currCol = piece.getCurrPosition(board)[1];
        if(!piece.getColor().equals(board.getTurn())){
            return false;
        }
        if(move.getToCol() - move.getFromCol() == 0 && board.getBoard()[move.getToRow()][move.getToCol()] != null){
            return false;
        }
        if(move.getToCol() - move.getFromCol() != 0 && board.getBoard()[move.getToRow()][move.getToCol()] == null && !enPassant(piece, move, board)){
            return false;
        }
        if(board.getBoard()[move.getToRow()][move.getToCol()] !=null){
            if(board.getBoard()[move.getToRow()][move.getToCol()].getColor().equals(piece.getColor())){
                return false;
            }
        }
        if(Math.abs(move.getToRow() - currRow) == 2 && board.getBoard()[(currRow + move.getToRow())/2][currCol] != null){
            return false;
        }
        if(piece.getColor().equals("white")){
            if((Math.abs(currCol-move.getToCol()) == 1 && move.getToRow() - currRow == 1 && !(board.getBoard()[move.getToRow()][move.getToCol()] != null || enPassant(piece,move,board))) || (Math.abs(currCol - move.getToCol()) != 1 && Math.abs(currCol - move.getToCol()) != 0)){
                return false;
            }
            if((move.getToRow() - currRow == 2 && piece.getHasMoved()) || move.getToRow() - currRow > 2 || move.getToRow() - currRow < 1){
                return false;
            }
            
        }
        else{
            if((Math.abs(currCol-move.getToCol()) == 1 && move.getToRow() - currRow == -1 && !(board.getBoard()[move.getToRow()][move.getToCol()] != null || enPassant(piece,move,board))) || (Math.abs(currCol - move.getToCol()) != 1 && Math.abs(currCol - move.getToCol()) != 0)){
                return false;
            }
            if((move.getToRow() - currRow == -2 && piece.getHasMoved()) || move.getToRow() - currRow < -2 || move.getToRow() - currRow > -1){
                return false;
            }
        }
        if(enPassant(piece, move, board)){
            //enpassant
            board.enPassant(move, piece);
            if(CheckDetector.detectCheck(board, board.getBoard()[board.findPiece(piece.getColor(),"king")[0]][board.findPiece(piece.getColor(), "king")[1]])){
                board.undoEnPassant(move, piece);
                return false;
            }
            board.undoEnPassant(move, piece);
            isEnPassant = true;
            return true;
        }
        board.makeMove(move, piece);
        if(CheckDetector.detectCheck(board, board.getBoard()[board.findPiece(piece.getColor(), "king")[0]][board.findPiece(piece.getColor(), "king")[1]])){
            board.undoMove(move, piece);
            return false;
        }
        else{board.undoMove(move, piece);}
        
        return true;
    }
    public static boolean validateKnight(Piece piece, Move move, BoardState board){
        int currRow = piece.getCurrPosition(board)[0];
        int currCol = piece.getCurrPosition(board)[1];
        int toRow = move.getToRow();
        int toCol = move.getToCol();
        String color = piece.getColor();
        if(!piece.getColor().equals(board.getTurn())){
            return false;
        }
        if(board.getBoard()[toRow][toCol] != null){
            if(board.getBoard()[toRow][toCol].getColor().equals(color)){
                return false;
            }
        }
        if(!(Math.abs(currRow - toRow) == 2 && Math.abs(currCol - toCol) == 1) && !(Math.abs(currRow - toRow) == 1 && Math.abs(currCol - toCol) == 2)){
            return false;
        }
        board.makeMove(move, piece);
        if(CheckDetector.detectCheck(board, board.getBoard()[board.findPiece(color, "king")[0]][board.findPiece(color, "king")[1]])){
            board.undoMove(move, piece);
            return false;
        }
        else{board.undoMove(move, piece);}
        return true;
    }
    public static boolean validateBishop(Piece piece, Move move, BoardState board){
        int currRow = piece.getCurrPosition(board)[0];
        int currCol = piece.getCurrPosition(board)[1];
        int toRow = move.getToRow();
        int toCol = move.getToCol();
        String color = piece.getColor();
        if(!piece.getColor().equals(board.getTurn())){
            return false;
        }
        if(board.getBoard()[toRow][toCol] != null){
            if(board.getBoard()[toRow][toCol].getColor().equals(color)){
                return false;
            }
        }
        if(Math.abs(currRow - toRow) != Math.abs(currCol - toCol)){
            return false;
        }
        //Piece is in the way:
        int squaresBetween = Math.abs(toRow - currRow);
        if(toRow - currRow > 0 && toCol - currCol > 0){
            for(int i = 1; i < squaresBetween; i++){
                if(board.getBoard()[currRow+i][currCol+i] != null){
                    return false;
                }
            }
        }
        else if(toRow - currRow > 0 && toCol - currCol < 0){
            for(int i = 1; i < squaresBetween; i ++){
                if(board.getBoard()[currRow + i][currCol - i] != null){
                    return false;
                }
            }
        }
        else if(toRow - currRow < 0 && toCol - currCol < 0){
            for(int i = 1; i < squaresBetween; i++){
                if(board.getBoard()[currRow - i][currCol - i] != null){
                    return false;
                }
            }
        }
        else{
            for(int i = 1; i < squaresBetween; i++){
                if(board.getBoard()[currRow - i][currCol + i] != null){
                    return false;
                }
            }
        }
        board.makeMove(move, piece);
        if(CheckDetector.detectCheck(board, board.getBoard()[board.findPiece(color, "king")[0]][board.findPiece(color, "king")[1]])){
            board.undoMove(move, piece);
            return false;
        }
        board.undoMove(move, piece);
        return true;
    }
    public static boolean validateRook(Piece piece, Move move, BoardState board){
        int currRow = piece.getCurrPosition(board)[0];
        int currCol = piece.getCurrPosition(board)[1];
        int toRow = move.getToRow();
        int toCol = move.getToCol();
        String color = piece.getColor();
        if(!piece.getColor().equals(board.getTurn())){
            return false;
        }
        if(board.getBoard()[toRow][toCol] != null){
            if(board.getBoard()[toRow][toCol].getColor().equals(color)){
                return false;
            }
        }
        if(currRow - toRow != 0 && currCol - toCol != 0){
            return false;
        }

        //if piece in the way:
        int squaresBetweenVertical = Math.abs(toRow - currRow);
        int squaresBetweenHorizontal = Math.abs(toCol - currCol);
        if(currRow-toRow == 0 && toCol - currCol > 0){
            for(int i = 1; i < squaresBetweenHorizontal; i++){
                if(board.getBoard()[currRow][currCol + i] != null){
                    return false;
                }
            }
        }
        else if(toRow - currRow > 0 && toCol - currCol == 0){
            for(int i = 1; i < squaresBetweenVertical; i++){
                if(board.getBoard()[currRow + i][currCol] != null){
                    return false;
                }
            }
        }
        else if(toRow - currRow == 0 && toCol - currCol < 0){
            for(int i = 1; i < squaresBetweenHorizontal; i++){
                if(board.getBoard()[currRow][currCol - i] != null){
                    return false;
                }
            }
        }
        else{
            for(int i = 1; i < squaresBetweenVertical; i++){
                if(board.getBoard()[currRow - i][currCol] != null){
                    return false;
                }
            }
        }
        board.makeMove(move, piece);
        if(CheckDetector.detectCheck(board, board.getBoard()[board.findPiece(color, "king")[0]][board.findPiece(color, "king")[1]])){
            board.undoMove(move, piece);
            return false;
        }
        board.undoMove(move, piece);
        return true;
    }
    public static boolean validateQueen(Piece piece, Move move, BoardState board){
        if(validateRook(piece, move, board) || validateBishop(piece, move, board)){
            return true;
        }
        else{
            return false;
        }
    }
    public static boolean validateKing(Piece piece, Move move, BoardState board){
        int currRow = move.getFromRow();
        int currCol = move.getFromCol();
        int toRow = move.getToRow();
        int toCol = move.getToCol();
        String color = piece.getColor();
        if(!piece.getColor().equals(board.getTurn())){
            return false;
        }
        if(board.getBoard()[toRow][toCol] != null){
            if(board.getBoard()[toRow][toCol].getColor().equals(color)){
                return false;
            }
        }
        if(Math.abs(toRow - currRow) > 1 || Math.abs(toCol - currCol) > 1){
            if(Math.abs(toCol - currCol) == 2){
                if(validateCastle(piece, move, board)){
                    return true;
                }
            }
            return false;
        }
        board.makeMove(move, piece);
        if(CheckDetector.detectCheck(board, piece)){
            board.undoMove(move, piece);
            return false;
        }
        board.undoMove(move, piece);
        return true;
    }
    public static boolean enPassant(Piece pawn, Move move, BoardState board){
        int currRow = move.getFromRow();
        int currCol = move.getFromCol();
        int rankToMatch;
        int toRow = move.getToRow();
        int toCol = move.getToCol();
        String color = pawn.getColor();
        String file = "";
        String moveToMatch;
        switch (toCol){
            case 0:
                file = "a";
                break;
            case 1:
                file = "b";
                break;
            case 2:
                file = "c";
                break;
            case 3:
                file = "d";
                break;
            case 4:
                file = "e";
                break;
            case 5:
                file = "f";
                break;
            case 6:
                file = "g";
                break;
            case 7: 
                file = "h";
                break;
        }
        if(color.equals("white")){
            moveToMatch = file + "5";
            rankToMatch = 4;
        }
        else{
            moveToMatch = file + "4";
            rankToMatch = 3;
        }
        if(board.getBoard()[toRow][toCol] != null){
            return false;
        }
        if(board.getLastMove() != null){
            if(board.getLastMove().equals(moveToMatch) && rankToMatch == currRow && Math.abs(currCol - toCol) == 1){
                return true;
            }
        }


        return false;
    }
    public static boolean validateCastle(Piece piece, Move move, BoardState board){
        int currRow = move.getFromRow();
        int toRow = move.getToRow();
        int currCol = move.getFromCol();
        int toCol = move.getToCol();
        String color = piece.getColor();
        String side;
        if(!piece.getColor().equals(board.getTurn())){
            return false;
        }
        if(piece.getHasMoved()){
            return false;
        }
        if(CheckDetector.detectCheck(board, piece)){
            return false;
        }
        if(toCol - currCol == 2){
            side = "kingside";
        }
        else if(toCol - currCol == -2){
            side = "queenside";
        }
        else{
            return false;
        }
        if(color.equals("white") && side.equals("kingside")){
            if(board.findPieceByCoordinates(0, 7) == null){
                return false;
            }
            if(board.findPieceByCoordinates(0, 7).getHasMoved()){
                return false;
            }
            if(board.getBoard()[currRow][currCol + 1] != null || board.getBoard()[toRow][toCol] != null){
                return false;
            }
            move.setToCol(toCol - 1);
            board.makeMove(move, piece);
            if(CheckDetector.detectCheck(board, piece)){
                board.undoMove(move, piece);
                move.setToCol(toCol);
                return false;
            }
            board.undoMove(move,piece);
            move.setToCol(toCol);
        }
        else if(color.equals("white") && side.equals("queenside")){
            if(board.findPieceByCoordinates(0, 0) == null){
                return false;
            }
            if(board.findPieceByCoordinates(0, 0).getHasMoved()){
                return false;
            }
            if(board.getBoard()[currRow][currCol - 1] != null || board.getBoard()[toRow][toCol] != null || board.getBoard()[toRow][toCol - 1] != null){
                return false;
            }
            move.setToCol(toCol + 1);
            board.makeMove(move, piece);
            if(CheckDetector.detectCheck(board, piece)){
                board.undoMove(move, piece);
                move.setToCol(toCol);
                return false;
            }
            board.undoMove(move,piece);
            move.setToCol(toCol);
        }
        else if(color.equals("black") && side.equals("kingside")){
            if(board.findPieceByCoordinates(7, 7) == null){
                return false;
            }
            if(board.findPieceByCoordinates(7, 7).getHasMoved()){
                return false;
            }
            if(board.getBoard()[currRow][currCol + 1] != null || board.getBoard()[toRow][toCol] != null){
                return false;
            }
            move.setToCol(toCol - 1);
            board.makeMove(move, piece);
            if(CheckDetector.detectCheck(board, piece)){
                board.undoMove(move, piece);
                move.setToCol(toCol);
                return false;
            }
            board.undoMove(move,piece);
            move.setToCol(toCol);
        }
        else if(color.equals("black") && side.equals("queenside")){
            if(board.findPieceByCoordinates(7, 0) == null){
                return false;
            }
            if(board.findPieceByCoordinates(7, 0).getHasMoved()){
                return false;
            }
            if(board.getBoard()[currRow][currCol - 1] != null || board.getBoard()[toRow][toCol] != null || board.getBoard()[toRow][toCol - 1] != null){
                return false;
            }
            move.setToCol(toCol + 1);
            board.makeMove(move, piece);
            if(CheckDetector.detectCheck(board, piece)){
                board.undoMove(move, piece);
                move.setToCol(toCol);
                return false;
            }
            board.undoMove(move,piece);
            move.setToCol(toCol);
        }
        // check if castle into check:
        board.castle(move, piece, side);
        if(CheckDetector.detectCheck(board, piece)){
            board.undoCastle(move, piece, side);
            return false;
        }
        board.undoCastle(move, piece, side);
        return true;
    }
    //get and set enpassant status.
    public static boolean getEnPassant(){
        return isEnPassant;
    }
    public static void setEnPassant(boolean e){
        isEnPassant = e;
    }
}