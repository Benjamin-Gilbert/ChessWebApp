package com.chess.chess_poker.controller;

import com.chess.chess_poker.dtos.CreateGameRequest;
import com.chess.chess_poker.dtos.GameResponse;
import com.chess.chess_poker.dtos.JoinGameRequest;
import com.chess.chess_poker.dtos.MoveRequest;
import com.chess.chess_poker.engine.GenerateMoves;
import com.chess.chess_poker.repositories.UserRepository;
import com.chess.chess_poker.boardinfo.Game;
import com.chess.chess_poker.service.GameService;
import com.chess.chess_poker.userHandling.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.Optional;
import java.net.Authenticator;
import java.util.List;
//import java.util.HashMap;
import org.springframework.web.bind.annotation.RequestParam;




//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameService gameService;
    @Autowired private UserRepository userRepository;
    @PostMapping("/create")
    public GameResponse createGame(@RequestBody CreateGameRequest createGameRequest, Authentication authentication ){
        String username = authentication.getName();
        User creator = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found."));
        return gameService.createGame(creator.getUsername(), creator.getRating(), createGameRequest.getGameMode(), createGameRequest.getMinutes(), createGameRequest.getSeconds(), createGameRequest.getColor());
    }
    @PostMapping("/join/{gameID}")
    public GameResponse joinGame(@PathVariable String gameID, Authentication authentication){
        String username = authentication.getName();
        User joiner = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Game not found."));
        return gameService.joinGame(gameID, joiner.getUsername(), joiner.getRating());
        }
    @PostMapping("/move")
    public GameResponse makeMove(@RequestBody MoveRequest request, Authentication authentication){
        Game game = gameService.findGame(request.getGameID());
        return new GameResponse(gameService.makeMove(request), game.getBoard().getBoard(), game.getLastMoveNotated(), game.getMoveList(), game.getBoard().getTurn(), game.getWhiteClock(), game.getBlackClock(), game.getLastMoveTime());
    }
    @GetMapping("/open")
    public ResponseEntity<?> getOpenGames() {
        List<Game> games = gameService.getOpenGames();
        return ResponseEntity.ok(games);
        }
    
    @GetMapping("/{gameID}")
    public GameResponse getGame(@PathVariable String gameID){
        Game game = gameService.findGame(gameID);
        if(game == null){
            return new GameResponse();
        }
        else{
            game.setWhiteClock(gameService.getLiveWhiteClock(game));
            game.setBlackClock(gameService.getLiveBlackClock(game));
            game.setLastMoveTime(System.currentTimeMillis());
            return game.returnAsResponse();
        }

    }
    @GetMapping("/allgames")
    public Map<String, Game> getAllGames() {
        return gameService.getAllGames();
    }
    @GetMapping("/{gameID}/moveList")
    public List<String> getMoveList(@PathVariable String gameID) {
        return gameService.findGame(gameID).getMoveHistory();
    }
    @PostMapping("/generateMoves")
    public GameResponse generatePieceMoves(@RequestBody MoveRequest request, Authentication authentication){
        Game game = gameService.findGame(request.getGameID());
        return new GameResponse(GenerateMoves.legalMovesForPiece(request.getToRow(), request.getToCol(), game.getBoard()));
    }
    @PostMapping("/resign")
    public GameResponse postMethodName(@RequestBody MoveRequest request, Authentication authentication) {
        Game game = gameService.findGame(request.getGameID());
        int[] ratings = new int[4];
        if(game.getBoard().getTurn().equals("white")){
            ratings = gameService.whiteLoss(game);
            return new GameResponse(ratings[0], ratings[1], ratings[2], ratings[3]);
        }
        else{
            ratings = gameService.blackLoss(game);
            return new GameResponse(ratings[0], ratings[1], ratings[2], ratings[3]);
        }
    }
    @PostMapping("/checkMate")
    public GameResponse checkmate(@RequestBody MoveRequest request, Authentication authentication){
        Game game = gameService.findGame(request.getGameID());
        //User userWhite = userRepository.findByUsername(game.getPlayerWhite()).orElseThrow(() -> new RuntimeException("user not found."));
        //User userBlack = userRepository.findByUsername(game.getPlayerBlack()).orElseThrow(() -> new RuntimeException("user not found."));
        int[] ratings = new int[4];
        if(request.getWinner().equals("white")){
            ratings = gameService.blackLoss(game);
        }
        else{
            ratings = gameService.whiteLoss(game);
        }
        return new GameResponse(ratings[0], ratings[1], ratings[2], ratings[3]);
    }
    @PostMapping("/draw")
    public GameResponse draw(@RequestBody MoveRequest request, Authentication authentication) {
        Game game = gameService.findGame(request.getGameID());
        //User userWhite = userRepository.findByUsername(game.getPlayerWhite()).orElseThrow(() -> new RuntimeException("user not found."));
        //User userBlack = userRepository.findByUsername(game.getPlayerBlack()).orElseThrow(() -> new RuntimeException("user not found."));
        int[] ratings = new int[4];
        ratings = gameService.drawnGame(game);
        return new GameResponse(ratings[0], ratings[1], ratings[2], ratings[3]);
    }
    @PostMapping("/timeout")
    public GameResponse timeout(@RequestBody MoveRequest request, Authentication authentication){
        Game game = gameService.findGame(request.getGameID());
        int[] ratings = new int[4];
        if(game.getBlackClock() <= 0){
            ratings = gameService.blackLoss(game);
        }
        else if(game.getWhiteClock() <= 0){
            ratings = gameService.whiteLoss(game);
        }
        return new GameResponse(ratings[0], ratings[1], ratings[2], ratings[3]);
    }
    
    

}
