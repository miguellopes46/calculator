package com.example.calculator.dtos;

import java.math.BigDecimal;

public class OperationRequestDTO {
    private BigDecimal a;
    private BigDecimal b;
    private String operation;
    private String requestId;

    public OperationRequestDTO() {}

    public OperationRequestDTO(BigDecimal a, BigDecimal b, String operation, String requestId) {
        this.a = a;
        this.b = b;
        this.operation = operation;
        this.requestId = requestId;
    }

    // Getters and Setters
    public BigDecimal getA() { return a; }
    public void setA(BigDecimal a) { this.a = a; }

    public BigDecimal getB() { return b; }
    public void setB(BigDecimal b) { this.b = b; }

    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }

    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }

    @Override
    public String toString() {
        return "OperationRequestDTO{" +
                "a=" + a +
                ", b=" + b +
                ", operation='" + operation + '\'' +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}
