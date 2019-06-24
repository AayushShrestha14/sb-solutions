package com.sb.solutions.api.productMode.entity;

import com.sb.solutions.core.enums.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Rujan Maharjan on 6/24/2019
 */
@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductMode {

    @Id
    private Long id;

    private Product product;
}
