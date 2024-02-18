package com.ljb.certification_nlw.model.students.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ljb.certification_nlw.model.students.dto.HasCertificationDTO;
import com.ljb.certification_nlw.model.students.dto.StudentCertificationAnswerDTO;
import com.ljb.certification_nlw.model.students.service.CertificationService;
import com.ljb.certification_nlw.model.students.service.StudentCertificationAnswersService;

//rest controller = tudo o que retorna dentro é via json (API REST)
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private CertificationService certificationService;

    @Autowired
    private StudentCertificationAnswersService studentCertificationAnswersService;

    @PostMapping("/hasCertification")
    public String hasCertification(@RequestBody HasCertificationDTO hasCertificationDTO) {
        
        var result = this.certificationService.execute(hasCertificationDTO);
        if(result) {
            return "Usuário já realizou a prova!";
        }

        return "Usuário pode realizar a prova!"; 
    }

    @PostMapping("/certfication/answer")
    public ResponseEntity<Object> certificationAnswer(
        @RequestBody StudentCertificationAnswerDTO dto) {
        try {
            var result = studentCertificationAnswersService.execute(dto);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }
}
