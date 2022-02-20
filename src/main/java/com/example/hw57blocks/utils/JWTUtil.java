package com.example.hw57blocks.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class JWTUtil {
    private static final SecretKey SIGN_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public static String generateToken(Map<String, Object> claims, long lifeTimeMillis) {
        long currentTime = System.currentTimeMillis();
        long expTime = currentTime + lifeTimeMillis;

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expTime))
                .setSubject("login token for authentication")
                .setIssuer("graphql API")
                .signWith(SIGN_KEY)
                .compact();
    }
}
