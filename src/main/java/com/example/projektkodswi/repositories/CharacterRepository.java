package com.example.projektkodswi.repositories;

import com.example.projektkodswi.entities.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Character, String> {
    Optional<Character> findByCharacterName(String characterName);
    boolean existsByCharacterName(String characterName);
}
