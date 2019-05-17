package com.sb.solutions.web.user.mapper;

import com.sb.solutions.api.rolePermissionRight.entity.RoleHierarchy;
import com.sb.solutions.core.dto.BaseMapper;
import com.sb.solutions.web.user.dto.RoleHierarchyDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author Rujan Maharjan on 5/13/2019
 */

@Component
@Mapper(componentModel = BaseMapper.SPRING_MODEL)
public abstract class RoleHierarchyMapper extends BaseMapper<RoleHierarchy, RoleHierarchyDto> {

}
