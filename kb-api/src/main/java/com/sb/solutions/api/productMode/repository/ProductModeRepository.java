package com.sb.solutions.api.productMode.repository;

import com.sb.solutions.api.productMode.entity.ProductMode;
import com.sb.solutions.core.enums.Product;
import com.sb.solutions.core.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Rujan Maharjan on 6/24/2019
 */
public interface ProductModeRepository extends JpaRepository<ProductMode,Long> {

    ProductMode getByProductAndStatus(Product product,Status status);
}
