package com.ljb.certification_nlw.model.certifications.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ljb.certification_nlw.model.certifications.service.TopTenRankingService;
import com.ljb.certification_nlw.model.students.entity.CertificationStudentEntity;

@RestController
@RequestMapping("/ranking")
public class RankingController {
    
    @Autowired
    private TopTenRankingService service;

    @GetMapping("/topTen")
    public List<CertificationStudentEntity> topTen() {
        return this.service.execute();
    }
}
