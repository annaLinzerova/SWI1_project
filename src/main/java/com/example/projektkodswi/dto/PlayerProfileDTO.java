package com.example.projektkodswi.dto;

import java.util.List;

public class PlayerProfileDTO {
    private String playerId;
    private String username;
    private String email;
    private List<OrderProfileDTO> orders;

    public PlayerProfileDTO() {
    }

    public PlayerProfileDTO(String playerId, String username, String email, List<OrderProfileDTO> orders) {
        this.playerId = playerId;
        this.username = username;
        this.email = email;
        this.orders = orders;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<OrderProfileDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderProfileDTO> orders) {
        this.orders = orders;
    }
}
