package com.chess.chess_poker.Controller;

import com.chess.chess_poker.DTOs.MoveRequest;
import com.chess.chess_poker.BoardInfo.Game;
import com.chess.chess_poker.Service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.List;
//import java.util.HashMap;




@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameService gameService;
    @PostMapping("/create")
    public String createGame(){
        return gameService.createGame();
    }
    @PostMapping("/move")
    public String makeMove(@RequestBody MoveRequest request){
        return gameService.makeMove(request);
    }
    @GetMapping("/{gameID}")
    public String getGame(@PathVariable String gameID){
        Game game = gameService.findGame(gameID);
        if(game == null){
            return "Game not found";
        }
        else{
            return game.getBoard().boardToString();
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
