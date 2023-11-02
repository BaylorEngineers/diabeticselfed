package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.entities.Question;
import com.baylor.diabeticselfed.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Question uploadQuestion(String description) {
        Question q = new Question();

        q.setDescription(description);

        return questionRepository.save(q);
    }
}
