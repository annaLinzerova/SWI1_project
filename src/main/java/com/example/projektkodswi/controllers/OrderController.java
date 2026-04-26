package com.example.projektkodswi.controllers;

import com.example.projektkodswi.dto.DlcDTO;
import com.example.projektkodswi.dto.OrderDTO;
import com.example.projektkodswi.dto.SkinDTO;
import com.example.projektkodswi.entities.Dlc;
import com.example.projektkodswi.entities.Order;
import com.example.projektkodswi.entities.Player;
import com.example.projektkodswi.entities.Skin;
import com.example.projektkodswi.repositories.DlcRepository;
import com.example.projektkodswi.repositories.OrderRepository;
import com.example.projektkodswi.repositories.PlayerRepository;
import com.example.projektkodswi.repositories.SkinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private SkinRepository skinRepository;

    @Autowired
    private DlcRepository dlcRepository;

    @GetMapping
    public List<OrderDTO> getOrders() {
        return orderRepository.findAll().stream()
            .map(this::toDto)
            .toList();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable String orderId) {
        return orderRepository.findById(orderId)
            .<ResponseEntity<?>>map(order -> ResponseEntity.ok(toDto(order)))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Order not found with ID: " + orderId));
    }

    @GetMapping("/player/{playerId}")
    public ResponseEntity<?> getOrdersByPlayerId(@PathVariable String playerId) {
        Optional<Player> playerOptional = playerRepository.findById(playerId);
        if (playerOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Player not found with ID: " + playerId);
        }

        List<OrderDTO> orders = orderRepository.findByPlayer_PlayerId(playerId).stream()
            .map(this::toDto)
            .toList();
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            if (orderRequest == null || orderRequest.getPlayerId() == null || orderRequest.getPlayerId().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("playerId is required");
            }

            Optional<Player> playerOptional = playerRepository.findById(orderRequest.getPlayerId());
            if (playerOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Player not found with ID: " + orderRequest.getPlayerId());
            }

            Order order = new Order();
            order.setPlayer(playerOptional.get());
            order.setOrderDate(resolveOrderDate(orderRequest.getOrderDate()));
            order.setOrderDescription(orderRequest.getOrderDescription());

            if (orderRequest.getSkinIds() != null && !orderRequest.getSkinIds().isEmpty()) {
                List<Skin> skins = new ArrayList<>();
                for (String skinId : orderRequest.getSkinIds()) {
                    Optional<Skin> skinOptional = skinRepository.findById(skinId);
                    if (skinOptional.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Skin not found with ID: " + skinId);
                    }
                    skins.add(skinOptional.get());
                }
                order.setSkins(skins);
            }

            if (orderRequest.getDlcIds() != null && !orderRequest.getDlcIds().isEmpty()) {
                List<Dlc> dlcs = new ArrayList<>();
                for (String dlcId : orderRequest.getDlcIds()) {
                    Optional<Dlc> dlcOptional = dlcRepository.findById(dlcId);
                    if (dlcOptional.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Dlc not found with ID: " + dlcId);
                    }
                    dlcs.add(dlcOptional.get());
                }
                order.setDlcs(dlcs);
            }

            Order savedOrder = orderRepository.save(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(toDto(savedOrder));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating order: " + e.getMessage());
        }
    }

    @PostMapping("/skins")
    public ResponseEntity<?> getSkinsByOrderId(@RequestBody OrderAssetsRequest request) {
        if (request == null || request.getOrderId() == null || request.getOrderId().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("orderId is required");
        }

        Optional<Order> orderOptional = orderRepository.findById(request.getOrderId());
        if (orderOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Order not found with ID: " + request.getOrderId());
        }

        List<SkinDTO> skinDTOs = new ArrayList<>();
        for (Skin skin : orderOptional.get().getSkins()) {
            skinDTOs.add(new SkinDTO(skin.getSkinId(), skin.getSkinName(), skin.getSkinDescription()));
        }

        return ResponseEntity.ok(skinDTOs);
    }

    @PostMapping("/dlcs")
    public ResponseEntity<?> getDlcsByOrderId(@RequestBody OrderAssetsRequest request) {
        if (request == null || request.getOrderId() == null || request.getOrderId().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("orderId is required");
        }

        Optional<Order> orderOptional = orderRepository.findById(request.getOrderId());
        if (orderOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Order not found with ID: " + request.getOrderId());
        }

        List<DlcDTO> dlcDTOs = new ArrayList<>();
        for (Dlc dlc : orderOptional.get().getDlcs()) {
            dlcDTOs.add(new DlcDTO(dlc.getDlcId(), dlc.getDlcName(), dlc.getDlcDescription()));
        }

        return ResponseEntity.ok(dlcDTOs);
    }

    private LocalDateTime resolveOrderDate(LocalDateTime requestedOrderDate) {
        if (requestedOrderDate != null) {
            return requestedOrderDate;
        }
        return LocalDateTime.now();
    }

    private OrderDTO toDto(Order order) {
        return new OrderDTO(
            order.getOrderId(),
            order.getPlayer().getPlayerId(),
            order.getPlayer().getUsername(),
            order.getOrderDate(),
            order.getOrderDescription()
        );
    }

    public static class OrderRequest {
        private String playerId;
        private LocalDateTime orderDate;
        private String orderDescription;
        private List<String> skinIds;
        private List<String> dlcIds;

        public OrderRequest() {
        }

        public String getPlayerId() {
            return playerId;
        }

        public void setPlayerId(String playerId) {
            this.playerId = playerId;
        }

        public LocalDateTime getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(LocalDateTime orderDate) {
            this.orderDate = orderDate;
        }

        public String getOrderDescription() {
            return orderDescription;
        }

        public void setOrderDescription(String orderDescription) {
            this.orderDescription = orderDescription;
        }

        public List<String> getSkinIds() {
            return skinIds;
        }

        public void setSkinIds(List<String> skinIds) {
            this.skinIds = skinIds;
        }

        public List<String> getDlcIds() {
            return dlcIds;
        }

        public void setDlcIds(List<String> dlcIds) {
            this.dlcIds = dlcIds;
        }
    }

    public static class OrderAssetsRequest {
        private String orderId;

        public OrderAssetsRequest() {
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }
}
