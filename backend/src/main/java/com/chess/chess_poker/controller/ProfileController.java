package com.chess.chess_poker.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chess.chess_poker.repositories.UserRepository;
import com.chess.chess_poker.userHandling.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam(defaultValue = "chess") String mode, Authentication authentication){
        if(!mode.equals("chess")){return ResponseEntity.badRequest().body("Unsupported mode.");}
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found."));
        Map<String, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("rating", user.getRating());
        response.put("gamesPlayed", user.getGamesPlayed());
        response.put("wins", user.getGamesWon());
        response.put("losses", user.getGamesLost());
        response.put("draws", user.getGamesDrawn());
        response.put("ratingHistory", user.getRatingHistory());
        return ResponseEntity.ok(response);
    }
}
