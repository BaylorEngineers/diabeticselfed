package com.baylor.diabeticselfed;

import com.baylor.diabeticselfed.auth.AuthenticationService;
import com.baylor.diabeticselfed.auth.RegisterRequest;
import com.baylor.diabeticselfed.entities.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

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

//            // Create three patients
//            var patient1 = RegisterRequest.builder()
//                    .firstname("Patient1")
//                    .lastname("Patient1")
//                    .email("patient1@mail.com")
//                    .password("password")
//                    .role(Role.PATIENT)
//                    .build();
//            System.out.println("Patient1 built ");
//
//            var patient2 = RegisterRequest.builder()
//                    .firstname("Patient2")
//                    .lastname("Patient2")
//                    .email("patient2@mail.com")
//                    .password("password")
//                    .role(Role.PATIENT)
//                    .build();
//            System.out.println("Patient2 built ");
//
//            var patient3 = RegisterRequest.builder()
//                    .firstname("Patient3")
//                    .lastname("Patient3")
//                    .email("patient3@mail.com")
//                    .password("password")
//                    .role(Role.PATIENT)
//                    .build();
//            System.out.println("Patient3 built ");
        };
    }
}