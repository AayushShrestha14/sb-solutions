package com.sb.solutions.core.utils.email;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
