package com.ljb.certification_nlw.model.questions.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ljb.certification_nlw.model.questions.entity.QuestionEntity;

public interface QuestionRepository extends JpaRepository<QuestionEntity, UUID> {
    
    List<QuestionEntity> findByTechnology(String technology);
}
