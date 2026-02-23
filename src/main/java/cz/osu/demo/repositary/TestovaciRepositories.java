package cz.osu.demo.repositary;

import cz.osu.demo.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestovaciRepositories extends JpaRepository<Player, String> {
    Optional<Player> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
