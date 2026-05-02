package com.example.projektkodswi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "skins")
public class Skin {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "skin_id", nullable = false, updatable = false, length = 36)
    private String skinId;

    @Column(name = "skin_name", nullable = false, length = 200)
    private String skinName;

    @Column(name = "skin_description", length = 500)
    private String skinDescription;

    @Column(name = "price", nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "rarity", nullable = false)
    private Rarity rarity;

    @JsonIgnore
    @ManyToMany(mappedBy = "skins")
    private List<Order> orders = new ArrayList<>();

    public Skin() {
        this.rarity = Rarity.COMMON;
    }

    public Skin(String skinName, String skinDescription, double price) {
        this.skinName = skinName;
        this.skinDescription = skinDescription;
        this.price = price;
        this.rarity = Rarity.COMMON;
    }

    public Skin(String skinName, String skinDescription, double price, Rarity rarity) {
        this.skinName = skinName;
        this.skinDescription = skinDescription;
        this.price = price;
        this.rarity = rarity;
    }

    public String getSkinId() {
        return skinId;
    }

    public void setSkinId(String skinId) {
        this.skinId = skinId;
    }

    public String getSkinName() {
        return skinName;
    }

    public void setSkinName(String skinName) {
        this.skinName = skinName;
    }

    public String getSkinDescription() {
        return skinDescription;
    }

    public void setSkinDescription(String skinDescription) {
        this.skinDescription = skinDescription;
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
}
