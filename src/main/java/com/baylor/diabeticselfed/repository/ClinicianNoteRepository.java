package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.entities.ClinicianNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClinicianNoteRepository extends JpaRepository<ClinicianNote, Long> {

    List<ClinicianNote> findByPatientId(Integer patientId);
}
