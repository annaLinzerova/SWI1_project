package com.example.projektkodswi.dto;

import com.example.projektkodswi.entities.Rarity;

public class DlcDTO {
    private String dlcId;
    private String dlcName;
    private String dlcDescription;
    private double price;
    private Rarity rarity;

    public DlcDTO() {
    }

    public DlcDTO(String dlcId, String dlcName, String dlcDescription, double price, Rarity rarity) {
        this.dlcId = dlcId;
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
}
