package com.example.projektkodswi.dto;

public class CharacterDTO {
    private String characterId;
    private String characterName;
    private String characterDescription;

    public CharacterDTO() {
    }

    public CharacterDTO(String characterId, String characterName, String characterDescription) {
        this.characterId = characterId;
        this.characterName = characterName;
        this.characterDescription = characterDescription;
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
}
