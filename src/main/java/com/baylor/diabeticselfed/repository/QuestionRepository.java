package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.entities.Goal;
import com.baylor.diabeticselfed.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findById(Integer Id);

}
