package com.example.projektkodswi.controllers;

import com.example.projektkodswi.dto.DlcDTO;
import com.example.projektkodswi.dto.OrderProfileDTO;
import com.example.projektkodswi.dto.PlayerDTO;
import com.example.projektkodswi.dto.PlayerProfileDTO;
import com.example.projektkodswi.dto.SkinDTO;
import com.example.projektkodswi.entities.Order;
import com.example.projektkodswi.entities.Player;
import com.example.projektkodswi.repositories.OrderRepository;
import com.example.projektkodswi.repositories.PlayerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerRepository playerRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    public PlayerController(PlayerRepository playerRepository, OrderRepository orderRepository, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<PlayerDTO> getPlayers() {
        return playerRepository.findAll().stream()
            .map(this::toDto)
            .toList();
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<?> getPlayer(@PathVariable String playerId) {
        return playerRepository.findById(playerId)
            .<ResponseEntity<?>>map(player -> ResponseEntity.ok(toDto(player)))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Player not found with ID: " + playerId));
    }

    @GetMapping("/{playerId}/profile")
    public ResponseEntity<?> getPlayerProfile(@PathVariable String playerId) {
        System.out.println("== DEBUG PROFILE =========================================");
        System.out.println("== DEBUG PROFILE ==> Received request for playerId: " + playerId);

        Optional<Player> playerOptional = playerRepository.findById(playerId);
        if (playerOptional.isEmpty()) {
            System.out.println("== DEBUG PROFILE ==> Player not found in database.");
            System.out.println("==========================================================");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Player not found with ID: " + playerId);
        }

        Player player = playerOptional.get();
        System.out.println("== DEBUG PROFILE ==> Found player username: " + player.getUsername());

        List<Order> playerOrders = orderRepository.findByPlayer_PlayerId(playerId);
        System.out.println("== DEBUG PROFILE ==> Found " + playerOrders.size() + " orders for this player.");

        List<OrderProfileDTO> orderProfiles = playerOrders.stream().map(order -> {
            List<SkinDTO> skinDTOs = order.getSkins().stream()
                .map(skin -> new SkinDTO(skin.getSkinId(), skin.getSkinName(), skin.getSkinDescription()))
                .collect(Collectors.toList());
            
            List<DlcDTO> dlcDTOs = order.getDlcs().stream()
                .map(dlc -> new DlcDTO(dlc.getDlcId(), dlc.getDlcName(), dlc.getDlcDescription()))
                .collect(Collectors.toList());

            return new OrderProfileDTO(
                order.getOrderId(),
                order.getOrderDate(),
                order.getOrderDescription(),
                skinDTOs,
                dlcDTOs
            );
        }).collect(Collectors.toList());

        PlayerProfileDTO profileDTO = new PlayerProfileDTO(
            player.getPlayerId(),
            player.getUsername(),
            player.getEmail(),
            orderProfiles
        );
        
        System.out.println("== DEBUG PROFILE ==> Successfully built profile DTO.");
        System.out.println("==========================================================");
        return ResponseEntity.ok(profileDTO);
    }

    @PostMapping
    public ResponseEntity<?> createPlayer(@RequestBody PlayerDTO request) {
        if (request == null || isBlank(request.getUsername()) || isBlank(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("username and password are required");
        }

        if (playerRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Username already exists");
        }

        if (!isBlank(request.getEmail()) && playerRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Email already exists");
        }

        Player player = new Player();
        player.setUsername(request.getUsername().trim());
        player.setPassword(passwordEncoder.encode(request.getPassword()));
        player.setEmail(isBlank(request.getEmail()) ? null : request.getEmail().trim());

        Player savedPlayer = playerRepository.save(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(savedPlayer));
    }

    private PlayerDTO toDto(Player player) {
        return new PlayerDTO(
            player.getPlayerId(),
            player.getUsername(),
            null,
            player.getEmail()
        );
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
