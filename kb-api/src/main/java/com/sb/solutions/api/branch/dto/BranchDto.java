package com.sb.solutions.api.branch.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.sb.solutions.api.user.dto.UserDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchDto {
    private Long id;
    private String name;


    public BranchDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
