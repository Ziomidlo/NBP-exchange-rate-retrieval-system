package com.example.demo.bootstrap;

import com.example.demo.model.Computer;
import com.example.demo.repository.ComputerRepository;
import com.example.demo.service.NbpService;
import com.example.demo.service.XmlExportService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private final NbpService nbpService;
    private final ComputerRepository computerRepository;
    private final XmlExportService xmlExportService;

    public DataLoader(NbpService nbpService, ComputerRepository computerRepository, XmlExportService xmlExportService) {
        this.nbpService = nbpService;
        this.computerRepository = computerRepository;
        this.xmlExportService = xmlExportService;
    }

    @Override
    public void run(String... args) throws Exception{
        System.out.println("Starting Data Loader...");

        if (computerRepository.count() == 0) {
            Computer acer = new Computer();
            acer.setName("ACER Aspire");
            acer.setDate(LocalDate.of(2026, 1, 5));
            acer.setCostUsd(new BigDecimal("345.00"));

            Computer dell = new Computer();
            dell.setName("DELL Latitude");
            dell.setDate(LocalDate.of(2026, 1, 11));
            dell.setCostUsd(new BigDecimal("543"));

            Computer hp = new Computer();
            hp.setName("HP Victus");
            hp.setDate(LocalDate.of(2026, 1, 19));
            hp.setCostUsd(new BigDecimal("346"));

            nbpService.processAndSaveComputer(acer);
            nbpService.processAndSaveComputer(dell);
            nbpService.processAndSaveComputer(hp);

            System.out.println("All computers have been processed and saved to database.");
        }


        List<Computer> allComputers = computerRepository.findAll();

        xmlExportService.exportToXml(allComputers);


    }
}
