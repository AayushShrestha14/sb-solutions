package com.sb.solutions.api.offerLetter.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferLetter {

    @Id
    @GeneratedValue
    private Long id;
    private @NotNull String name;
    private @NotNull String templateUrl;
}
