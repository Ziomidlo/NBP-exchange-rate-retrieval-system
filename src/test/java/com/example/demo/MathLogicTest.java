package com.example.demo;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathLogicTest {
    @Test
    public void shouldCorrectlyCalculateUsdCost() {
        BigDecimal costPln = new BigDecimal("1000.00");
        BigDecimal exchangeRate = new BigDecimal("4.00");

        BigDecimal calculatedUsd = costPln.divide(exchangeRate, 2, RoundingMode.HALF_UP);

        BigDecimal expectedUsd = new BigDecimal("250.00");
        assertEquals(expectedUsd, calculatedUsd, "The PLN to USD calculation is wrong!");

    }
}
