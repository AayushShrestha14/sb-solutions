package com.sb.solutions.web.eligibility.question;

import com.sb.solutions.api.eligibility.question.entity.Question;
import com.sb.solutions.api.eligibility.question.service.QuestionService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.exception.GlobalExceptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    private final GlobalExceptionHandler globalExceptionHandler;

    @PostMapping(path = "/v1/admin/questions")
    final public ResponseEntity<?> addQuestions(@Valid @RequestBody List<Question> questions, BindingResult bindingResult) {
        globalExceptionHandler.constraintValidation(bindingResult);
        final List<Question> savedQuestions = questionService.save(questions);
        if (savedQuestions.size() == 0) return new RestResponseDto().failureModel("Oops! Something went wrong.");
        return new RestResponseDto().successModel(savedQuestions);
    }

    @GetMapping(path = "/v1/questions")
    final public ResponseEntity<?> getQuestionsOfScheme(@RequestParam("scheme_id") long schemeId) {
        final List<Question> questions = questionService.findBySchemeId(schemeId);
        return new RestResponseDto().successModel(questions);
    }
}
