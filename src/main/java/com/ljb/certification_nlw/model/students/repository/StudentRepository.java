package com.ljb.certification_nlw.model.students.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ljb.certification_nlw.model.students.entity.StudentEntity;

public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {
    
    Optional<StudentEntity> findByEmail(String email);
}
