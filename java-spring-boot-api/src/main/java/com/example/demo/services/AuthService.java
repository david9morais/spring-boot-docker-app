package com.example.demo.services;

import com.example.demo.repositories.UserRepository;
import com.example.demo.security.jwt.JwtTokenProvider;
import com.example.demo.valueobject.security.AccountCredentialsVO;
import com.example.demo.valueobject.security.TokenVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository repository;

    @SuppressWarnings("rawtypes")
    public ResponseEntity signIn(AccountCredentialsVO data) {
        if(checkIfParamsIsNotNUll(data)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

        try {
            var username = data.getUsername();
            var password = data.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            var user = repository.findByUsername(username);

            var tokenResponse = new TokenVO();
            if(Objects.nonNull(user)) {
                tokenResponse = jwtTokenProvider.createAccessToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException("Username " + username + " not found!");
            }

            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid Username/Password supplied!");
        }
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity refreshToken(String username, String refreshToken) {
        if(checkIfParamsIsNotNUll(username, refreshToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

        var user = repository.findByUsername(username);
        var tokenResponse = new TokenVO();

        if(Objects.nonNull(user)) {
            tokenResponse = jwtTokenProvider.refreshToken(refreshToken);
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }

        return ResponseEntity.ok(tokenResponse);
    }

    private boolean checkIfParamsIsNotNUll(AccountCredentialsVO data) {
        return Objects.isNull(data)
                || Objects.isNull(data.getUsername())
                || data.getUsername().isBlank()
                || Objects.isNull(data.getPassword())
                || data.getPassword().isBlank();
    }

    private static boolean checkIfParamsIsNotNUll(String username, String refreshToken) {
        return Objects.isNull(refreshToken) || refreshToken.isBlank()
                || Objects.isNull(username) || username.isBlank();
    }
}
