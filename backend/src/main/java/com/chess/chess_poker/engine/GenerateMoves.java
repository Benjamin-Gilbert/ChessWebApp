package com.chess.chess_poker.engine;
import java.util.List;

import com.chess.chess_poker.boardinfo.BoardState;
import com.chess.chess_poker.boardinfo.Move;
import com.chess.chess_poker.boardinfo.Piece;

import java.util.ArrayList;

public class GenerateMoves {
    public static List<Move> GeneratePossibleMoves(BoardState board, Piece piece) { 
        List<Move> moves = new ArrayList<Move>();
        String color = piece.getColor();
        Piece foundPiece;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board.getBoard()[i][j] != null){
                    if(board.getBoard()[i][j].getColor().equals(color)){
                        if(board.getBoard()[i][j].getName().equals("king")){
                            foundPiece = board.getBoard()[i][j];
                            int x = i + 1;
                            int y = j - 1;
                            while(x < 8 && y > -1 && y < 8 && y <= j + 1){
                                if(board.getBoard()[x][y] != null){
                                    if(board.getBoard()[x][y].getColor().equals(color)){
                                        break;
                                    }
                                    else{
                                        moves.add(new Move(i,j,x,y,foundPiece));
                                        break;
                                    }
                                }
                                else{
                                    moves.add(new Move(i,j,x,y,foundPiece));
                                }
                                y++;
                            }
                            x = i - 1;
                            y = j - 1;
                            while(x > -1 && y > -1 && y < 8 && y <= j + 1){
                                if(board.getBoard()[x][y] != null){
                                    if(board.getBoard()[x][y].getColor().equals(color)){
                                        break;
                                    }
                                    else{
                                        moves.add(new Move(i,j,x,y,foundPiece));
                                        break;
                                    }
                                }
                                else{
                                    moves.add(new Move(i,j,x,y,foundPiece));
                                }
                                y++;
                            }
                            if(j+1 < 8){
                                if(board.getBoard()[i][j+1] != null){
                                    if(!board.getBoard()[i][j+1].getColor().equals(color)){
                                        moves.add(new Move(i,j,i,j+1,foundPiece));
                                    }
                                }
                                else{
                                    moves.add(new Move(i,j,i,j+1,foundPiece));
                                }
                            }
                            
                        }
                        else if(board.getBoard()[i][j].getName().equals("pawn")){
                            foundPiece = board.getBoard()[i][j];
                            if(color.equals("white")){
                                if(!foundPiece.getHasMoved() && i + 2 < 8){
                                    if(board.getBoard()[i+1][j] == null && board.getBoard()[i+2][j] == null){
                                        moves.add(new Move(i, j, i+1, j, foundPiece));
                                        moves.add(new Move(i,j,i+2,j,foundPiece));
                                    }
                                }
                                else if(board.getBoard()[i+1][j] == null){
                                    moves.add(new Move(i,j,i+1,j,foundPiece));
                                }
                                if(j + 1 < 8){
                                    if(board.getBoard()[i+1][j+1] != null){
                                        if(!board.getBoard()[i+1][j+1].getColor().equals(color)){
                                            moves.add(new Move(i,j,i+1,j+1, foundPiece));
                                        }
                                    }
                                    if(MoveValidator.enPassant(foundPiece, new Move(i,j,i+1,j+1), board)){
                                        moves.add(new Move(i,j,i+1,j+1,foundPiece,true));
                                    }
                                }
                                if(j-1>-1){
                                    if(board.getBoard()[i+1][j-1] != null){
                                        if(!board.getBoard()[i+1][j-1].getColor().equals(color)){
                                            moves.add(new Move(i,j, i+1,j-1, foundPiece));
                                        }
                                    }
                                    if(MoveValidator.enPassant(foundPiece, new Move(i,j,i+1,j-1), board)){
                                        moves.add(new Move(i,j,i+1,j-1,foundPiece,true));
                                    }
                                }
                            }
                            //for black
                            else{
                                if(!foundPiece.getHasMoved() && i - 2 > -1){
                                    if(board.getBoard()[i-1][j] == null && board.getBoard()[i-2][j] == null){
                                        moves.add(new Move(i, j, i-1, j, foundPiece));
                                        moves.add(new Move(i,j,i-2,j,foundPiece));
                                    }
                                }
                                else if(board.getBoard()[i-1][j] == null){
                                    moves.add(new Move(i,j,i-1,j,foundPiece));
                                }
                                if(j + 1 < 8){
                                    if(board.getBoard()[i-1][j+1] != null){
                                        if(!board.getBoard()[i-1][j+1].getColor().equals(color)){
                                            moves.add(new Move(i,j,i-1,j+1, foundPiece));
                                        }
                                    }
                                    if(MoveValidator.enPassant(foundPiece, new Move(i,j,i-1,j+1), board)){
                                        moves.add(new Move(i,j,i-1,j+1,foundPiece,true));
                                    }
                                }
                                if(j-1>-1){
                                    if(board.getBoard()[i-1][j-1] != null){
                                        if(!board.getBoard()[i-1][j-1].getColor().equals(color)){
                                            moves.add(new Move(i,j, i-1,j-1, foundPiece));
                                        }
                                    }
                                    if(MoveValidator.enPassant(foundPiece, new Move(i,j,i-1,j-1), board)){
                                        moves.add(new Move(i,j,i-1,j-1,foundPiece,true));
                                    }
                                }
                            }
                        }
                        else if(board.getBoard()[i][j].getName().equals("knight")){
                            foundPiece = board.getBoard()[i][j];
                            if(i + 2 < 8 && j + 1 < 8){
                                if(board.getBoard()[i+2][j+1] == null){
                                    moves.add(new Move(i,j,i+2,j+1, foundPiece));
                                }
                                else if(!board.getBoard()[i+2][j+1].getColor().equals(color)){
                                    moves.add(new Move(i,j,i+2,j+1,foundPiece));
                                }
                            }
                            if(i + 2 < 8 && j - 1 > -1){
                                if(board.getBoard()[i+2][j-1] == null){
                                    moves.add(new Move(i,j,i+2,j-1, foundPiece));
                                }
                                else if(!board.getBoard()[i+2][j-1].getColor().equals(color)){
                                    moves.add(new Move(i,j,i+2,j-1,foundPiece));
                                }
                            }
                            if(i + 1 < 8 && j + 2 < 8){
                                if(board.getBoard()[i+1][j+2] == null){
                                    moves.add(new Move(i,j,i+1,j+2, foundPiece));
                                }
                                else if(!board.getBoard()[i+1][j+2].getColor().equals(color)){
                                    moves.add(new Move(i,j,i+1,j+2,foundPiece));
                                }
                            }
                            if(i + 1 < 8 && j - 2 > -1){
                                if(board.getBoard()[i+1][j-2] == null){
                                    moves.add(new Move(i,j,i+1,j-2, foundPiece));
                                }
                                else if(!board.getBoard()[i+1][j-2].getColor().equals(color)){
                                    moves.add(new Move(i,j,i+1,j-2,foundPiece));
                                }
                            }
                            if(i - 1 > -1 && j + 2 < 8){
                                if(board.getBoard()[i-1][j+2] == null){
                                    moves.add(new Move(i,j,i-1,j+2, foundPiece));
                                }
                                else if(!board.getBoard()[i-1][j+2].getColor().equals(color)){
                                    moves.add(new Move(i,j,i-1,j+2,foundPiece));
                                }
                            }
                            if(i - 1 > -1 && j - 2 > -1){
                                if(board.getBoard()[i-1][j-2] == null){
                                    moves.add(new Move(i,j,i-1,j-2, foundPiece));
                                }
                                else if(!board.getBoard()[i-1][j-2].getColor().equals(color)){
                                    moves.add(new Move(i,j,i-1,j-2,foundPiece));
                                }
                            }
                            if(i - 2 > -1 && j + 1 < 8){
                                if(board.getBoard()[i-2][j+1] == null){
                                    moves.add(new Move(i,j,i-2,j+1, foundPiece));
                                }
                                else if(!board.getBoard()[i-2][j+1].getColor().equals(color)){
                                    moves.add(new Move(i,j,i-2,j+1,foundPiece));
                                }
                            }
                            if(i - 2 > -1 && j - 1 > -1){
                                if(board.getBoard()[i-2][j-1] == null){
                                    moves.add(new Move(i,j,i-2,j-1, foundPiece));
                                }
                                else if(!board.getBoard()[i-2][j-1].getColor().equals(color)){
                                    moves.add(new Move(i,j,i-2,j-1,foundPiece));
                                }
                            }

                            
                        }
                        else if(board.getBoard()[i][j].getName().equals("bishop")){
                            foundPiece = board.getBoard()[i][j];
                            int x = i + 1;
                            int y = j + 1;
                            while(x < 8 && y < 8){
                                if(board.getBoard()[x][y] != null){
                                    if(board.getBoard()[x][y].getColor().equals(color)){
                                        break;
                                    }
                                    else{
                                        moves.add(new Move(i,j,x,y,foundPiece));
                                        break;
                                    }
                                }
                                else{
                                    moves.add(new Move(i,j,x,y,foundPiece));
                                }
                                x++;
                                y++;
                            }
                            x = i + 1;
                            y = j - 1;
                            while(x<8 && y > -1){
                                if(board.getBoard()[x][y] != null){
                                    if(board.getBoard()[x][y].getColor().equals(color)){
                                        break;
                                    }
                                    else{
                                        moves.add(new Move(i,j,x,y,foundPiece));
                                        break;
                                    }
                                }
                                else{
                                    moves.add(new Move(i,j,x,y,foundPiece));
                                }
                                x++;
                                y--;
                            }
                            x = i - 1;
                            y = j - 1;
                            while(x>-1 && y>-1){
                                if(board.getBoard()[x][y] != null){
                                    if(board.getBoard()[x][y].getColor().equals(color)){
                                        break;
                                    }
                                    else{
                                        moves.add(new Move(i,j,x,y,foundPiece));
                                        break;
                                    }
                                }
                                else{
                                    moves.add(new Move(i,j,x,y,foundPiece));
                                }
                                x--;
                                y--;
                            }
                            x = i - 1;
                            y = j + 1;
                            while(x>-1 && y<8){
                                if(board.getBoard()[x][y] != null){
                                    if(board.getBoard()[x][y].getColor().equals(color)){
                                        break;
                                    }
                                    else{
                                        moves.add(new Move(i,j,x,y,foundPiece));
                                        break;
                                    }
                                }
                                else{
                                    moves.add(new Move(i,j,x,y,foundPiece));
                                }
                                x--;
                                y++;
                            }

                        }
                        else if(board.getBoard()[i][j].getName().equals("rook")){
                            foundPiece = board.getBoard()[i][j];
                            int x = i + 1;
                            int y = j;
                            while(x < 8){
                                if(board.getBoard()[x][y] != null){
                                    if(board.getBoard()[x][y].getColor().equals(color)){
                                        break;
                                    }
                                    else{
                                        moves.add(new Move(i,j,x,y,foundPiece));
                                        break;
                                    }
                                }
                                else{
                                    moves.add(new Move(i,j,x,y,foundPiece));
                                }
                                x++;
                            }
                            x = i;
                            y = j + 1;
                            while(y < 8){
                                if(board.getBoard()[x][y] != null){
                                    if(board.getBoard()[x][y].getColor().equals(color)){
                                        break;
                                    }
                                    else{
                                        moves.add(new Move(i,j,x,y,foundPiece));
                                        break;
                                    }
                                }
                                else{
                                    moves.add(new Move(i,j,x,y,foundPiece));
                                }
                                y++;
                            }
                            x = i - 1;
                            y = j;
                            while(x > -1){
                                if(board.getBoard()[x][y] != null){
                                    if(board.getBoard()[x][y].getColor().equals(color)){
                                        break;
                                    }
                                    else{
                                        moves.add(new Move(i,j,x,y,foundPiece));
                                        break;
                                    }
                                }
                                else{
                                    moves.add(new Move(i,j,x,y,foundPiece));
                                }
                                x--;
                            }
                            x = i;
                            y = j - 1;
                            while(y > -1){
                                if(board.getBoard()[x][y] != null){
                                    if(board.getBoard()[x][y].getColor().equals(color)){
                                        break;
                                    }
                                    else{
                                        moves.add(new Move(i,j,x,y,foundPiece));
                                        break;
                                    }
                                }
                                else{
                                    moves.add(new Move(i,j,x,y,foundPiece));
                                }
                                y--;
                            }
                        }
                        else if(board.getBoard()[i][j].getName().equals("queen")){
                            foundPiece = board.getBoard()[i][j];
                            int x = i + 1;
                            int y = j + 1;
                            while(x < 8 && y < 8){
                                if(board.getBoard()[x][y] != null){
                                    if(board.getBoard()[x][y].getColor().equals(color)){
                                        break;
                                    }
                                    else{
                                        moves.add(new Move(i,j,x,y,foundPiece));
                                        break;
                                    }
                                }
                                else{
                                    moves.add(new Move(i,j,x,y,foundPiece));
                                }
                                x++;
                                y++;
                            }
                            x = i + 1;
                            y = j - 1;
                            while(x<8 && y > -1){
                                if(board.getBoard()[x][y] != null){
                                    if(board.getBoard()[x][y].getColor().equals(color)){
                                        break;
                                    }
                                    else{
                                        moves.add(new Move(i,j,x,y,foundPiece));
                                        break;
                                    }
                                }
                                else{
                                    moves.add(new Move(i,j,x,y,foundPiece));
                                }
                                x++;
                                y--;
                            }
                            x = i - 1;
                            y = j - 1;
                            while(x>-1 && y>-1){
                                if(board.getBoard()[x][y] != null){
                                    if(board.getBoard()[x][y].getColor().equals(color)){
                                        break;
                                    }
                                    else{
                                        moves.add(new Move(i,j,x,y,foundPiece));
                                        break;
                                    }
                                }
                                else{
                                    moves.add(new Move(i,j,x,y,foundPiece));
                                }
                                x--;
                                y--;
                            }
                            x = i - 1;
                            y = j + 1;
                            while(x>-1 && y<8){
                                if(board.getBoard()[x][y] != null){
                                    if(board.getBoard()[x][y].getColor().equals(color)){
                                        break;
                                    }
                                    else{
                                        moves.add(new Move(i,j,x,y,foundPiece));
                                        break;
                                    }
                                }
                                else{
                                    moves.add(new Move(i,j,x,y,foundPiece));
                                }
                                x--;
                                y++;
                            }
                            x = i + 1;
                            y = j;
                            while(x < 8){
                                if(board.getBoard()[x][y] != null){
                                    if(board.getBoard()[x][y].getColor().equals(color)){
                                        break;
                                    }
                                    else{
                                        moves.add(new Move(i,j,x,y,foundPiece));
                                        break;
                                    }
                                }
                                else{
                                    moves.add(new Move(i,j,x,y,foundPiece));
                                }
                                x++;
                            }
                            x = i;
                            y = j + 1;
                            while(y < 8){
                                if(board.getBoard()[x][y] != null){
                                    if(board.getBoard()[x][y].getColor().equals(color)){
                                        break;
                                    }
                                    else{
                                        moves.add(new Move(i,j,x,y,foundPiece));
                                        break;
                                    }
                                }
                                else{
                                    moves.add(new Move(i,j,x,y,foundPiece));
                                }
                                y++;
                            }
                            x = i - 1;
                            y = j;
                            while(x > -1){
                                if(board.getBoard()[x][y] != null){
                                    if(board.getBoard()[x][y].getColor().equals(color)){
                                        break;
                                    }
                                    else{
                                        moves.add(new Move(i,j,x,y,foundPiece));
                                        break;
                                    }
                                }
                                else{
                                    moves.add(new Move(i,j,x,y,foundPiece));
                                }
                                x--;
                            }
                            x = i;
                            y = j - 1;
                            while(y > -1){
                                if(board.getBoard()[x][y] != null){
                                    if(board.getBoard()[x][y].getColor().equals(color)){
                                        break;
                                    }
                                    else{
                                        moves.add(new Move(i,j,x,y,foundPiece));
                                        break;
                                    }
                                }
                                else{
                                    moves.add(new Move(i,j,x,y,foundPiece));
                                }
                                y--;
                            }
                        }
                    }
                }
            }
        }
        return moves; 
    } 
}
