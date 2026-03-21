package com.example.projektkodswi.repositories;

import com.example.projektkodswi.entities.Dlc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DlcRepository extends JpaRepository<Dlc, String> {
    Optional<Dlc> findByDlcName(String dlcName);
    boolean existsByDlcName(String dlcName);
}
