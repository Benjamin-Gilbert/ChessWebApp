package com.chess.chess_poker.dtos;

public class AuthRequest {
    private String username;
    private String password;

    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public void setUsername(String u){
        username = u;
    }
    public void setPassword(String p){
        password = p;
    }
}
