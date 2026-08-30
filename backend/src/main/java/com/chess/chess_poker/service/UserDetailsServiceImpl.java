package com.chess.chess_poker.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chess.chess_poker.repositories.UserRepository;
import com.chess.chess_poker.userHandling.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User was not found."));
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
        .password(user.getPassword()).authorities(Collections.emptyList()).build();
    }
}
