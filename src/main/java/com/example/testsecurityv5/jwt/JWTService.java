package com.example.testsecurityv5.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {

    String extractUsername(String token);
    String generateToken(UserDetails userDetails);
}
