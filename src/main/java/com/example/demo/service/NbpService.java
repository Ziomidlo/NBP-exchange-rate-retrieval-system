package com.example.demo.service;

import com.example.demo.dto.NbpResponseDto;
import com.example.demo.model.Computer;
import com.example.demo.repository.ComputerRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class NbpService {


    private final String nbpApiUrl;
    private final ComputerRepository computerRepository;
    private final RestTemplate restTemplate;

    public NbpService(ComputerRepository computerRepository, RestTemplate restTemplate, @Value("${nbp.api.url}") String nbpApiUrl) {
        this.computerRepository = computerRepository;
        this.restTemplate = restTemplate;
        this.nbpApiUrl = nbpApiUrl;
    }

    

    public BigDecimal getExchangeRateForDate(LocalDate date) {
        for (int attempt = 0; attempt < 7; attempt++) {
            try {
                String url = nbpApiUrl + "exchangerates/rates/a/usd/" + date.toString() + "/?format=json";
                NbpResponseDto response = restTemplate.getForObject(url, NbpResponseDto.class);
                if (response != null) {
                    return response.rates().getFirst().mid();
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

    public List<Computer> getComputerByName(String search) {
        List<Computer> computers;
        if(search != null && !search.isEmpty()) {
            computers = computerRepository.findByNameContainingIgnoreCase(search);
        } else {
            computers = computerRepository.findAll();
        }
        return computers;
    }
}
