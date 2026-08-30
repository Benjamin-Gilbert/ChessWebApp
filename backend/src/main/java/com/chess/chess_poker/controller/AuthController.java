package com.chess.chess_poker.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chess.chess_poker.dtos.AuthRequest;
import com.chess.chess_poker.repositories.UserRepository;
import com.chess.chess_poker.security.JwtUtil;
import com.chess.chess_poker.userHandling.User;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class AuthController {
   @Autowired private UserRepository userRepository;
   @Autowired private PasswordEncoder passwordEncoder;
   @Autowired private JwtUtil jwtUtil;

   @PostMapping("/register")
   public ResponseEntity<?> register(@RequestBody AuthRequest request) {
       if(userRepository.findByUsername(request.getUsername()).isPresent()){
        return ResponseEntity.badRequest().body("Username is already taken.");
       }
       User user = new User();
       user.setUsername(request.getUsername());
       String hashed = passwordEncoder.encode(request.getPassword());
       user.setPassword(hashed);
       userRepository.save(user);
       return ResponseEntity.ok(Map.of("token", jwtUtil.generateToken(user.getUsername())));
   }

   @PostMapping("/login")
   public ResponseEntity<?> login(@RequestBody AuthRequest request) {
       User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
       if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
        return ResponseEntity.status(401).body("Incorrect username or password.");
       }
       return ResponseEntity.ok(Map.of("token", jwtUtil.generateToken(user.getUsername())));
   }
   
   
   
   
}
