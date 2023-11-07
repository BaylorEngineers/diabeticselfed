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

}
