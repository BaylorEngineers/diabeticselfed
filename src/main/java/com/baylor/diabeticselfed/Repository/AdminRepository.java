package com.baylor.diabeticselfed.Repository;

import com.baylor.diabeticselfed.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}

