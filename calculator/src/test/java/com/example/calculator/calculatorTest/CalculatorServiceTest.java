package com.example.calculator.calculatorTest;

import com.example.calculator.services.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.assertj.core.api.Assertions.*;

class CalculatorServiceTest {

    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorService();
    }

    @Test
    void shouldCalculateSum() {
        BigDecimal result = calculatorService.calculate("sum", new BigDecimal("10"), new BigDecimal("5"));
        assertThat(result).isEqualByComparingTo(new BigDecimal("15"));
    }

    @Test
    void shouldCalculateSubtraction() {
        BigDecimal result = calculatorService.calculate("subtraction", new BigDecimal("10"), new BigDecimal("5"));
        assertThat(result).isEqualByComparingTo(new BigDecimal("5"));
    }

    @Test
    void shouldCalculateMultiplication() {
        BigDecimal result = calculatorService.calculate("multiplication", new BigDecimal("10"), new BigDecimal("5"));
        assertThat(result).isEqualByComparingTo(new BigDecimal("50"));
    }

    @Test
    void shouldCalculateDivision() {
        BigDecimal result = calculatorService.calculate("division", new BigDecimal("10"), new BigDecimal("4"));
        BigDecimal expected = new BigDecimal("2.5").round(MathContext.DECIMAL128);
        assertThat(result).isEqualByComparingTo(expected);
    }

    @Test
    void shouldThrowExceptionForInvalidOperation() {
        assertThatThrownBy(() -> calculatorService.calculate("invalid", BigDecimal.ONE, BigDecimal.ONE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid operation");
    }
//----Testing the precision
    @Test
    void shouldHandleDecimalSumPrecisely() {
        BigDecimal a = new BigDecimal("0.1");
        BigDecimal b = new BigDecimal("0.2");
        BigDecimal result = calculatorService.calculate("sum", a, b);
        assertThat(result).isEqualByComparingTo(new BigDecimal("0.3"));
    }

    @Test
    void shouldHandleHighPrecisionDivision() {
        BigDecimal a = new BigDecimal("1");
        BigDecimal b = new BigDecimal("3");
        BigDecimal result = calculatorService.calculate("division", a, b);

        // testing 0,3333
        BigDecimal expected = a.divide(b, MathContext.DECIMAL128);
        assertThat(result).isEqualByComparingTo(expected);
    }

    @Test
    void shouldHandleTrailingZerosCorrectly() {
        BigDecimal a = new BigDecimal("2.00");
        BigDecimal b = new BigDecimal("3.000");
        BigDecimal result = calculatorService.calculate("multiplication", a, b);

        assertThat(result).isEqualByComparingTo(new BigDecimal("6"));
        assertThat(result.stripTrailingZeros()).isEqualByComparingTo(new BigDecimal("6"));
    }

}