package com.baylor.diabeticselfed.repository;


import com.baylor.diabeticselfed.entities.Clinician;
import com.baylor.diabeticselfed.entities.User;
import com.baylor.diabeticselfed.model.ViewPatientSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ClinicianRepository extends JpaRepository<Clinician, Long> {

    Optional<Clinician> findByEmail(String email);
    Optional<Clinician> findByClinicianUser(User user);
    Optional<Clinician> findById(Long id);
//    @Query("SELECT v FROM ViewPatientSummary v")
//    List<ViewPatientSummary> findAllPatientSummary();


}
//@Repository
//public class ClinicianRepository {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public ClinicianRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<ViewPatientSummary> findAll() {
//        String sql = "SELECT * FROM view_patient_summary";
//        return jdbcTemplate.query(sql, new ViewPatientSummaryRowMapper());
//    }
//
//    private static class ViewPatientSummaryRowMapper implements RowMapper<ViewPatientSummary> {
//        @Override
//        public ViewPatientSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
//            ViewPatientSummary viewPatientSummary = new ViewPatientSummary();
//            viewPatientSummary.setPatientId(rs.getInt("patient_id"));
//            viewPatientSummary.setModuleId(rs.getInt("module_id"));
//            viewPatientSummary.setCompletedPercentage(rs.getInt("completed_percentage"));
//            viewPatientSummary.setStartT(rs.getObject("startt", LocalDateTime.class));
//            viewPatientSummary.setEndT(rs.getObject("endt", LocalDateTime.class));
//            viewPatientSummary.setHeight(rs.getInt("height"));
//            viewPatientSummary.setWeight(rs.getInt("weight"));
//            viewPatientSummary.setBmi(rs.getDouble("bmi"));
//            viewPatientSummary.setSurveyDate(rs.getObject("survey_date", LocalDateTime.class));
//            viewPatientSummary.setSurveyAnswer(rs.getBoolean("survey_answer"));
//            viewPatientSummary.setQuestionDescription(rs.getString("question_description"));
//            return viewPatientSummary;
//        }
//    }
//}
