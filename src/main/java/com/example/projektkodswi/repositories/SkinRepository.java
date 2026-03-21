package com.example.projektkodswi.repositories;

import com.example.projektkodswi.entities.Skin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkinRepository extends JpaRepository<Skin, String> {
    Optional<Skin> findBySkinName(String skinName);
    boolean existsBySkinName(String skinName);
}
