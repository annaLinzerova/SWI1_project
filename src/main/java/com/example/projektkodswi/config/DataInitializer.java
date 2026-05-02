package com.example.projektkodswi.config;

import com.example.projektkodswi.entities.Character;
import com.example.projektkodswi.entities.Dlc;
import com.example.projektkodswi.entities.Player;
import com.example.projektkodswi.entities.Skin;
import com.example.projektkodswi.repositories.CharacterRepository;
import com.example.projektkodswi.repositories.DlcRepository;
import com.example.projektkodswi.repositories.PlayerRepository;
import com.example.projektkodswi.repositories.SkinRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class DataInitializer {

    private static class CharacterInfo {
        String name;
        String description;

        CharacterInfo(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    private static final List<CharacterInfo> GAME_CHARACTERS = List.of(
        new CharacterInfo("Nicolas McCartney", "Male, 32 years old, Scottish."),
        new CharacterInfo("Luke Noshida", "Male, 25 years old, Japanese."),
        new CharacterInfo("Emily Luca", "Female, 28 years old, Italian."),
        new CharacterInfo("Monica Smith", "Female, 35 years old, American."),
        new CharacterInfo("Taras Pullman", "Male, 40 years old, Ukrainian."),
        new CharacterInfo("Kimiko Lorona", "Female, 22 years old, Mexican-Japanese."),
        new CharacterInfo("Hugo Oneil", "Male, 29 years old, Irish."),
        new CharacterInfo("John Roberts", "Male, 45 years old, British."),
        new CharacterInfo("Julia Kala", "Female, 31 years old, Polish."),
        new CharacterInfo("Robert Reeds", "Male, 38 years old, Canadian."),
        new CharacterInfo("Lucille Jones", "Female, 27 years old, Australian.")
    );

    @Bean
    CommandLineRunner seedData(
        PlayerRepository playerRepository,
        SkinRepository skinRepository,
        DlcRepository dlcRepository,
        CharacterRepository characterRepository,
        PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if (playerRepository.count() == 0) {
                playerRepository.save(new Player("demo-player", passwordEncoder.encode("demo123"), "demo@example.com"));
            }

            syncCharacters(characterRepository, dlcRepository);

            // Fetch existing skins and check if we need to add the new ones
            List<Skin> existingSkins = skinRepository.findAll();
            List<String> existingSkinNames = existingSkins.stream()
                    .map(Skin::getSkinName)
                    .collect(Collectors.toList());

            List<Skin> targetSkins = List.of(
                new Skin("Street Singer", "Street Singer skin."),
                new Skin("Bounty Hunter", "Bounty Hunter skin."),
                new Skin("Summer Dress", "Summer Dress skin."),
                new Skin("Holy Attire", "Holy Attire skin."),
                new Skin("Randez Vous", "Randez Vous skin."),
                new Skin("Masquarade", "Masquarade skin."),
                new Skin("Morning Glory", "Morning Glory skin."),
                new Skin("New Year´s festivities", "New Year´s festivities skin."),
                new Skin("Wheel of Fortune", "Wheel of Fortune skin."),
                new Skin("Song of Ice and Fire", "Song of Ice and Fire skin.")
            );

            List<String> targetSkinNames = targetSkins.stream()
                    .map(Skin::getSkinName)
                    .collect(Collectors.toList());

            // Remove skins that are in DB but NOT in our target list
            for (Skin skin : existingSkins) {
                if (!targetSkinNames.contains(skin.getSkinName())) {
                    skinRepository.delete(skin);
                }
            }

            List<Skin> newSkins = new ArrayList<>();
            for (Skin skin : targetSkins) {
                if (!existingSkinNames.contains(skin.getSkinName())) {
                    newSkins.add(skin);
                }
            }
            if (!newSkins.isEmpty()) {
                skinRepository.saveAll(newSkins);
            }

            // Fetch existing dlcs and check if we need to add the new ones
            List<Dlc> existingDlcs = dlcRepository.findAll();
            List<String> existingDlcNames = existingDlcs.stream()
                    .map(Dlc::getDlcName)
                    .collect(Collectors.toList());

            Dlc easterDlc1 = new Dlc("Easter Bunny", "A festive Easter Bunny outfit.");
            Dlc easterDlc2 = new Dlc("Coming of Spring", "Celebrate the arrival of spring.");
            Dlc easterDlc3 = new Dlc("Easter Spirit", "The true spirit of Easter.");

            Dlc schoolDlc1 = new Dlc("Freshman", "The classic freshman look.");
            Dlc schoolDlc2 = new Dlc("Prom Queen", "Ready for the big dance.");
            Dlc schoolDlc3 = new Dlc("School President", "Dressed for leadership.");
            Dlc schoolDlc4 = new Dlc("The Bully", "Tough look for the hallways.");

            Dlc christmasDlc1 = new Dlc("Santa´s Favourite", "Warm and cozy holiday attire.");
            Dlc christmasDlc2 = new Dlc("Santa", "The big man himself.");
            Dlc christmasDlc3 = new Dlc("Little Helper", "Helping out at the North Pole.");

            Dlc weddingDlc1 = new Dlc("Wedding Dress", "A beautiful white gown.");
            Dlc weddingDlc2 = new Dlc("Bridesmaid", "Elegant support for the bride.");
            Dlc weddingDlc3 = new Dlc("Lucky Man", "Sharp suit for the groom.");
            Dlc weddingDlc4 = new Dlc("Honeymoon Suit", "Stylish look for the getaway.");

            List<Dlc> allTargetDlcs = List.of(
                easterDlc1, easterDlc2, easterDlc3,
                schoolDlc1, schoolDlc2, schoolDlc3, schoolDlc4,
                christmasDlc1, christmasDlc2, christmasDlc3,
                weddingDlc1, weddingDlc2, weddingDlc3, weddingDlc4
            );

            List<String> targetDlcNames = allTargetDlcs.stream()
                    .map(Dlc::getDlcName)
                    .collect(Collectors.toList());

            // Remove DLCs that are in DB but NOT in our target list
            for (Dlc dlc : existingDlcs) {
                if (!targetDlcNames.contains(dlc.getDlcName())) {
                    dlc.setCharacters(new ArrayList<>()); // Remove references first
                    dlcRepository.save(dlc);
                    dlcRepository.delete(dlc);
                }
            }

            List<Dlc> newDlcs = new ArrayList<>();
            List<Character> characters = characterRepository.findAll();

            for (Dlc dlc : allTargetDlcs) {
                if (!existingDlcNames.contains(dlc.getDlcName())) {
                    dlc.setCharacters(new ArrayList<>(characters));
                    newDlcs.add(dlc);
                }
            }

            if (!newDlcs.isEmpty()) {
                dlcRepository.saveAll(newDlcs);
            }
        };
    }

    private void syncCharacters(CharacterRepository characterRepository, DlcRepository dlcRepository) {
        List<Character> existingCharacters = characterRepository.findAll();
        
        boolean alreadySynced = existingCharacters.size() == GAME_CHARACTERS.size()
            && existingCharacters.stream().allMatch(character ->
                GAME_CHARACTERS.stream().anyMatch(info ->
                    info.name.equals(character.getCharacterName()) &&
                    info.description.equals(character.getCharacterDescription())
                )
            );

        if (alreadySynced) {
            return;
        }

        List<Dlc> existingDlcs = dlcRepository.findAll();
        for (Dlc dlc : existingDlcs) {
            dlc.setCharacters(new ArrayList<>());
        }
        dlcRepository.saveAll(existingDlcs);

        characterRepository.deleteAll();
        characterRepository.flush();

        List<Character> charactersToSave = GAME_CHARACTERS.stream()
            .map(info -> new Character(info.name, info.description))
            .toList();
            
        List<Character> savedCharacters = characterRepository.saveAll(charactersToSave);

        // Re-associate new characters with existing DLCs
        for (Dlc dlc : existingDlcs) {
            dlc.setCharacters(new ArrayList<>(savedCharacters));
        }
        dlcRepository.saveAll(existingDlcs);
    }
}
