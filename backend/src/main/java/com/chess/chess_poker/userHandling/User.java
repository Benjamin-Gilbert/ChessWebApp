package com.chess.chess_poker.userHandling;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private int rating = 100;
    private int gamesPlayed = 0;

    public Long getId(){
        return id;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public int getRating(){
        return rating;
    }
    public int getGamesPlayed(){return gamesPlayed;}
    public void setId(Long i){
        id = i;
    }
    public void setUsername(String u){
        username = u;
    }
    public void setPassword(String p){
        password = p;
    }
    public void setRating(int r){
        rating = r;
    }
    public void setGamesPlayed(int gp){gamesPlayed = gp;}
    public void addGameToPlayed(){gamesPlayed++;}
    

}
