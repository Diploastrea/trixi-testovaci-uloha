package com.example.trixihomework;

import com.example.trixihomework.services.XMLService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URL;

@SpringBootApplication
@AllArgsConstructor
public class TrixiHomeworkApplication implements CommandLineRunner {
    private static final String url = "https://www.smartform.cz/download/kopidlno.xml.zip";
    private static final String destinationPath = "src/kopidlno.xml.zip";
    private final XMLService xmlService;

    public static void main(String[] args) {
        SpringApplication.run(TrixiHomeworkApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        xmlService.download(new URL(url), destinationPath);
//        System.out.println("Downloading finished!");
        xmlService.processFile(destinationPath);
        System.out.println("Finished processing XML file.");
    }
}
