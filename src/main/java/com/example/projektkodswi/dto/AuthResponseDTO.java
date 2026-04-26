package com.example.projektkodswi.dto;

public class AuthResponseDTO {
    private String message;
    private PlayerDTO player;

    public AuthResponseDTO() {
    }

    public AuthResponseDTO(String message, PlayerDTO player) {
        this.message = message;
        this.player = player;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PlayerDTO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDTO player) {
        this.player = player;
    }
}
