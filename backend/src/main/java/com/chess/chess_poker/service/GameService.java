package com.chess.chess_poker.service;
import com.chess.chess_poker.ChessPokerApplication;
import org.springframework.stereotype.Service;
import com.chess.chess_poker.boardinfo.*;
import com.chess.chess_poker.engine.*;
import com.chess.chess_poker.dtos.MoveRequest;
import java.util.Map;
import java.util.HashMap;

@Service
public class GameService {
    private Map<String, Game> games;

    public GameService(ChessPokerApplication chessPokerApplication){
        games = new HashMap<>();
    }

    public Game findGame(String gameID){
        return games.get(gameID);
    }
    public Map<String, Game> getAllGames(){
        return games;
    }
    public String createGame(){
        Game game = new Game();
        games.put(game.getGameID(), game);
        return game.getGameID();
    }
    public String makeMove(MoveRequest request){
        Game game = games.get(request.getGameID());
        boolean enPassant = false;
        boolean capture = false;
        String notatedMove;
        String side = "";
        String promotion = "";
        if(game == null){
            return "Error: game not found";
        }
        Piece piece = game.getBoard().findPieceByCoordinates(request.getFromRow(), request.getFromCol());
        if(piece == null){
            return "No piece here";
        }
        Move move = new Move(request.getFromRow(), request.getFromCol(), request.getToRow(), request.getToCol());
        String name = piece.getName();
        boolean validMove;
        switch (name){
            case "pawn":
                validMove = MoveValidator.validatePawn(piece, move, game.getBoard());
                break;
            case "bishop":
                validMove = MoveValidator.validateBishop(piece, move, game.getBoard());
                break;
            case "knight":
                validMove = MoveValidator.validateKnight(piece, move, game.getBoard());
                break;
            case "rook":
                validMove = MoveValidator.validateRook(piece, move, game.getBoard());
                break;
            case "queen":
                validMove = MoveValidator.validateQueen(piece, move, game.getBoard());
                break;
            case "king":
                validMove = MoveValidator.validateKing(piece, move, game.getBoard());
                break;
            default:
                validMove = false;
        }
        if(!validMove){
            return "Invalid move";
        }
        //Apply move, update move history and last move, update game status, etc.... and return result.
        //Check for castle and en Passant:
        if(piece.getName().equals("king") && move.getToCol() - move.getFromCol() == 2){
            side = "kingside";
            game.getBoard().castle(move, piece, side);
            game.addMoveToHistory("O-O");
            game.setLastMove(move);
            game.getBoard().setLastMove("O-O");
            return "Castled" + side + move.getToCol();
        }
        else if(piece.getName().equals("king") && move.getToCol() - move.getFromCol() == -2){
            side = "queenside";
            game.getBoard().castle(move, piece, side);
            game.addMoveToHistory("O-O-O");
            game.setLastMove(move);
            game.getBoard().setLastMove("O-O-O");
            return "Castled" + side + move.getToCol();
        }
        else if(MoveValidator.getEnPassant()){
            game.getBoard().enPassant(move, piece);
            enPassant = true;
            if(piece.getColor().equals("white")){
                game.getBoard().addPieceToBlackCaptured(game.getBoard().getCapturedPiece());
                game.getBoard().setCapturedPiece(null);
            }
            else{
                game.getBoard().addPieceToWhiteCaptured(game.getBoard().getCapturedPiece());
                game.getBoard().setCapturedPiece(null);
            }
        }
        else{
            game.getBoard().makeMove(move, piece);
            if(game.getBoard().getCapturedPiece() != null){
                if(piece.getColor().equals("white")){
                    game.getBoard().addPieceToBlackCaptured(game.getBoard().getCapturedPiece());
                    game.getBoard().setCapturedPiece(null);
                }
                else{
                    game.getBoard().addPieceToWhiteCaptured(game.getBoard().getCapturedPiece());
                    game.getBoard().setCapturedPiece(null);
                }
                capture = true;
            }
            if(piece.getName().equals("pawn") && (piece.getCurrPosition(game.getBoard())[0] == 0 || piece.getCurrPosition(game.getBoard())[0] == 7)){
                game.getBoard().promote(piece, "queen");
                promotion = "queen";
            }
            piece.setHasMoved(true);
        }
        //Add to move history etc.
        notatedMove = move.notate(piece, side, enPassant, capture, promotion, game.getBoard());
        game.addMoveToHistory(notatedMove);
        game.setLastMove(move);
        game.getBoard().setLastMove(notatedMove);
        //STILL TODO: UPDATE GAME STATUS AND CHECK FOR CHECK, MATE, STALEMATE, ETC.
        if(notatedMove.contains("+")){
            game.setStatus(GameStatus.CHECK);
        }
        else if(notatedMove.contains("#")){
            game.setStatus(GameStatus.CHECKMATE);
            //TODO: CHECKMATE ENDGAME STUFF IDK IF NEEDED
        }
        //else if() stalemate stuff
        else{
            game.setStatus(GameStatus.ONGOING);
        }
        return "Move made successfully and added to move history";
    }

}
