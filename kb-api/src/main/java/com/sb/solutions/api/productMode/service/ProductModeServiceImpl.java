package com.sb.solutions.api.productMode.service;

import com.sb.solutions.api.productMode.entity.ProductMode;
import com.sb.solutions.api.productMode.repository.ProductModeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Rujan Maharjan on 6/24/2019
 */
@Service
public class ProductModeServiceImpl implements ProductModeService {

    private final ProductModeRepository productModeRepository;

    public ProductModeServiceImpl(@Autowired ProductModeRepository productModeRepository) {
        this.productModeRepository = productModeRepository;
    }

    @Override
    public List<ProductMode> findAll() {
        return productModeRepository.findAll();
    }

    @Override
    public ProductMode findOne(Long id) {
        return productModeRepository.getOne(id);
    }

    @Override
    public ProductMode save(ProductMode productMode) {
        return productModeRepository.save(productMode);
    }

    @Override
    public Page<ProductMode> findAllPageable(Object t, Pageable pageable) {
        return null;
    }
}
