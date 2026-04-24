package com.example.demo.controller;

import com.example.demo.model.Computer;
import com.example.demo.repository.ComputerRepository;
import com.example.demo.service.NbpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ComputerRestController {
    private final NbpService nbpService;
    private final ComputerRepository computerRepository;

    public ComputerRestController(NbpService nbpService, ComputerRepository computerRepository) {
        this.nbpService = nbpService;
        this.computerRepository = computerRepository;
    }

    @GetMapping("/computers")
    public List<Computer> getAllComputers() {
        return computerRepository.findAll();
    }

    @GetMapping("/computers/{id}")
    public ResponseEntity<Computer> getComputer(@PathVariable  Long id) {
        Optional<Computer> optionalComputer = computerRepository.findById(id);
        if(optionalComputer.isPresent()) {
            return ResponseEntity.ok(optionalComputer.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/computers")
    public Computer createComputer(@RequestBody Computer newComputer) {
        BigDecimal exchangeRate = nbpService.getExchangeRateForDate(newComputer.getDate());
        BigDecimal usdCost = newComputer.getCostPln().divide(exchangeRate, 2, RoundingMode.HALF_UP);
        newComputer.setCostUsd(usdCost);

        return computerRepository.save(newComputer);
    }

    @DeleteMapping("/computers/{id}")
    public String deleteComputer(@PathVariable Long id) {
        computerRepository.deleteById(id);
        return "Computer with id: " + id + " has been deleted.";
    }

    @PutMapping("/computers/{id}")
    public ResponseEntity<Computer> updateComputer(@RequestBody Computer updatedComputer, @PathVariable Long id) {
        Optional<Computer> existingComputerOpt = computerRepository.findById(id);
        if(existingComputerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Computer existingComputer = existingComputerOpt.get();

        existingComputer.setName(updatedComputer.getName());
        existingComputer.setDate(updatedComputer.getDate());
        existingComputer.setCostPln(updatedComputer.getCostPln());
        BigDecimal exchangeRate = nbpService.getExchangeRateForDate(updatedComputer.getDate());
        BigDecimal usdCost = updatedComputer.getCostPln().divide(exchangeRate, 2, RoundingMode.HALF_UP);
        existingComputer.setCostUsd(usdCost);

        Computer savedComputer = computerRepository.save(existingComputer);

        return ResponseEntity.ok(savedComputer);
    }

}
