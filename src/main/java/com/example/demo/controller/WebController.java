package com.example.demo.controller;

import com.example.demo.model.Computer;
import com.example.demo.repository.ComputerRepository;
import com.example.demo.service.NbpService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class WebController {
    private final NbpService nbpService;

    public WebController( NbpService nbpService) {
        this.nbpService = nbpService;
    }

    @GetMapping("/")
    public String showComputers(Model model, @RequestParam(required = false) String search) {
        List<Computer> computers = nbpService.getComputerByName(search);
        model.addAttribute("computers", computers);
        return "index";
    }
}
