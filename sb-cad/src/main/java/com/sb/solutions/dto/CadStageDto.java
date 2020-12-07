package com.sb.solutions.dto;

import com.sb.solutions.api.authorization.dto.RoleDto;
import com.sb.solutions.api.user.dto.UserDto;
import com.sb.solutions.core.enums.DocAction;
import com.sb.solutions.enums.CADDocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Rujan Maharjan on  12/4/2020
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CadStageDto {

    private Long cadId;

    private List<CustomerLoanDto> customerLoanDtoList = new ArrayList<>();

    private UserDto toUser;

    private RoleDto toRole;

    private String comment;

    private DocAction docAction;

    private CADDocumentType documentType;
}
