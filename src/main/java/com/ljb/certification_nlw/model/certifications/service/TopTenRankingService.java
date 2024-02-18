package com.ljb.certification_nlw.model.certifications.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ljb.certification_nlw.model.students.entity.CertificationStudentEntity;
import com.ljb.certification_nlw.model.students.repository.CertificationStudentRepository;

@Service
public class TopTenRankingService {
    
    @Autowired
    private CertificationStudentRepository repository;

    public List<CertificationStudentEntity> execute() {
        var result = this.repository.findTopTenByOrderByGradeDesc();
        return result;
    }
}
