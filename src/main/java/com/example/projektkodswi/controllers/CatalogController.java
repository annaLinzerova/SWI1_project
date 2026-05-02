package com.example.projektkodswi.controllers;

import com.example.projektkodswi.dto.CharacterDTO;
import com.example.projektkodswi.dto.DlcDTO;
import com.example.projektkodswi.dto.SkinDTO;
import com.example.projektkodswi.entities.Character;
import com.example.projektkodswi.entities.Dlc;
import com.example.projektkodswi.entities.Skin;
import com.example.projektkodswi.repositories.CharacterRepository;
import com.example.projektkodswi.repositories.DlcRepository;
import com.example.projektkodswi.repositories.SkinRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CatalogController {

    private final SkinRepository skinRepository;
    private final DlcRepository dlcRepository;
    private final CharacterRepository characterRepository;

    public CatalogController(
        SkinRepository skinRepository,
        DlcRepository dlcRepository,
        CharacterRepository characterRepository
    ) {
        this.skinRepository = skinRepository;
        this.dlcRepository = dlcRepository;
        this.characterRepository = characterRepository;
    }

    @GetMapping("/skins")
    public List<SkinDTO> getSkins() {
        return skinRepository.findAll().stream()
            .map(this::toDto)
            .toList();
    }

    @GetMapping("/dlcs")
    public List<DlcDTO> getDlcs() {
        return dlcRepository.findAll().stream()
            .map(this::toDto)
            .toList();
    }

    @GetMapping("/characters")
    public List<CharacterDTO> getCharacters() {
        return characterRepository.findAll().stream()
            .map(this::toDto)
            .toList();
    }

    private SkinDTO toDto(Skin skin) {
        return new SkinDTO(skin.getSkinId(), skin.getSkinName(), skin.getSkinDescription(), skin.getPrice());
    }

    private DlcDTO toDto(Dlc dlc) {
        return new DlcDTO(dlc.getDlcId(), dlc.getDlcName(), dlc.getDlcDescription(), dlc.getPrice());
    }

    private CharacterDTO toDto(Character character) {
        return new CharacterDTO(
            character.getCharacterId(),
            character.getCharacterName(),
            character.getCharacterDescription(),
            character.getPrice()
        );
    }
}
