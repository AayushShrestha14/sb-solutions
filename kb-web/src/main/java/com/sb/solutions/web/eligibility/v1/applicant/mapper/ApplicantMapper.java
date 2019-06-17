package com.sb.solutions.web.eligibility.v1.applicant.mapper;

import com.sb.solutions.api.eligibility.applicant.entity.Applicant;
import com.sb.solutions.core.dto.BaseMapper;
import com.sb.solutions.web.eligibility.v1.applicant.dto.ApplicantDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = BaseMapper.SPRING_MODEL)
public abstract class ApplicantMapper extends BaseMapper<Applicant, ApplicantDto> {

}
