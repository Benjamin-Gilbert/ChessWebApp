package com.chess.chess_poker.service;
import com.chess.chess_poker.ChessPokerApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chess.chess_poker.boardinfo.*;
import com.chess.chess_poker.engine.*;
import com.chess.chess_poker.repositories.UserRepository;
import com.chess.chess_poker.userHandling.User;
import com.chess.chess_poker.dtos.CreateGameRequest;
import com.chess.chess_poker.dtos.GameResponse;
import com.chess.chess_poker.dtos.MoveRequest;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.List;

@Service
public class GameService {
    private Map<String, Game> games;
    @Autowired private UserRepository userRepository;
    public GameService(ChessPokerApplication chessPokerApplication){
        games = new HashMap<>();
    }

    public Game findGame(String gameID){
        return games.get(gameID);
    }
    public Map<String, Game> getAllGames(){
        return games;
    }
    public List<Game> getOpenGames(){
        return games.values().stream().filter(g -> g.getPlayerBlack().equals("waiting")).collect(Collectors.toList());
    }
    public GameResponse createGame(String playerWhite, int playerWhiteRating, String gameMode, int minutes, int seconds, String color){
        Game game = new Game(playerWhite, playerWhiteRating, gameMode, minutes, seconds, color);
        games.put(game.getGameID(), game);
        GameResponse gameResponse = new GameResponse(game.getGameID(), game.getPlayerWhite(), game.getPlayerBlack(), game.getPlayerWhiteRating(), game.getPlayerBlackRating(), game.getBoard().getBoard());
        return gameResponse;
    }
    public GameResponse joinGame(String gameID, String username, int rating){
        Game game = games.get(gameID);
        if(game == null){return new GameResponse("Game not found.");}
        game.joinGame(username, rating);
        return new GameResponse(game.getGameID(), game.getPlayerWhite(), game.getPlayerBlack(), game.getPlayerWhiteRating(), game.getPlayerBlackRating(), game.getBoard().getBoard());
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
            return "Move made successfully and added to move history";
        }
        else if(piece.getName().equals("king") && move.getToCol() - move.getFromCol() == -2){
            side = "queenside";
            game.getBoard().castle(move, piece, side);
            game.addMoveToHistory("O-O-O");
            game.setLastMove(move);
            game.getBoard().setLastMove("O-O-O");
            return "Move made successfully and added to move history";
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
                if(request.getPromotion().equals("queen")){
                    game.getBoard().promote(piece, "queen");
                    promotion = "queen";
                }
                else if(request.getPromotion().equals("bishop")){
                    game.getBoard().promote(piece, "bishop");
                    promotion = "bishop";
                }
                else if(request.getPromotion().equals("knight")){
                    game.getBoard().promote(piece, "knight");
                    promotion = "knight";
                }
                else if(request.getPromotion().equals("rook")){
                    game.getBoard().promote(piece, "rook");
                    promotion = "rook";
                }
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
        else if(CheckDetector.detectStaleMate(game.getBoard(), game.getBoard().getBoard()[game.getBoard().findPiece("white", "king")[0]][game.getBoard().findPiece("white", "king")[1]])){
            game.setStatus(GameStatus.STALEMATE);
            game.addMoveToHistory("1/2");
        }
        else if(CheckDetector.detectStaleMate(game.getBoard(), game.getBoard().getBoard()[game.getBoard().findPiece("black", "king")[0]][game.getBoard().findPiece("black", "king")[1]])){
            game.setStatus(GameStatus.STALEMATE);
            game.addMoveToHistory("1/2");
        }
        else{
            game.setStatus(GameStatus.ONGOING);
        }
        return "Move made successfully and added to move history";
    }
    public void updateRating(User winner, User loser, boolean draw){
        int KWinner = 10;
        int KLoser = 10;
        double EWinner = 1/(1+Math.pow(10, ((double)(loser.getRating() - winner.getRating())/400)));
        if(winner.getGamesPlayed() < 10){KWinner = 40;}
        else if(winner.getRating() < 2400){KWinner = 20;}
        if(loser.getGamesPlayed() < 10){KLoser = 40;}
        else if(loser.getRating() < 2400){KLoser = 20;}
        double ELoser = 1.0 - EWinner;
        if(!draw){
        winner.setRating(winner.getRating() + (int) Math.round(KWinner * (1 - EWinner)));
        if(loser.getRating() + (int) Math.round(KLoser * (0-ELoser)) < 100){loser.setRating(100);}
        else{
        loser.setRating(loser.getRating() + (int) Math.round(KLoser * (0 - ELoser)));
        }}
        else{
            winner.setRating(winner.getRating() + (int) Math.round(KWinner * (.5 - EWinner)));
            if(loser.getRating() + (int) Math.round(KLoser * (.5 - ELoser)) < 100){loser.setRating(100);}
            else{
                loser.setRating(loser.getRating() + (int) Math.round(KLoser * (.5 - ELoser)));
            }
        }
        winner.addGameToPlayed();
        loser.addGameToPlayed();
        
        userRepository.save(winner);
        userRepository.save(loser);
    }

    public int[] whiteLoss(Game game){
        int[] result = new int[4];
        User userWhite = userRepository.findByUsername(game.getPlayerWhite()).orElseThrow(() -> new RuntimeException("no user found."));
        User userBlack = userRepository.findByUsername(game.getPlayerBlack()).orElseThrow(() -> new RuntimeException("no user found."));
        int oldWhiteRating = userWhite.getRating();
        int oldBlackRating = userBlack.getRating();
        userWhite.addGameToLost();
        userBlack.addGameToWon();
        updateRating(userBlack, userWhite, false);
        result[0] = userWhite.getRating();
        result[1] = userBlack.getRating();
        result[2] = Math.abs(userWhite.getRating() - oldWhiteRating);
        result[3] = Math.abs(userBlack.getRating() - oldBlackRating);
        return result;
    }
    public int[] blackLoss(Game game){
        int[] result = new int[4];
        User userWhite = userRepository.findByUsername(game.getPlayerWhite()).orElseThrow(() -> new RuntimeException("no user found."));
        User userBlack = userRepository.findByUsername(game.getPlayerBlack()).orElseThrow(() -> new RuntimeException("no user found."));
        int oldWhiteRating = userWhite.getRating();
        int oldBlackRating = userBlack.getRating();
        userWhite.addGameToWon();
        userBlack.addGameToLost();
        updateRating(userWhite, userBlack, false);
        result[0] = userWhite.getRating();
        result[1] = userBlack.getRating();
        result[2] = Math.abs(userWhite.getRating() - oldWhiteRating);
        result[3] = Math.abs(userBlack.getRating() - oldBlackRating);
        return result;
    }
    public int[] drawnGame(Game game){
        int[] result = new int[4];
        User userWhite = userRepository.findByUsername(game.getPlayerWhite()).orElseThrow(() -> new RuntimeException("no user found."));
        User userBlack = userRepository.findByUsername(game.getPlayerBlack()).orElseThrow(() -> new RuntimeException("no user found."));
        int oldWhiteRating = userWhite.getRating();
        int oldBlackRating = userBlack.getRating();
        userWhite.addGameToDrawn();
        userBlack.addGameToDrawn();
        updateRating(userWhite, userBlack, true);
        result[0] = userWhite.getRating();
        result[1] = userBlack.getRating();
        result[2] = Math.abs(userWhite.getRating() - oldWhiteRating);
        result[3] = Math.abs(userBlack.getRating() - oldBlackRating);
        return result;
    }

}
