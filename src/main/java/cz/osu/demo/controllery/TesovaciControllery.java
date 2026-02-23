package cz.osu.swidemo.controllers;


import cz.osu.demo.entities.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cz.osu.demo.repositary.TestovaciRepositories;

// Add UUID for unique usernames.
import java.util.UUID;

@RestController
@RequestMapping("/api/test")
public class TesovaciControllery {
    @Autowired
    private TestovaciRepositories userRepository;

    @GetMapping
    public String test() {
        Player player = new Player();
        player.setUsername("testuser-" + UUID.randomUUID());
        player.setPassword("password123");
        player.setCurrency(25);
        player.setEmail("test@example.com");

        userRepository.save(player);

        return "Hello from Test Controller";


    }


}
