package com.ljb.certification_nlw.model.students.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ljb.certification_nlw.model.students.dto.HasCertificationDTO;
import com.ljb.certification_nlw.model.students.repository.CertificationStudentRepository;

@Service
public class CertificationService {
    
    @Autowired
    private CertificationStudentRepository repository;

    public boolean execute(HasCertificationDTO dto) {
        var result = this.repository.findByStudentEmailAndTechnology(dto.getEmail(), dto.getTechnology());
        if(!result.isEmpty()){
            return true;
        }
        return false;
    }
}
