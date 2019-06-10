package com.sb.solutions.core.exception.handler;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RequestExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> formErrorHandler(MethodArgumentNotValidException error) {

        logger.error("Validation Error", error);

        final List<Violation> violations = processError(error);

        final Map<String, Object> response = Maps.newHashMap();

        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());

        if (null != violations) {
            response.put("message",
                "Validation failed for " + error.getBindingResult().getObjectName());

            response.put("errorCount", error.getBindingResult().getErrorCount());
            response.put("errors", violations);
        } else {
            response.put("message", error.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private List<Violation> processError(MethodArgumentNotValidException ex) {
        final BindingResult rs = ex.getBindingResult();

        if (rs instanceof BeanPropertyBindingResult && rs.hasErrors()) {
            final BeanPropertyBindingResult propertyResult = (BeanPropertyBindingResult) rs;

            final List<Violation> violations = Lists
                .newArrayListWithCapacity(ex.getBindingResult().getErrorCount());

            for (FieldError error : propertyResult.getFieldErrors()) {
                final Violation violation = new Violation(error.getField(),
                    error.getDefaultMessage());

                violations.add(violation);
            }

            return violations;
        }

        return null;
    }
}
