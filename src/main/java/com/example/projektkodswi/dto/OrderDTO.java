package com.example.projektkodswi.dto;

import java.time.LocalDateTime;

public class OrderDTO {
    private String orderId;
    private String playerId;
    private String username;
    private LocalDateTime orderDate;
    private String orderDescription;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, String playerId, String username, LocalDateTime orderDate, String orderDescription) {
        this.orderId = orderId;
        this.playerId = playerId;
        this.username = username;
        this.orderDate = orderDate;
        this.orderDescription = orderDescription;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }
}
