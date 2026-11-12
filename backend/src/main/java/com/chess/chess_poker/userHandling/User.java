package com.chess.chess_poker.userHandling;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.ArrayList;

import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.expression.spel.ast.TypeCode;

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
    private int gamesWon = 0;
    private int gamesDrawn = 0;
    private int gamesLost = 0;
    private int gamesPlayed = 0;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name="rating_history")
    private ArrayList<Integer> ratingHistory = new ArrayList<>();
    

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
    public int getGamesWon(){return gamesWon;}
    public int getGamesDrawn(){return gamesDrawn;}
    public int getGamesLost(){return gamesLost;}
    public int getGamesPlayed(){return gamesPlayed;}
    public ArrayList<Integer> getRatingHistory(){return ratingHistory;}
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
    public void setGamesWon(int gw){gamesWon = gw;}
    public void setGamesDrawn(int gd){gamesDrawn = gd;}
    public void setGamesLost(int gl){gamesLost = gl;}
    public void setGamesPlayed(int gp){gamesPlayed = gp;}
    public void setratingHistory(ArrayList<Integer> r){ratingHistory = r;}
    public void addGameToWon(){gamesWon++;}
    public void addGameToDrawn(){gamesDrawn++;}
    public void addGameToLost(){gamesLost++;}
    public void addGameToPlayed(){gamesPlayed++;}
    public void addRatingToHistory(int r){ratingHistory.add(r);}

    

}
