package com.example.projektkodswi.config;

import com.example.projektkodswi.entities.Character;
import com.example.projektkodswi.entities.Dlc;
import com.example.projektkodswi.entities.Player;
import com.example.projektkodswi.entities.Rarity;
import com.example.projektkodswi.entities.Skin;
import com.example.projektkodswi.repositories.CharacterRepository;
import com.example.projektkodswi.repositories.DlcRepository;
import com.example.projektkodswi.repositories.PlayerRepository;
import com.example.projektkodswi.repositories.SkinRepository;
import jakarta.transaction.Transactional;
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
        double price;

        CharacterInfo(String name, String description, double price) {
            this.name = name;
            this.description = description;
            this.price = price;
        }
    }

    private static final List<CharacterInfo> GAME_CHARACTERS = List.of(
        new CharacterInfo("Nicolas McCartney", "Male, 32 years old, Scottish.", 150.0),
        new CharacterInfo("Luke Noshida", "Male, 25 years old, Japanese.", 120.0),
        new CharacterInfo("Emily Luca", "Female, 28 years old, Italian.", 130.0),
        new CharacterInfo("Monica Smith", "Female, 35 years old, American.", 160.0),
        new CharacterInfo("Taras Pullman", "Male, 40 years old, Ukrainian.", 140.0),
        new CharacterInfo("Kimiko Lorona", "Female, 22 years old, Mexican-Japanese.", 110.0),
        new CharacterInfo("Hugo Oneil", "Male, 29 years old, Irish.", 125.0),
        new CharacterInfo("John Roberts", "Male, 45 years old, British.", 170.0),
        new CharacterInfo("Julia Kala", "Female, 31 years old, Polish.", 135.0),
        new CharacterInfo("Robert Reeds", "Male, 38 years old, Canadian.", 155.0),
        new CharacterInfo("Lucille Jones", "Female, 27 years old, Australian.", 115.0)
    );

    @Bean
    @Transactional
    CommandLineRunner seedData(
        PlayerRepository playerRepository,
        SkinRepository skinRepository,
        DlcRepository dlcRepository,
        CharacterRepository characterRepository,
        PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if (playerRepository.count() == 0) {
                Player demoPlayer = new Player("demo-player", passwordEncoder.encode("demo123"), "demo@example.com");
                demoPlayer.setCurrency(1000.0);
                playerRepository.save(demoPlayer);
            } else {
                playerRepository.findByUsername("demo-player").ifPresent(player -> {
                    if (player.getCurrency() == 0.0) {
                        player.setCurrency(1000.0);
                        playerRepository.save(player);
                    }
                });
            }

            syncCharacters(characterRepository, dlcRepository);

            List<Skin> existingSkins = skinRepository.findAll();
            List<String> existingSkinNames = existingSkins.stream()
                    .map(Skin::getSkinName)
                    .collect(Collectors.toList());

            List<Skin> targetSkins = List.of(
                new Skin("Street Singer", "Street Singer skin.", 50.0, Rarity.COMMON),
                new Skin("Bounty Hunter", "Bounty Hunter skin.", 75.0, Rarity.UNCOMMON),
                new Skin("Summer Dress", "Summer Dress skin.", 40.0, Rarity.COMMON),
                new Skin("Holy Attire", "Holy Attire skin.", 60.0, Rarity.RARE),
                new Skin("Randez Vous", "Randez Vous skin.", 80.0, Rarity.EVENT),
                new Skin("Masquarade", "Masquarade skin.", 70.0, Rarity.UNCOMMON),
                new Skin("Morning Glory", "Morning Glory skin.", 45.0, Rarity.COMMON),
                new Skin("New Year´s festivities", "New Year´s festivities skin.", 90.0, Rarity.EVENT),
                new Skin("Wheel of Fortune", "Wheel of Fortune skin.", 100.0, Rarity.LEGENDARY),
                new Skin("Song of Ice and Fire", "Song of Ice and Fire skin.", 120.0, Rarity.LEGENDARY)
            );

            List<String> targetSkinNames = targetSkins.stream()
                    .map(Skin::getSkinName)
                    .collect(Collectors.toList());

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

            List<Dlc> existingDlcs = dlcRepository.findAll();
            List<String> existingDlcNames = existingDlcs.stream()
                    .map(Dlc::getDlcName)
                    .collect(Collectors.toList());

            Dlc easterDlc1 = new Dlc("Easter Bunny", "A festive Easter Bunny outfit.", 200.0, Rarity.EVENT);
            Dlc easterDlc2 = new Dlc("Coming of Spring", "Celebrate the arrival of spring.", 180.0, Rarity.EVENT);
            Dlc easterDlc3 = new Dlc("Easter Spirit", "The true spirit of Easter.", 220.0, Rarity.EVENT);

            Dlc schoolDlc1 = new Dlc("Freshman", "The classic freshman look.", 150.0, Rarity.COMMON);
            Dlc schoolDlc2 = new Dlc("Prom Queen", "Ready for the big dance.", 250.0, Rarity.RARE);
            Dlc schoolDlc3 = new Dlc("School President", "Dressed for leadership.", 200.0, Rarity.UNCOMMON);
            Dlc schoolDlc4 = new Dlc("The Bully", "Tough look for the hallways.", 100.0, Rarity.COMMON);

            Dlc christmasDlc1 = new Dlc("Santa´s Favourite", "Warm and cozy holiday attire.", 300.0, Rarity.LEGENDARY);
            Dlc christmasDlc2 = new Dlc("Santa", "The big man himself.", 350.0, Rarity.LEGENDARY);
            Dlc christmasDlc3 = new Dlc("Little Helper", "Helping out at the North Pole.", 175.0, Rarity.RARE);

            Dlc weddingDlc1 = new Dlc("Wedding Dress", "A beautiful white gown.", 400.0, Rarity.LEGENDARY);
            Dlc weddingDlc2 = new Dlc("Bridesmaid", "Elegant support for the bride.", 280.0, Rarity.RARE);
            Dlc weddingDlc3 = new Dlc("Lucky Man", "Sharp suit for the groom.", 320.0, Rarity.EVENT);
            Dlc weddingDlc4 = new Dlc("Honeymoon Suit", "Stylish look for the getaway.", 290.0, Rarity.UNCOMMON);

            List<Dlc> allTargetDlcs = List.of(
                easterDlc1, easterDlc2, easterDlc3,
                schoolDlc1, schoolDlc2, schoolDlc3, schoolDlc4,
                christmasDlc1, christmasDlc2, christmasDlc3,
                weddingDlc1, weddingDlc2, weddingDlc3, weddingDlc4
            );

            List<String> targetDlcNames = allTargetDlcs.stream()
                    .map(Dlc::getDlcName)
                    .collect(Collectors.toList());

            for (Dlc dlc : existingDlcs) {
                if (!targetDlcNames.contains(dlc.getDlcName())) {
                    dlc.setCharacters(new ArrayList<>());
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
                    info.description.equals(character.getCharacterDescription()) &&
                    info.price == character.getPrice()
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
            .map(info -> new Character(info.name, info.description, info.price))
            .toList();
            
        List<Character> savedCharacters = characterRepository.saveAll(charactersToSave);

        for (Dlc dlc : existingDlcs) {
            dlc.setCharacters(new ArrayList<>(savedCharacters));
        }
        dlcRepository.saveAll(existingDlcs);
    }
}
