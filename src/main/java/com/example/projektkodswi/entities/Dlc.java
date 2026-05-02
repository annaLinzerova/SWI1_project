package com.example.projektkodswi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dlcs")
public class Dlc {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "dlc_id", nullable = false, updatable = false, length = 36)
    private String dlcId;

    @Column(name = "dlc_name", nullable = false, length = 200)
    private String dlcName;

    @Column(name = "dlc_description", length = 500)
    private String dlcDescription;

    @Column(name = "price", nullable = false)
    private double price;

    @Enumerated(EnumType.STRING) // Store enum as String in DB
    @Column(name = "rarity", nullable = false)
    private Rarity rarity; // New field for DLC rarity

    @JsonIgnore
    @ManyToMany(mappedBy = "dlcs")
    private List<Order> orders = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "dlc_characters",
        joinColumns = @JoinColumn(name = "dlc_id"),
        inverseJoinColumns = @JoinColumn(name = "character_id")
    )
    private List<Character> characters = new ArrayList<>();

    public Dlc() {
        this.rarity = Rarity.COMMON;
    }

    public Dlc(String dlcName, String dlcDescription, double price) {
        this.dlcName = dlcName;
        this.dlcDescription = dlcDescription;
        this.price = price;
        this.rarity = Rarity.COMMON;
    }

    public Dlc(String dlcName, String dlcDescription, double price, Rarity rarity) {
        this.dlcName = dlcName;
        this.dlcDescription = dlcDescription;
        this.price = price;
        this.rarity = rarity;
    }

    public String getDlcId() {
        return dlcId;
    }

    public void setDlcId(String dlcId) {
        this.dlcId = dlcId;
    }

    public String getDlcName() {
        return dlcName;
    }

    public void setDlcName(String dlcName) {
        this.dlcName = dlcName;
    }

    public String getDlcDescription() {
        return dlcDescription;
    }

    public void setDlcDescription(String dlcDescription) {
        this.dlcDescription = dlcDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }
}
