package com.sb.solutions.core.config.security;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
@Getter
@Setter
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {

    private String customMessage;

    public CustomOauthException(String msg) {
        super(msg);
    }
}
