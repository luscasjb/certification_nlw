package com.ljb.certification_nlw.model.questions.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ljb.certification_nlw.model.questions.dto.AlternativesResultDTO;
import com.ljb.certification_nlw.model.questions.dto.QuestionResultDTO;
import com.ljb.certification_nlw.model.questions.entity.AlternativesEntity;
import com.ljb.certification_nlw.model.questions.entity.QuestionEntity;
import com.ljb.certification_nlw.model.questions.repository.QuestionRepository;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    
    @Autowired
    private QuestionRepository repository;

    @GetMapping("/technology/{technology}")
    public List<QuestionResultDTO> getByTechnology(@PathVariable String technology) {
        var result = this.repository.findByTechnology(technology);

        var toMap = result.stream().map(question -> mapQuestionDTO(question)).collect(Collectors.toList());
        return toMap;
    }

    static QuestionResultDTO mapQuestionDTO(QuestionEntity question) {
        var questionResultDTO =  QuestionResultDTO.builder()
        .id(question.getId())
        .technology(question.getTechnology())
        .description(question.getDescription())
        .build();

        List<AlternativesResultDTO> alternativesResultDTO = question.getAlternatives().stream()
        .map(alternative -> mapAlternativeDTO(alternative)).collect(Collectors.toList());

        questionResultDTO.setAlternatives(alternativesResultDTO);
        return questionResultDTO;
    }

    static AlternativesResultDTO mapAlternativeDTO(AlternativesEntity alternatives) {
        return AlternativesResultDTO.builder()
            .id(alternatives.getId())    
            .description(alternatives.getDescription())
            .build();
    }
}
