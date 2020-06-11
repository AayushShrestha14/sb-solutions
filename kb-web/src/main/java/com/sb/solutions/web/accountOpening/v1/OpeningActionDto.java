package com.sb.solutions.web.accountOpening.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.sb.solutions.core.enums.AccountStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpeningActionDto {

    private Long id;

    private AccountStatus actionStatus;

    private String email;

}
