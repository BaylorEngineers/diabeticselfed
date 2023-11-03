package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.entities.ContentArea;
import com.baylor.diabeticselfed.entities.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {

    List<Module> findByContentArea(ContentArea contentArea);

    Optional<Module> findByName(String name);

}
