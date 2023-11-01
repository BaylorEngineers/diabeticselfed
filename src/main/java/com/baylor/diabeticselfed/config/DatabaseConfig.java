package com.baylor.diabeticselfed.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseConfig {
    @Bean
    public CommandLineRunner createMaterializedView(JdbcTemplate jdbcTemplate) {
        return args -> {
            jdbcTemplate.execute("CREATE MATERIALIZED VIEW IF NOT EXISTS view_patient_summary AS " +
                    "SELECT " +
                    "  p.id AS patient_id, " +
                    "  ml.module_id, " +
                    "  mp.completed_percentage, " +
                    "  ml.startt, " +
                    "  ml.endt, " +
                    "  wt.height, " +
                    "  wt.weight, " +
                    "  (wt.weight / (wt.height / 100.0)^2 * 703) AS bmi, " +
                    "  s.date AS survey_date, " +
                    "  s.answer AS survey_answer, " +
                    "  q.description AS question_description " +
                    "FROM " +
                    "  patient p " +
                    "  LEFT JOIN module_log ml ON p.id = ml.patient_id " +
                    "  LEFT JOIN module_progress mp ON p.id = mp.patient_id AND ml.module_id = mp.module_id " +
                    "  LEFT JOIN weight_tracker wt ON p.id = wt.patient_id " +
                    "  LEFT JOIN survey s ON p.id = s.patient_id " +
                    "  LEFT JOIN question q ON s.question_id = q.id");
        };
    }

//    @Bean
//    public CommandLineRunner refreshMaterializedView(JdbcTemplate jdbcTemplate) {
//        return args -> {
//            while (true) {
//                jdbcTemplate.execute("REFRESH MATERIALIZED VIEW view_patient_summary");
//                Thread.sleep(60000);  // sleep for 1 minute
//            }
//        };
//    }

//    @Bean
//    public CommandLineRunner refreshMaterializedView(JdbcTemplate jdbcTemplate) {
//        return args -> {
//            while (true) {
//                Integer count = jdbcTemplate.queryForObject(
//                        "SELECT count(*) FROM pg_matviews WHERE matviewname = 'view_patient_summary'",
//                        Integer.class);
//                if (count != null && count > 0) {
//                    jdbcTemplate.execute("REFRESH MATERIALIZED VIEW view_patient_summary");
//                }
//                Thread.sleep(60);  // sleep for 1 minute
//            }
//        };
//    }
}
