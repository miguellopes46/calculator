package com.example.calculator.consumer;

import com.example.calculator.dtos.OperationRequestDTO;
import com.example.calculator.dtos.OperationResponseDTO;
import com.example.calculator.services.CalculatorService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Consumer {

    private final CalculatorService calculatorService;
    private final KafkaTemplate<String, OperationResponseDTO> kafkaTemplate;

    public Consumer(CalculatorService calculatorService, KafkaTemplate<String, OperationResponseDTO> kafkaTemplate) {
        this.calculatorService = calculatorService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "calc_requests", groupId = "calculator-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(OperationRequestDTO request) {
        System.out.println("📥 [calculator] Received request: " + request);

        OperationResponseDTO response = new OperationResponseDTO();
        response.setRequestId(request.getRequestId());

        try {
            BigDecimal result = calculatorService.calculate(request.getOperation(), request.getA(), request.getB());
            response.setResult(result);
        } catch (Exception e) {
            response.setError(e.getMessage());
        }

        System.out.println("----Sending response: ----" + response);
        kafkaTemplate.send("calc_responses", response);
    }
}