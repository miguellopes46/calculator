package com.example.rest.controllers;

import com.example.rest.dtos.OperationRequestDTO;
import com.example.rest.dtos.OperationResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/calculator")
public class CalculatorController {

    private final KafkaTemplate<String, OperationRequestDTO> kafkaTemplate;
    private final Map<String, CompletableFuture<OperationResponseDTO>> responseFutures = new ConcurrentHashMap<>();

    public CalculatorController(KafkaTemplate<String, OperationRequestDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/{operation}")
    public ResponseEntity<?> calculate(@PathVariable String operation, @RequestParam BigDecimal a, @RequestParam BigDecimal b) {

        String requestId = UUID.randomUUID().toString();
        CompletableFuture<OperationResponseDTO> future = new CompletableFuture<>();
        responseFutures.put(requestId, future);
        System.out.println("--------Sending requestId: " + requestId);

        OperationRequestDTO request = new OperationRequestDTO(a, b, operation, requestId);
        kafkaTemplate.send("calc_requests", request);
        System.out.println("---------Sent request to calc_requests--------: " + request);

        try {
            OperationResponseDTO response = future.get(5, TimeUnit.SECONDS);
            if (response.getError() != null) {
                return ResponseEntity.badRequest()
                        .header("X-Request-ID", requestId)
                        .body(Map.of("error", response.getError()));
            }
            return ResponseEntity.ok()
                    .header("X-Request-ID", requestId)
                    .body(Map.of("result", response.getResult()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
                    .header("X-Request-ID", requestId)
                    .body(Map.of("error", "Timeout waiting for result"));
        } finally {
            responseFutures.remove(requestId);
        }
    }

    @KafkaListener(topics = "calc_responses")
    public void listen(OperationResponseDTO response) {
        CompletableFuture<OperationResponseDTO> future = responseFutures.get(response.getRequestId());
        if (future != null) future.complete(response);
    }
}

