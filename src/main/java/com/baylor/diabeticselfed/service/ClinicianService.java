package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.model.ViewPatientSummary;
import com.baylor.diabeticselfed.repository.ClinicianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.swing.text.View;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicianService {

    private final ClinicianRepository clinicianRepository;
    private final JdbcTemplate jdbcTemplate;

//    public List<ViewPatientSummary> getViewPatientSummary() {
//        refreshMaterializedView();
//        return clinicianRepository.findAllPatientSummary();
//    }

    public List<ViewPatientSummary> getAllPatientSummary() {
        return clinicianRepository.findAll();
    }
    private void refreshMaterializedView() {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM pg_matviews WHERE matviewname = 'view_patient_summary'",
                Integer.class);
        if (count != null && count > 0) {
            jdbcTemplate.execute("REFRESH MATERIALIZED VIEW view_patient_summary");
        }
    }
}
