package com.baylor.diabeticselfed.config;

import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Type;
import java.util.Date;

@Configuration
public class TestDataInitializer {
//    private final PatientRepository patientRepository;
    @Bean
    public CommandLineRunner dataInitForTestOnly(JdbcTemplate jdbcTemplate, PatientRepository patientRepository) {
        return args -> {
            insertPatientData(patientRepository);
            insertSampleData(jdbcTemplate);
        };
    }


    public void insertPatientData(PatientRepository patientRepository) {
        Patient patient1 = new Patient();
        patient1.setName("John Doe");
        patient1.setDOB(new Date());
        patient1.setEmail("john.doe@example.com");
        patient1.setLevelOfEd(Patient.EducationLevel.GRADUATE);
        patient1.setType(Patient.Type.TYPE1);
        patientRepository.save(patient1);

        Patient patient2 = new Patient();
        patient2.setName("Jane Doe");
        patient2.setDOB(new Date());
        patient2.setEmail("jane.doe@example.com");
        patient2.setLevelOfEd(Patient.EducationLevel.UNDERGRADUATE);
        patient2.setType(Patient.Type.TYPE2);
        patientRepository.save(patient2);

        Patient patient3 = new Patient();
        patient3.setName("Jim Doe");
        patient3.setDOB(new Date());
        patient3.setEmail("jim.doe@example.com");
        patient3.setLevelOfEd(Patient.EducationLevel.HIGHSCHOOL);
        patient2.setType(Patient.Type.TYPE2);
        patientRepository.save(patient3);
    }


    public void insertSampleData(JdbcTemplate jdbcTemplate) {

        // This weight tracker Insert sample data
        String sql1 = "INSERT INTO weight_tracker (id, patient_id, datet, height, weight) VALUES (1, 1, '2023-01-01', 180, 80)";
        jdbcTemplate.execute(sql1);

        String sql2 = "INSERT INTO weight_tracker (id, patient_id, datet, height, weight) VALUES (2, 2, '2023-02-01', 170, 70)";
        jdbcTemplate.execute(sql2);

        String sql3 = "INSERT INTO weight_tracker (id, patient_id, datet, height, weight) VALUES (3, 3, '2023-03-01', 160, 70)";
        jdbcTemplate.execute(sql3);


        // This module log Insert sample data
        String sql4 = "INSERT INTO module_log (id, patient_id, module_id, startt, endt) VALUES (1, 1, 1, '2023-01-01T00:00:00', '2023-01-10T00:00:00')";
        jdbcTemplate.execute(sql4);

        String sql5 = "INSERT INTO module_log (id, patient_id, module_id, startt, endt) VALUES (2, 2,  2, '2023-02-01T00:00:00', '2023-02-10T00:00:00')";
        jdbcTemplate.execute(sql5);

        String sql6 = "INSERT INTO module_log (id, patient_id, module_id, startt, endt) VALUES (3, 3,  3, '2023-03-01T00:00:00', '2023-03-10T00:00:00')";
        jdbcTemplate.execute(sql6);

        // This module progress Insert sample data
        String sql7 = "INSERT INTO module_progress (id, patient_id, module_id, completed_percentage) VALUES (1, 1, 1, 50)";
        jdbcTemplate.execute(sql7);

        String sql8 = "INSERT INTO module_progress (id, patient_id, module_id, completed_percentage) VALUES (2, 2, 2, 75)";
        jdbcTemplate.execute(sql8);

        String sql9 = "INSERT INTO module_progress (id, patient_id, module_id, completed_percentage) VALUES (3, 3, 3, 100)";
        jdbcTemplate.execute(sql9);

        // This question Insert sample data
        String sql10 = "INSERT INTO question (id, description) VALUES (1, 'Do you have a family history of diabetes?')";
        jdbcTemplate.execute(sql10);

        String sql11 = "INSERT INTO question (id, description) VALUES (2, 'Do you regularly monitor your blood sugar levels?\n')";
        jdbcTemplate.execute(sql11);

        String sql12 = "INSERT INTO question (id, description) VALUES (3, 'Do you follow a specific diet to help manage your diabetes?\n')";
        jdbcTemplate.execute(sql12);

        // This survey Insert sample data
        String sql13 = "INSERT INTO survey (id, patient_id, date, question_id, answer) VALUES (1,1, '2023-01-01T00:00:00', 1, true)";
        jdbcTemplate.execute(sql13);

        String sql14 = "INSERT INTO survey (id, patient_id, date, question_id, answer) VALUES (2,2, '2023-02-01T00:00:00', 2, false)";
        jdbcTemplate.execute(sql14);

        String sql15 = "INSERT INTO survey (id, patient_id, date, question_id, answer) VALUES (3,3, '2023-03-01T00:00:00', 3, true)";
        jdbcTemplate.execute(sql15);


    }
}
