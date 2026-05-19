package com.chess.chess_poker.controller;

import com.chess.chess_poker.dtos.CreateGameRequest;
import com.chess.chess_poker.dtos.GameResponse;
import com.chess.chess_poker.dtos.MoveRequest;
import com.chess.chess_poker.boardinfo.Game;
import com.chess.chess_poker.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.List;
//import java.util.HashMap;



@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameService gameService;
    @PostMapping("/create")
    public GameResponse createGame(@RequestBody CreateGameRequest createGameRequest){
        return gameService.createGame(createGameRequest);
    }
    @PostMapping("/move")
    public GameResponse makeMove(@RequestBody MoveRequest request){
        Game game = gameService.findGame(request.getGameID());
        return new GameResponse(gameService.makeMove(request), game.getBoard().getBoard(), game.getLastMoveNotated());
    }
    @GetMapping("/{gameID}")
    public GameResponse getGame(@PathVariable String gameID){
        Game game = gameService.findGame(gameID);
        if(game == null){
            return new GameResponse();
        }
        else{
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

}
