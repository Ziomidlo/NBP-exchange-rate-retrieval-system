package com.example.demo.dto;

import java.util.List;


public record NbpResponseDto (
        String code,
        List<RateDto> rates
) {}