package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.dto.ContentAreaDTO;
import com.baylor.diabeticselfed.entities.ContentArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContentAreaRepository extends JpaRepository<ContentArea, Integer> {
    Optional<ContentArea> findByName(String name);
    Optional<ContentArea> findById(Long id);


    @Query("select new com.baylor.diabeticselfed.dto.ContentAreaDTO(c.id, c.name) from ContentArea c")
    List<ContentAreaDTO> findAllProjectedBy();
}
