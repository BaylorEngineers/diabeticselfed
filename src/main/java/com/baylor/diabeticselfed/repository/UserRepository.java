package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.entities.Role;
import com.baylor.diabeticselfed.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);
  List<User> findByRole(String role);

  Optional<User> findById(Long id);

  @Query("SELECT u.lastLoginDate FROM User u WHERE u.id = :userId")
  Date getLastLoginDate(@Param("userId") Integer id);

}
