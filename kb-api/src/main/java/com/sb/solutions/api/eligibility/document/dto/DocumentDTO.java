package com.sb.solutions.api.eligibility.document.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDTO {

    @NotNull(message = "Document Type is required.")
    private String type;

    @NotNull(message = "Image name is required.")
    private String name;

    @NotNull(message = "Image is required.")
    private String image;

}
