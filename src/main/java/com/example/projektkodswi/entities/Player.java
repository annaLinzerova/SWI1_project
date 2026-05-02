package com.example.projektkodswi.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "player_id", nullable = false, updatable = false, length = 36)
    private String playerId;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "currency", nullable = false)
    private double currency; // New field for player currency

    @JsonIgnore
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public Player() {
        this.currency = 0.0; // Default currency for new players
    }

    public Player(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.currency = 0.0; // Default currency for new players
    }

    // Getters and Setters
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
