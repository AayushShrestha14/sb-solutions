package com.sb.solutions.core.dto;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sb.solutions.core.exception.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Rujan Maharjan on 12/31/2018
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponseDto {

    private String message;
    private Object detail;
    private ResponseStatus responseStatus;

    @JsonIgnore
    private int code;

    public ResponseEntity successModel(Object o) {
        RestResponseDto r = new RestResponseDto();
        r.setDetail(o);
        r.setMessage("SUCCESS");
        r.setResponseStatus(ResponseStatus.SUCCESS);
        return new ResponseEntity(r, HttpStatus.OK);
    }

    public ResponseEntity validationFailed(List<ObjectError> errors) {
        RestResponseDto r = new RestResponseDto();
        r.setResponseStatus(ResponseStatus.BAD_REQUEST);
        r.setDetail(errors);

        return new ResponseEntity(r, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity failureModel(String message) {
        RestResponseDto r = new RestResponseDto();
        r.setMessage(message);
        r.setResponseStatus(ResponseStatus.BAD_REQUEST);
        return new ResponseEntity(r, HttpStatus.BAD_REQUEST);
    }
}
