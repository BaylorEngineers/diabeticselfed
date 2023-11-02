package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.entities.ContentArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentAreaRepository extends JpaRepository<ContentArea, Integer> {
}
