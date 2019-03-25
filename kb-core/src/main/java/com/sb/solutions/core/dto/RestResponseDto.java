package com.sb.solutions.core.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    public ResponseEntity failureModel(String message) {
        RestResponseDto r = new RestResponseDto();
        r.setMessage(message);
        r.setResponseStatus(ResponseStatus.BAD_REQUEST);
        return new ResponseEntity(r, HttpStatus.BAD_REQUEST);
    }
}
