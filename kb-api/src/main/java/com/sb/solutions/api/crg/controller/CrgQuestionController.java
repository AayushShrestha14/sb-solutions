package com.sb.solutions.api.crg.controller;

import java.util.List;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.solutions.api.crg.entity.CrgQuestion;
import com.sb.solutions.api.crg.service.CrgQuestionService;
import com.sb.solutions.api.customer.enums.CustomerType;
import com.sb.solutions.core.dto.RestResponseDto;

/**
 * @author Sunil Babu Shrestha on 9/10/2020
 */
@RestController
@RequestMapping("/v1/{customerType}/crg-questions")
@RequiredArgsConstructor
public class CrgQuestionController {

    private final CrgQuestionService questionService;


    @PostMapping
    public final ResponseEntity<?> addQuestions(
        @Valid @RequestBody List<CrgQuestion> questions) {

        final List<CrgQuestion> savedQuestions = questionService.save(questions);

        if (savedQuestions.size() == 0) {
            return new RestResponseDto().failureModel("Oops! Something went wrong.");
        }

        return new RestResponseDto().successModel(savedQuestions);
    }

    @GetMapping
    public final ResponseEntity<?> getQuestionsOfCustomerType(
        @PathVariable CustomerType customerType) {
        final List<CrgQuestion> questions = questionService.findByCustomerType(customerType);
        return new RestResponseDto().successModel(questions);
    }

    @PutMapping("/{id}")
    public final ResponseEntity<?> updateQuestions(
        @Valid @RequestBody CrgQuestion question) {

        final CrgQuestion updatedQuestion = questionService.update(question);
        return new RestResponseDto().successModel(updatedQuestion);
    }

    @DeleteMapping("/{id}")
    public final ResponseEntity<?> deleteQuestion(
        @PathVariable long id) {
        questionService.delete(id);
        return new RestResponseDto().successModel("Successfully deleted.");
    }
}
