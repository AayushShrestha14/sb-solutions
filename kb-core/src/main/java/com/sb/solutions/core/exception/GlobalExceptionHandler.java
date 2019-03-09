package com.sb.solutions.core.exception;

import com.sb.solutions.core.dto.RestResponseDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author Rujan Maharjan on 1/21/2019
 */

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

/*

    @ExceptionHandler(value = {ApiException.class, NullPointerException.class, ConstraintViolationException.class, SQLException.class,
            ResponseStatusException.class, EntityNotFoundException.class, IllegalArgumentException.class, SQLIntegrityConstraintViolationException.class, DataIntegrityViolationException.class, Exception.class})
    protected ResponseEntity<Object> globalErrorHandle(
            RuntimeException ex) {
        RestResponseDto restResponseDto = new RestResponseDto();

        if (ex instanceof DataIntegrityViolationException) {
            restResponseDto = new RestResponseDto(((DataIntegrityViolationException) ex).getMostSpecificCause().getMessage().toString().substring(0, 16), null, ResponseStatus.VALIDATION_FAILED, 400);

        } else if (ex instanceof ApiException) {
            restResponseDto = new RestResponseDto(ex.getMessage(), null, ResponseStatus.VALIDATION_FAILED, 400);

        } else if (ex instanceof ResponseStatusException) {
            restResponseDto = new RestResponseDto(((ResponseStatusException) ex).getReason(), null, ResponseStatus.VALIDATION_FAILED, 400);

        } else if (ex instanceof EntityNotFoundException) {
            restResponseDto = new RestResponseDto(((EntityNotFoundException) ex).getMessage(), null, ResponseStatus.VALIDATION_FAILED, 400);

        } else {
            restResponseDto = new RestResponseDto(ex.getMessage(), null, ResponseStatus.BAD_REQUEST, 400);
        }

        return new ResponseEntity<>(restResponseDto, HttpStatus.valueOf(restResponseDto.getCode()));
    }
*/

    public void constraintValidation(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ApiException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
    }

}
