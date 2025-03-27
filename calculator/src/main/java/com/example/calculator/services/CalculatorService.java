package com.example.calculator.services;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.MathContext;

@Service
public class CalculatorService {

    public BigDecimal calculate(String operation, BigDecimal a, BigDecimal b) {
        System.out.println("-----calculating------------");
        return switch (operation.toLowerCase()) {
            case "sum" -> a.add(b);
            case "subtraction" -> a.subtract(b);
            case "multiplication" -> a.multiply(b);
            case "division" -> a.divide(b, MathContext.DECIMAL128);
            default -> throw new IllegalArgumentException("Invalid operation: " + operation);
        };
    }
}
