package com.example.demo.dto;


import java.math.BigDecimal;

public record RateDto(
        String effectiveDate,
        BigDecimal mid)
{}
