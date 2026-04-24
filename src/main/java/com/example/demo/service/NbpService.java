package com.example.demo.service;

import com.example.demo.dto.NbpResponseDto;
import com.example.demo.model.Computer;
import com.example.demo.repository.ComputerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class NbpService {

    private ComputerRepository computerRepository;

    public NbpService(ComputerRepository computerRepository) {
        this.computerRepository = computerRepository;
    }

    public BigDecimal getExchangeRateForDate(LocalDate date) {
        RestTemplate restTemplate = new RestTemplate();
        for (int attempt = 0; attempt < 7; attempt++) {
            try {
                String url = "http://api.nbp.pl/api/exchangerates/rates/a/usd/" + date.toString() + "/?format=json";
                NbpResponseDto response = restTemplate.getForObject(url, NbpResponseDto.class);
                if (response != null) {
                    return response.getRates().getFirst().getMid();
                }
            } catch (HttpClientErrorException.NotFound e) {
                date = date.minusDays(1);
            }
        }
        return null;
    }

    public void processAndSaveComputer(Computer computer) {
        BigDecimal exchangeRate = getExchangeRateForDate(computer.getDate());

        if(exchangeRate != null) {
            BigDecimal plnCost = computer.getCostUsd().multiply(exchangeRate);

            computer.setCostPln(plnCost);

            computerRepository.save(computer);
        }

    }
}
