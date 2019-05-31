package com.sb.solutions.web.eligibility.v1.loanconfig.mapper;

import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.core.dto.BaseMapper;
import com.sb.solutions.web.eligibility.v1.loanconfig.dto.LoanConfigDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = BaseMapper.SPRING_MODEL)
public abstract class EligibilityLoanConfigMapper extends BaseMapper<LoanConfig, LoanConfigDto> {

}
