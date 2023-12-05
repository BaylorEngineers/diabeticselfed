package com.baylor.diabeticselfed;

import com.baylor.diabeticselfed.auth.AuthenticationService;
import com.baylor.diabeticselfed.auth.RegisterRequest;
import com.baylor.diabeticselfed.entities.ContentArea;
import com.baylor.diabeticselfed.entities.Module;
import com.baylor.diabeticselfed.entities.Question;
import com.baylor.diabeticselfed.repository.ContentAreaRepository;
import com.baylor.diabeticselfed.repository.ModuleRepository;
import com.baylor.diabeticselfed.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;

import java.util.ArrayList;

import static com.baylor.diabeticselfed.entities.Role.ADMIN;
import static com.baylor.diabeticselfed.entities.Role.CLINICIAN;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class DiabeticSelfEdApplication {

    @Autowired
    private ContentAreaRepository contentAreaRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    public static void main(String[] args) {
        SpringApplication.run(DiabeticSelfEdApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service
    ) {
        return args -> {
            var admin = RegisterRequest.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@mail.com")
                    .password("password")
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token: " + service.register(admin).getAccessToken());

            var clinician = RegisterRequest.builder()
                    .firstname("Clinician")
                    .lastname("Sky")
                    .email("clinician@mail.com")
                    .password("password")
                    .role(CLINICIAN)
                    .build();
            System.out.println("Clinician token: " + service.register(clinician).getAccessToken());
        };
    }

    @Bean
    public CommandLineRunner contentAreaSetup() {
        return args -> {
            ContentArea healthyEating = createContentArea("Healthy Eating");
            createContentArea("Physical Activity");
            createContentArea("Stress Reduction");

            HashMap<String, String[]> moduleDetails = new HashMap<>();
            System.out.println(System.getProperty("user.dir"));
            readModuleKeywords("/diabeticselfed/contents/PreventT2Keywords.txt", moduleDetails);

            moduleDetails.forEach((moduleName, details) -> {
                String description = details[0];
                String keywords = details[1];
//                System.out.println("Module: " + moduleName + " - Description: " + description + " - Keywords: " + keywords);
            });

            String relativePath = "/diabeticselfed/contents/HealthyEating";
            try (Stream<Path> paths = Files.walk(Paths.get(relativePath))) {
                paths.filter(Files::isRegularFile).forEach(path -> {
                    String moduleName = path.getFileName().toString().replace(".pdf", "");
                    if (moduleDetails.containsKey(moduleName)) {
                        String[] details = moduleDetails.get(moduleName);
                        Module module = new Module();
                        module.setName(moduleName);
                        module.setFilePath(relativePath + "/" + path.getFileName().toString());
                        module.setDescription(details[0]); // Set description
                        module.setContentArea(healthyEating);
                        module.setKeywords(details[1]); // Set keywords
                        moduleRepository.save(module);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    private void readModuleKeywords(String filePath, HashMap<String, String[]> moduleDetails) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String currentModuleName = "";
            String currentDescription = "";
            StringJoiner keywordJoiner = new StringJoiner(", ");

            while ((line = reader.readLine()) != null) {
                if (line.matches("^\\d+\\.\\s.*")) {
                    if (!currentModuleName.isEmpty()) {
                        moduleDetails.put(currentModuleName, new String[]{currentDescription, keywordJoiner.toString()});
                        keywordJoiner = new StringJoiner(", ");
                    }

                    String[] parts = line.split(":");
                    currentModuleName = parts[0].trim().split("\\.")[1].trim();

                    // Check if line contains both description and keywords
                    if (parts.length > 1 && parts[1].contains("•")) {
                        String[] descriptionAndKeywords = parts[1].split("•", 2);
                        currentDescription = descriptionAndKeywords[0].trim();
                        keywordJoiner.add(descriptionAndKeywords[1].trim());
                    } else {
                        currentDescription = parts.length > 1 ? parts[1].trim() : "";
                    }
                } else if (!line.trim().isEmpty() && line.startsWith("•")) {
                    String keyword = line.trim().substring(1).trim(); // Remove bullet point
                    keywordJoiner.add(keyword);
                }
            }
            if (!currentModuleName.isEmpty()) {
                moduleDetails.put(currentModuleName, new String[]{currentDescription, keywordJoiner.toString()});
            }
        }
    }


    private ContentArea createContentArea(String name) {
        ContentArea contentArea = new ContentArea();
        contentArea.setName(name);
        return contentAreaRepository.save(contentArea);
    }

    @Bean
    public CommandLineRunner addQuestions(QuestionRepository questionRepository) {
        return args -> {


            // Create sample questions
            List<Question> questions = new ArrayList<>();
            questions.add(new Question(1, "Do you feel like you have been adhering to a healthy diet the past few days?", null));
            questions.add(new Question(2, "What is your weight today? Please enter your weight in pounds.", null));
            questions.add(new Question(3, "At the beginning of each new week: Did you meet your action plan goal from last week? (Yes/No)", null));

            // Save questions to the database
            questionRepository.saveAll(questions);
        };
    }
}