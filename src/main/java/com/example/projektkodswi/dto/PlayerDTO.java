package com.example.projektkodswi.dto;

public class PlayerDTO {
    private String playerId;
    private String username;
    private String password;
    private String email;
    private double currency;

    public PlayerDTO() {
    }

    public PlayerDTO(String playerId, String username, String password, String email) {
        this.playerId = playerId;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public PlayerDTO(String playerId, String username, String password, String email, double currency) {
        this.playerId = playerId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.currency = currency;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getCurrency() {
        return currency;
    }

    public void setCurrency(double currency) {
        this.currency = currency;
    }
}
