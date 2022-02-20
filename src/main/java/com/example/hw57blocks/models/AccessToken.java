package com.example.hw57blocks.models;

public class AccessToken {
    private String sessionToken;

    public AccessToken(String token) {
        this.sessionToken = token;
    }

    public String getSessionToken() {
        return this.sessionToken;
    }
}
