package com.example.projektkodswi.dto;

import com.example.projektkodswi.entities.Rarity;

public class SkinDTO {
    private String skinId;
    private String skinName;
    private String skinDescription;
    private double price;
    private Rarity rarity;

    public SkinDTO() {
    }

    public SkinDTO(String skinId, String skinName, String skinDescription, double price, Rarity rarity) {
        this.skinId = skinId;
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
}
