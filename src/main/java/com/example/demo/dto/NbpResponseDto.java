package com.example.demo.dto;

import java.util.List;


public class NbpResponseDto {
    private String code;
    private List<RateDto> rates;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<RateDto> getRates() {
        return rates;
    }

    public void setRates(List<RateDto> rates) {
        this.rates = rates;
    }
}