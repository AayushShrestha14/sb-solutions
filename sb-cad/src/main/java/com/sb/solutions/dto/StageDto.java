package com.sb.solutions.dto;

import com.sb.solutions.entity.CadStage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : Rujan Maharjan on  12/7/2020
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StageDto {

    private CadStage cadStage;

    private String previousList;

}
