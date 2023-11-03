package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.entities.Clinician;
import com.baylor.diabeticselfed.model.ViewPatientSummary;
import com.baylor.diabeticselfed.repository.ClinicianRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClinicianService {

    private final ClinicianRepository clinicianRepository;

//    @Autowired
    public ClinicianService(ClinicianRepository clinicianRepository) {
        this.clinicianRepository = clinicianRepository;
    }

//    public List<ViewPatientSummary> getViewPatientSummary() {
////        return clinicianRepository.findAll();
//    }

    public List<Clinician> getAllClinicians() {
        return clinicianRepository.findAll();
    }

}


//@Service
//@RequiredArgsConstructor
//public class ClinicianService {
//
//    private final ClinicianRepository clinicianRepository;
//    private final JdbcTemplate jdbcTemplate;
//
//    public List<ViewPatientSummary> getViewPatientSummary() {
//        System.out.println("to getViewPatientSummary");
//        refreshMaterializedView();
//        return clinicianRepository.findAll();
//    }
//
//    private void refreshMaterializedView() {
//        Integer count = jdbcTemplate.queryForObject(
//                "SELECT count(*) FROM pg_matviews WHERE matviewname = 'view_patient_summary'",
//                Integer.class);
//        if (count != null && count > 0) {
//            jdbcTemplate.execute("REFRESH MATERIALIZED VIEW view_patient_summary");
//        }
//    }
//}

