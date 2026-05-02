package com.example.projektkodswi.dto;

public class SkinDTO {
    private String skinId;
    private String skinName;
    private String skinDescription;
    private double price; // New field for skin price

    public SkinDTO() {
    }

    public SkinDTO(String skinId, String skinName, String skinDescription) {
        this.skinId = skinId;
        this.skinName = skinName;
        this.skinDescription = skinDescription;
        this.price = 0.0; // Default price
    }

    public SkinDTO(String skinId, String skinName, String skinDescription, double price) {
        this.skinId = skinId;
        this.skinName = skinName;
        this.skinDescription = skinDescription;
        this.price = price;
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
}
