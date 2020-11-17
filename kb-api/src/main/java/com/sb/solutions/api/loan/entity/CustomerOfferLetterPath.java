package com.sb.solutions.api.loan.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.sb.solutions.api.offerLetter.entity.OfferLetter;
import com.sb.solutions.core.enitity.BaseEntity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerOfferLetterPath extends BaseEntity<Long> {

    private String path;

    private String initialInformation;

    private String supportedInformation;

    @OneToOne
    private OfferLetter offerLetter;

    private String pathSigned;

}
