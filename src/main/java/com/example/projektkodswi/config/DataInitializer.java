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

            if (skinRepository.count() == 0) {
                skinRepository.saveAll(List.of(
                    new Skin("Crimson Phantom", "Legendary red-black armor set."),
                    new Skin("Arctic Pulse", "Cold-toned tactical skin.")
                ));
            }

            if (dlcRepository.count() == 0) {
                List<Character> characters = characterRepository.findAll();

                Dlc firstDlc = new Dlc("Origins Pack", "Starter expansion with two extra missions.");
                firstDlc.setCharacters(characters);

                Dlc secondDlc = new Dlc("Night Raid", "Late-game story and combat expansion.");
                secondDlc.setCharacters(characters.isEmpty() ? List.of() : List.of(characters.get(0)));

                dlcRepository.saveAll(List.of(firstDlc, secondDlc));
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
        characterRepository.saveAll(charactersToSave);
    }
}
