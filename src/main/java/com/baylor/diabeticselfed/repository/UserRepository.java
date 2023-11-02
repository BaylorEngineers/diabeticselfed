package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.entities.Role;
import com.baylor.diabeticselfed.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);
  List<User> findByRole(String role);

  Optional<User> findById(Long id);

}
