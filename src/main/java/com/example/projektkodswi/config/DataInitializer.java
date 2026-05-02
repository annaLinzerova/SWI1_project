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

    private static final List<String> GAME_CHARACTER_NAMES = List.of(
        "Nicolas McCartney",
        "Luke Noshida",
        "Emily Luca",
        "Monica Smith",
        "Taras Pullman",
        "Kimiko Lorona",
        "Hugo Oneil",
        "John Roberts",
        "Julia Kala",
        "Robert Reeds",
        "Lucille Jones"
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
            List<String> existingSkinNames = skinRepository.findAll().stream()
                    .map(Skin::getSkinName)
                    .collect(Collectors.toList());

            List<Skin> newSkins = new ArrayList<>();
            List<Skin> targetSkins = List.of(
                new Skin("Crimson Phantom", "Legendary red-black armor set."),
                new Skin("Arctic Pulse", "Cold-toned tactical skin."),
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

            for (Skin skin : targetSkins) {
                if (!existingSkinNames.contains(skin.getSkinName())) {
                    newSkins.add(skin);
                }
            }

            if (!newSkins.isEmpty()) {
                skinRepository.saveAll(newSkins);
            }

            // Fetch existing dlcs and check if we need to add the new ones
            List<String> existingDlcNames = dlcRepository.findAll().stream()
                    .map(Dlc::getDlcName)
                    .collect(Collectors.toList());

            List<Dlc> newDlcs = new ArrayList<>();
            List<Character> characters = characterRepository.findAll();

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
        boolean alreadySynced = existingCharacters.size() == GAME_CHARACTER_NAMES.size()
            && existingCharacters.stream().allMatch(character ->
                GAME_CHARACTER_NAMES.contains(character.getCharacterName()));

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

        List<Character> charactersToSave = GAME_CHARACTER_NAMES.stream()
            .map(name -> new Character(name, "Playable game character."))
            .toList();
        List<Character> savedCharacters = characterRepository.saveAll(charactersToSave);

        // Re-associate new characters with existing DLCs
        for (Dlc dlc : existingDlcs) {
            dlc.setCharacters(new ArrayList<>(savedCharacters));
        }
        dlcRepository.saveAll(existingDlcs);
    }
}
