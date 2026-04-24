package com.example.demo.service;

import com.example.demo.model.Computer;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@Service
public class XmlExportService {

    public void exportToXml(List<Computer> computers) {
        try (PrintWriter writer = new PrintWriter("faktura.xml")) {
            writer.println("<faktura>");
            for(Computer computer : computers) {
                writer.println("  <komputer>");
                writer.println("    <nazwa>" + computer.getName() + "</nazwa>");
                writer.println("    <data_ksiegowania>" + computer.getDate() + "</data_ksiegowania>");
                writer.println("    <koszt_USD>" + computer.getCostUsd() + "</koszt_USD>");
                writer.println("    <koszt_PLN>" + computer.getCostPln() + "</koszt_PLN>");
                writer.println("  </komputer>");
            }
            writer.println("</faktura>");
        } catch (Exception e) {
            System.out.println("Failed to write XML file.");
            e.printStackTrace();
        }
    }
}
