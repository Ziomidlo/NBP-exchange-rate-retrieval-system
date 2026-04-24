package com.example.demo.controller;

import com.example.demo.model.Computer;
import com.example.demo.repository.ComputerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebController {
    private final ComputerRepository computerRepository;

    public WebController(ComputerRepository computerRepository) {
        this.computerRepository = computerRepository;
    }

    @GetMapping("/")
    public String showComputers(Model model, @RequestParam(required = false) String search) {
        List<Computer> computers;
        if(search != null && !search.isEmpty()) {
            computers = computerRepository.findByNameContainingIgnoreCase(search);
        } else {
            computers = computerRepository.findAll();
        }
        model.addAttribute("computers", computers);
        return "index";
    }
}
