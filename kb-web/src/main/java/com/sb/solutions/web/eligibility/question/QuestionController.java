package com.sb.solutions.web.eligibility.question;

import com.sb.solutions.api.eligibility.question.entity.Question;
import com.sb.solutions.api.eligibility.question.service.QuestionService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/companies/{companyId}/schemes/{schemeId}/questions")
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    private final GlobalExceptionHandler globalExceptionHandler;

    @PostMapping
    final public ResponseEntity<?> addQuestions(@PathVariable long companyId, @PathVariable long schemeId
            , @Valid @RequestBody List<Question> questions, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        final List<Question> savedQuestions = questionService.save(questions);
        if (savedQuestions.size() == 0) return new RestResponseDto().failureModel("Oops! Something went wrong.");
        return new RestResponseDto().successModel(savedQuestions);
    }

    @GetMapping
    final public ResponseEntity<?> getQuestionsOfScheme(@PathVariable long companyId, @PathVariable long schemeId) {
        final List<Question> questions = questionService.findBySchemeId(schemeId);
        return new RestResponseDto().successModel(questions);
    }

    @PutMapping("/{id}")
    final public ResponseEntity<?> updateQuestions(@PathVariable long companyId, @PathVariable long schemeId
            , @PathVariable long id, @Valid @RequestBody Question question, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        final Question updatedQuestion = questionService.update(question);
        return new RestResponseDto().successModel(updatedQuestion);
    }
}
