package com.baylor.diabeticselfed.Repository;

import com.baylor.diabeticselfed.Entity.Clinician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicianRepository extends JpaRepository<Clinician, Long> {
}

