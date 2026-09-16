package com.chess.chess_poker.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chess.chess_poker.repositories.UserRepository;
import com.chess.chess_poker.userHandling.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController 
@RequestMapping("/Leaderboard")    
public class LeaderBoardController {
    @Autowired 
    private UserRepository repository;

    @GetMapping()
    public List<Map<String, Object>> getLeaderboard(){
        List<User> users = repository.findAllByOrderByRatingDesc();
        List<Map<String, Object>> result = new ArrayList<>();
        int rank = 1;
        for(User user : users){
            Map<String, Object> element = new HashMap<>();
            element.put("rank", rank++);
            element.put("username", user.getUsername());
            element.put("rating", user.getRating());
            element.put("gamesWon", user.getGamesWon());
            element.put("gamesDrawn", user.getGamesDrawn());
            element.put("gamesLost", user.getGamesLost());
            element.put("gamesPlayed", user.getGamesPlayed());
            result.add(element);
        }
        return result;

    }
}
