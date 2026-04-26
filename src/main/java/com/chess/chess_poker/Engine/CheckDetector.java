package com.chess.chess_poker.Engine;

import com.chess.chess_poker.BoardInfo.BoardState;
import com.chess.chess_poker.BoardInfo.Piece;
import com.chess.chess_poker.BoardInfo.Move;

import java.util.List;
import java.util.ArrayList;

public class CheckDetector {
    public static boolean detectCheck(BoardState board, Piece king){
        int[] loc = king.getCurrPosition(board);
        Piece foundPiece;
        for(int i = loc[0] + 1; i < 8; i++){
            if(board.getBoard()[i][loc[1]] != null){
                foundPiece = board.getBoard()[i][loc[1]];
                if(!(foundPiece.getColor().equals(king.getColor())) && (foundPiece.getName().equals("rook") || foundPiece.getName().equals("queen"))){
                    return true;
                }
                else{break;}
            }
        }
        for(int i = loc[0] - 1; i >= 0; i--){
            if(board.getBoard()[i][loc[1]] != null){
                foundPiece = board.getBoard()[i][loc[1]];
                if(!(foundPiece.getColor().equals(king.getColor())) && (foundPiece.getName().equals("rook") || foundPiece.getName().equals("queen"))){
                    return true;
                }
                else{break;}
            }
        }
        for(int i = loc[1] + 1; i < 8; i++){
            if(board.getBoard()[loc[0]][i] != null){
                foundPiece = board.getBoard()[loc[0]][i];
                if(!(foundPiece.getColor().equals(king.getColor())) && (foundPiece.getName().equals("rook") || foundPiece.getName().equals("queen"))){
                    return true;
                }
                else{break;}
            }
        }
        for(int i = loc[1] - 1; i >= 0; i--){
            if(board.getBoard()[loc[0]][i] != null){
                foundPiece = board.getBoard()[loc[0]][i];
                if(!(foundPiece.getColor().equals(king.getColor())) && (foundPiece.getName().equals("rook") || foundPiece.getName().equals("queen"))){
                    return true;
                }
                else{break;}
            }
        }
        int rowIndex = loc[0] + 1;
        int colIndex = loc[1] + 1;
        while(rowIndex < 8 && colIndex < 8){
            if(board.getBoard()[rowIndex][colIndex] != null){
                foundPiece = board.getBoard()[rowIndex][colIndex];
                if(!(foundPiece.getColor().equals(king.getColor())) && (foundPiece.getName().equals("bishop") || foundPiece.getName().equals("queen"))){
                    return true;
                }
                else{break;}
            }
            rowIndex++;
            colIndex++;
        }
        rowIndex = loc[0] + 1;
        colIndex = loc[1] - 1;
        while(rowIndex < 8 && colIndex > -1){
            if(board.getBoard()[rowIndex][colIndex] != null){
                foundPiece = board.getBoard()[rowIndex][colIndex];
                if(!(foundPiece.getColor().equals(king.getColor())) && (foundPiece.getName().equals("bishop") || foundPiece.getName().equals("queen"))){
                    return true;
                }
                else{break;}
            }
            rowIndex++;
            colIndex--;
        }
        rowIndex = loc[0] - 1;
        colIndex = loc[1] - 1;
        while(rowIndex > -1 && colIndex > -1){
            if(board.getBoard()[rowIndex][colIndex] != null){
                foundPiece = board.getBoard()[rowIndex][colIndex];
                if(!(foundPiece.getColor().equals(king.getColor())) && (foundPiece.getName().equals("bishop") || foundPiece.getName().equals("queen"))){
                    return true;
                }
                else{break;}
            }
            rowIndex--;
            colIndex--;
        }
        rowIndex = loc[0] - 1;
        colIndex = loc[1] + 1;
        while(rowIndex > -1 && colIndex < 8){
            if(board.getBoard()[rowIndex][colIndex] != null){
                foundPiece = board.getBoard()[rowIndex][colIndex];
                if(!(foundPiece.getColor().equals(king.getColor())) && (foundPiece.getName().equals("bishop") || foundPiece.getName().equals("queen"))){
                    return true;
                }
                else{break;}
            }
            rowIndex--;
            colIndex++;
        }

        if(loc[0] + 2 < 8 && loc[1] + 1 < 8){
            if(board.getBoard()[loc[0] + 2][loc[1]+ 1] != null){
                foundPiece = board.getBoard()[loc[0] + 2][loc[1] + 1];
                if(!(foundPiece.getColor().equals(king.getColor())) && foundPiece.getName().equals("knight")){
                    return true;
                }
            }
        }
        if(loc[0] + 2 < 8 && loc[1] - 1 > -1){
            if(board.getBoard()[loc[0] + 2][loc[1] - 1] != null){
                foundPiece = board.getBoard()[loc[0] + 2][loc[1] - 1];
                if(!(foundPiece.getColor().equals(king.getColor())) && foundPiece.getName().equals("knight")){
                    return true;
                }
            }
        }
        if(loc[0] + 1 < 8 && loc[1] + 2 < 8){
            if(board.getBoard()[loc[0] + 1][loc[1]+ 2] != null){
                foundPiece = board.getBoard()[loc[0] + 1][loc[1] + 2];
                if(!(foundPiece.getColor().equals(king.getColor())) && foundPiece.getName().equals("knight")){
                    return true;
                }
            }
        }
        if(loc[0] + 1 < 8 && loc[1] - 2 > -1){
            if(board.getBoard()[loc[0] + 1][loc[1] - 2] != null){
                foundPiece = board.getBoard()[loc[0] + 1][loc[1] - 2];
                if(!(foundPiece.getColor().equals(king.getColor())) && foundPiece.getName().equals("knight")){
                    return true;
                }
            }
        }
        if(loc[0] - 1 > -1 && loc[1] + 2 < 8){
            if(board.getBoard()[loc[0] - 1][loc[1] + 2] != null){
                foundPiece = board.getBoard()[loc[0] - 1][loc[1] + 2];
                if(!(foundPiece.getColor().equals(king.getColor())) && foundPiece.getName().equals("knight")){
                    return true;
                }
            }
        }
        if(loc[0] - 1 > -1 && loc[1] - 2 > -1){
            if(board.getBoard()[loc[0] - 1][loc[1] - 2] != null){
                foundPiece = board.getBoard()[loc[0] - 1][loc[1] - 2];
                if(!(foundPiece.getColor().equals(king.getColor())) && foundPiece.getName().equals("knight")){
                    return true;
                }
            }
        }
        if(loc[0] - 2 > -1 && loc[1] + 1 < 8){
            if(board.getBoard()[loc[0] - 2][loc[1] + 1] != null){
                foundPiece = board.getBoard()[loc[0] - 2][loc[1] + 1];
                if(!(foundPiece.getColor().equals(king.getColor())) && foundPiece.getName().equals("knight")){
                    return true;
                }
            }
        }
        if(loc[0] - 2 > -1 && loc[1] - 1 > -1){
            if(board.getBoard()[loc[0] - 2][loc[1] - 1] != null){
                foundPiece = board.getBoard()[loc[0] - 2][loc[1] - 1];
                if(!(foundPiece.getColor().equals(king.getColor())) && foundPiece.getName().equals("knight")){
                    return true;
                }
            }
        }
        if(king.getColor().equals("white")){
            if(loc[0] + 1 < 8 && loc[1] + 1 < 8){
                if(board.getBoard()[loc[0] + 1][loc[1] + 1] != null){
                    foundPiece = board.getBoard()[loc[0] + 1][loc[1] + 1];
                    if(!(foundPiece.getColor().equals(king.getColor())) && (foundPiece.getName().equals("pawn") || foundPiece.getName().equals("king"))){
                        return true;
                    }
                }
            }
            if(loc[0] + 1 < 8 && loc[1] - 1 > -1){
                if(board.getBoard()[loc[0] + 1][loc[1] - 1] != null){
                    foundPiece = board.getBoard()[loc[0] + 1][loc[1] - 1];
                    if(!(foundPiece.getColor().equals(king.getColor())) && (foundPiece.getName().equals("pawn") || foundPiece.getName().equals("king"))){
                        return true;
                    }
                }
            }
        }
        if(king.getColor().equals("black") && loc[0] - 1 > -1){
            if(loc[1] + 1 < 8){
                if(board.getBoard()[loc[0] - 1][loc[1] + 1] != null){
                    foundPiece = board.getBoard()[loc[0] - 1][loc[1] + 1];
                    if(!(foundPiece.getColor().equals(king.getColor())) && (foundPiece.getName().equals("pawn") || foundPiece.getName().equals("king"))){
                        return true;
                    }
                }
            }
            if(loc[1] - 1 > -1){
                if(board.getBoard()[loc[0] - 1][loc[1] - 1] != null){
                    foundPiece = board.getBoard()[loc[0] - 1][loc[1] - 1];
                    if(!(foundPiece.getColor().equals(king.getColor())) && (foundPiece.getName().equals("pawn") || foundPiece.getName().equals("king"))){
                        return true;
                    }
                }
            }
        }
        if(loc[0] + 1 < 8){
            if(board.getBoard()[loc[0] + 1][loc[1]] != null){
                    foundPiece = board.getBoard()[loc[0] + 1][loc[1]];
                    if(!(foundPiece.getColor().equals(king.getColor())) && foundPiece.getName().equals("king")){
                        return true;
                    }
                }
            if(loc[1] + 1 < 8){
                if(board.getBoard()[loc[0] + 1][loc[1] + 1] != null){
                    foundPiece = board.getBoard()[loc[0] + 1][loc[1] + 1];
                    if(!(foundPiece.getColor().equals(king.getColor())) && foundPiece.getName().equals("king")){
                        return true;
                    }
                }
            }
            if(loc[1] - 1 > -1){
                if(board.getBoard()[loc[0] + 1][loc[1] - 1] != null){
                    foundPiece = board.getBoard()[loc[0] + 1][loc[1] - 1];
                    if(!(foundPiece.getColor().equals(king.getColor())) && foundPiece.getName().equals("king")){
                        return true;
                    }
                }
            }
        }
        if(loc[0] - 1 > -1){
            if(board.getBoard()[loc[0] - 1][loc[1]] != null){
                    foundPiece = board.getBoard()[loc[0] - 1][loc[1]];
                    if(!(foundPiece.getColor().equals(king.getColor())) && foundPiece.getName().equals("king")){
                        return true;
                    }
                }
            if(loc[1] + 1 < 8){
                if(board.getBoard()[loc[0] - 1][loc[1] + 1] != null){
                    foundPiece = board.getBoard()[loc[0] - 1][loc[1] + 1];
                    if(!(foundPiece.getColor().equals(king.getColor())) && foundPiece.getName().equals("king")){
                        return true;
                    }
                }
            }
            if(loc[1] - 1 > -1){
                if(board.getBoard()[loc[0] - 1][loc[1] - 1] != null){
                    foundPiece = board.getBoard()[loc[0] - 1][loc[1] - 1];
                    if(!(foundPiece.getColor().equals(king.getColor())) && foundPiece.getName().equals("king")){
                        return true;
                    }
                }
            }
        }
        if(loc[1] + 1 < 8){
            if(board.getBoard()[loc[0]][loc[1] + 1] != null){
                foundPiece = board.getBoard()[loc[0]][loc[1] + 1];
                if(!(foundPiece.getColor().equals(king.getColor())) && foundPiece.getName().equals("king")){
                    return true;
                }
            }
        }
        if(loc[1] - 1 > -1){
            if(board.getBoard()[loc[0]][loc[1] - 1] != null){
                foundPiece = board.getBoard()[loc[0]][loc[1] - 1];
                if(!(foundPiece.getColor().equals(king.getColor())) && foundPiece.getName().equals("king")){
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean detectCheckMate(BoardState board, Piece king){
        //is in checkmate logic and send to end of game state.
        if(!detectCheck(board, king)){
            return false;
        }
        List<Move> moves = new ArrayList<Move>();
        moves = GenerateMoves.GeneratePossibleMoves(board, king);
        for(Move x : moves){
            board.makeMove(x, x.getPiece());
            if(!detectCheck(board, king)){
                board.undoMove(x, x.getPiece());
                return false;
            }
            else{
                board.undoMove(x, x.getPiece());
            }
        }
        return true;
    }
    public static boolean detectStaleMate(BoardState board, Piece king){
        // is in stalemate logic.
        if(detectCheck(board, king)){
            return false;
        }
        List<Move> moves = new ArrayList<Move>();
        moves = GenerateMoves.GeneratePossibleMoves(board, king);
        for(Move x : moves){
            board.makeMove(x, x.getPiece());
            if(!detectCheck(board, king)){
                board.undoMove(x, x.getPiece());
                return false;
            }
            else{
                board.undoMove(x, x.getPiece());
            }
        }
        return true;
    }
}
