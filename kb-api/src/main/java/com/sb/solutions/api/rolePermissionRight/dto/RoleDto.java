package com.sb.solutions.api.rolePermissionRight.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.sb.solutions.api.user.dto.UserDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private Long id;
    private String name;
    private List<UserDto> userDtoList;

    public RoleDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
