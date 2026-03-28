package com.example.projektkodswi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "characters")
public class Character {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "character_id", nullable = false, updatable = false, length = 36)
    private String characterId;

    @Column(name = "character_name", nullable = false, length = 200)
    private String characterName;

    @Column(name = "character_description", length = 500)
    private String characterDescription;

    @JsonIgnore
    @ManyToMany(mappedBy = "characters")
    private List<Dlc> dlcs = new ArrayList<>();

    public Character() {
    }

    public Character(String characterName, String characterDescription) {
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

    public List<Dlc> getDlcs() {
        return dlcs;
    }

    public void setDlcs(List<Dlc> dlcs) {
        this.dlcs = dlcs;
    }
}
