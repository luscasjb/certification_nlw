package com.ljb.certification_nlw.model.students.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ljb.certification_nlw.model.questions.entity.QuestionEntity;
import com.ljb.certification_nlw.model.questions.repository.QuestionRepository;
import com.ljb.certification_nlw.model.students.dto.HasCertificationDTO;
import com.ljb.certification_nlw.model.students.dto.StudentCertificationAnswerDTO;
import com.ljb.certification_nlw.model.students.entity.AnswersCertificationsEntity;
import com.ljb.certification_nlw.model.students.entity.CertificationStudentEntity;
import com.ljb.certification_nlw.model.students.entity.StudentEntity;
import com.ljb.certification_nlw.model.students.repository.CertificationStudentRepository;
import com.ljb.certification_nlw.model.students.repository.StudentRepository;

@Service
public class StudentCertificationAnswersService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CertificationStudentRepository certificationStudentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CertificationService certificationService;

    public CertificationStudentEntity execute(StudentCertificationAnswerDTO dto) throws Exception {

        var hasCertification = this.certificationService
            .execute(new HasCertificationDTO(dto.getEmail(), dto.getTechnology()));

        if(hasCertification) {
            throw new Exception("Você já possui essa certificação!");
        }

        List<QuestionEntity> questionEntity = questionRepository.findByTechnology(dto.getTechnology());
        List<AnswersCertificationsEntity> answersCertifications = new ArrayList<>();

        AtomicInteger correctAnswers = new AtomicInteger(0);

        dto.getQuestionsAnswers().stream().forEach(questionAnswer -> {
            var questionFilter = questionEntity.stream()
                .filter(question -> question.getId().equals(questionAnswer.getQuestionId()))
                .findFirst().get();

            var findCorrectAlternative = questionFilter.getAlternatives().stream()
                .filter(alternative -> alternative.isCorrect())
                .findFirst().get();

            if(findCorrectAlternative.getId().equals(questionAnswer.getAlternativeId())){
                questionAnswer.setCorrect(true);
                correctAnswers.incrementAndGet();
            } else {
                questionAnswer.setCorrect(false);
            }

            var answersCertificationsEntity = AnswersCertificationsEntity.builder()
                .answerId(questionAnswer.getAlternativeId())
                .questionId(questionAnswer.getQuestionId())
                .isCorrect(questionAnswer.isCorrect())
                .build();

            answersCertifications.add(answersCertificationsEntity);
        });

        var student = studentRepository.findByEmail(dto.getEmail());
        UUID studentId;
        if(student.isEmpty()) {
            var studentCreated = StudentEntity.builder().email(dto.getEmail()).build();
            studentCreated = studentRepository.save(studentCreated);
            studentId = studentCreated.getId();
        } else {
            studentId = student.get().getId();
        }

        CertificationStudentEntity certificationStudentEntity = CertificationStudentEntity.builder()
            .technology(dto.getTechnology())
            .studentId(studentId)
            .grade(correctAnswers.get())
            .build();

        var certificationStudentCreated = certificationStudentRepository.save(certificationStudentEntity);

        answersCertifications.stream().forEach(answerCertification -> {
            answerCertification.setCertificationId(certificationStudentEntity.getId());
            answerCertification.setCertificationStudentEntity(certificationStudentEntity);
        });

        certificationStudentEntity.setAnswersCertifications(answersCertifications);
        certificationStudentRepository.save(certificationStudentEntity);

        return certificationStudentCreated;
    }
}
