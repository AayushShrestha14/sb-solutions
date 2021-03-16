package com.sb.solutions.api.mGroupInfo.entity;

import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.utils.FieldErrorConstant;
import com.sb.solutions.core.utils.string.StringConstants;
import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MGroupInfo extends BaseEntity<Long> {

    @NotNull(message = FieldErrorConstant.NOT_NULL)
    @NotBlank(message = FieldErrorConstant.NOT_BLANK)
    @Size(min = StringConstants.DEFAULT_MIN_SIZE_4)
    private String groupCode;

    private String detailInformation;
}
