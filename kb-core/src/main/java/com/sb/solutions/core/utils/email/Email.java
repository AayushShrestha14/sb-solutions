package com.sb.solutions.core.utils.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    private List<String> bcc;
    private String body;
    private List<String> attachment;
    private String subject;
    private String to;

}
