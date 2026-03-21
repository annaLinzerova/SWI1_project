package com.example.projektkodswi.controllers;


import com.example.projektkodswi.entities.Order;
import com.example.projektkodswi.entities.Player;
import com.example.projektkodswi.repositories.OrderRepository;
import com.example.projektkodswi.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Add UUID for unique usernames.
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public String test() {
        Player player = new Player();
        player.setUsername("testuser-" + UUID.randomUUID());
        player.setPassword("password123");
        player.setEmail("test@example.com");

        playerRepository.save(player);

        return "Hello from Test Controller";
    }

    @GetMapping("/users")
    public List<Player> getAllUsers() {
        return playerRepository.findAll();
    }

    @GetMapping("/create-user-with-orders")
    public String createUserWithOrders() {
        Player player = new Player();
        player.setUsername("user-" + UUID.randomUUID().toString().substring(0, 8));
        player.setPassword("$2a$10$slYQmyNdGzin7olVN3p5be4nxQjV2d9dGvQGAlt28WjLdMZGd7rOG");

        Random random = new Random();
        player.setEmail(player.getUsername() + "@example.com");

        player = playerRepository.save(player);

        Order order1 = new Order();
        order1.setPlayer(player);
        order1.setOrderDate(LocalDateTime.now().minusDays(random.nextInt(30)));
        order1.setOrderDescription("Software licenses");
        orderRepository.save(order1);

        Order order2 = new Order();
        order2.setPlayer(player);
        order2.setOrderDate(LocalDateTime.now().minusDays(random.nextInt(30)));
        order2.setOrderDescription("Mobile devices");
        orderRepository.save(order2);

        return "Player created with ID: " + player.getPlayerId() + " and 2 orders added";
    }

}
