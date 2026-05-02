package com.example.projektkodswi.controllers;

import com.example.projektkodswi.dto.AuthResponseDTO;
import com.example.projektkodswi.dto.LoginRequestDTO;
import com.example.projektkodswi.dto.PlayerDTO;
import com.example.projektkodswi.entities.Player;
import com.example.projektkodswi.repositories.PlayerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    private static final double INITIAL_NEW_PLAYER_CURRENCY = 500.0; 

    public AuthController(PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody PlayerDTO request) {
        if (request == null || isBlank(request.getUsername()) || isBlank(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("username and password are required");
        }

        if (playerRepository.existsByUsername(request.getUsername().trim())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Username already exists");
        }

        if (!isBlank(request.getEmail()) && playerRepository.existsByEmail(request.getEmail().trim())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Email already exists");
        }

        Player player = new Player();
        player.setUsername(request.getUsername().trim());
        player.setPassword(passwordEncoder.encode(request.getPassword()));
        player.setEmail(isBlank(request.getEmail()) ? null : request.getEmail().trim());
        player.setCurrency(INITIAL_NEW_PLAYER_CURRENCY); // Set initial currency for new players

        Player savedPlayer = playerRepository.save(player);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new AuthResponseDTO("Registration successful", toDto(savedPlayer)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        if (request == null || isBlank(request.getUsernameOrEmail()) || isBlank(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("usernameOrEmail and password are required");
        }

        String loginValue = request.getUsernameOrEmail().trim();
        Optional<Player> playerOptional = loginValue.contains("@")
            ? playerRepository.findByEmail(loginValue)
            : playerRepository.findByUsername(loginValue);

        if (playerOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid username/email or password");
        }

        Player player = playerOptional.get();
        if (!passwordEncoder.matches(request.getPassword(), player.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid username/email or password");
        }

        return ResponseEntity.ok(new AuthResponseDTO("Login successful", toDto(player)));
    }

    private PlayerDTO toDto(Player player) {
        return new PlayerDTO(player.getPlayerId(), player.getUsername(), null, player.getEmail(), player.getCurrency());
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
