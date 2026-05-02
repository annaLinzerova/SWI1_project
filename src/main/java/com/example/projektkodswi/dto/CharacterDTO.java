package com.example.projektkodswi.dto;

public class CharacterDTO {
    private String characterId;
    private String characterName;
    private String characterDescription;
    private double price;

    public CharacterDTO() {
    }

    public CharacterDTO(String characterId, String characterName, String characterDescription) {
        this.characterId = characterId;
        this.characterName = characterName;
        this.characterDescription = characterDescription;
        this.price = 0.0;
    }

    public CharacterDTO(String characterId, String characterName, String characterDescription, double price) {
        this.characterId = characterId;
        this.characterName = characterName;
        this.characterDescription = characterDescription;
        this.price = price;
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getCharacterDescription() {
        return characterDescription;
    }

    public void setCharacterDescription(String characterDescription) {
        this.characterDescription = characterDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
