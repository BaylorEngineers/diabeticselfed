package com.baylor.diabeticselfed;

import com.baylor.diabeticselfed.auth.AuthenticationService;
import com.baylor.diabeticselfed.auth.RegisterRequest;
import com.baylor.diabeticselfed.entities.ContentArea;
import com.baylor.diabeticselfed.entities.Module;
import com.baylor.diabeticselfed.entities.Question;
import com.baylor.diabeticselfed.repository.ContentAreaRepository;
import com.baylor.diabeticselfed.repository.ModuleRepository;
import com.baylor.diabeticselfed.repository.QuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.baylor.diabeticselfed.entities.Role.ADMIN;
import static com.baylor.diabeticselfed.entities.Role.CLINICIAN;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class DiabeticSelfEdApplication {

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
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("clinician@mail.com")
                    .password("password")
                    .role(CLINICIAN)
                    .build();
            System.out.println("Clinician token: " + service.register(clinician).getAccessToken());
        };
    }

    @Bean
    public CommandLineRunner contentAreaSetup(ContentAreaRepository contentAreaRepository, ModuleRepository moduleRepository) {
        return args -> {
            ContentArea healthyEating = createContentArea(contentAreaRepository, "Healthy Eating");
            createContentArea(contentAreaRepository, "Physical Activity");
            createContentArea(contentAreaRepository, "Stress Reduction");

            String relativePath = "contents/HealthyEating";
            try (Stream<Path> paths = Files.walk(Paths.get(relativePath))) {
                paths.filter(Files::isRegularFile).forEach(path -> {
                    Module module = new Module();
                    module.setName(path.getFileName().toString());
                    module.setFilePath(relativePath + "/" + path.getFileName().toString()); // Set the file path
                    module.setDescription("Description for " + path.getFileName());
                    module.setContentArea(healthyEating);
                    moduleRepository.save(module);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    private ContentArea createContentArea(ContentAreaRepository repository, String name) {
        return repository.findByName(name).orElseGet(() -> {
            ContentArea newContentArea = new ContentArea();
            newContentArea.setName(name);
            return repository.save(newContentArea);
        });
    }

    @Bean
    public CommandLineRunner addQuestions(QuestionRepository questionRepository) {
        return args -> {


            // Create sample questions
            List<Question> questions = new ArrayList<>();
            questions.add(new Question(1, "Do you feel like you have been adhering to a healthy diet the past few days", null));
            questions.add(new Question(2, "What is your weight today? Please enter your weight in pounds.", null));
            questions.add(new Question(3, "At the beginning of each new week: Did you meet your action plan goal from last week? (Yes/No)", null));

            // Save questions to the database
            questionRepository.saveAll(questions);
        };
    }
}