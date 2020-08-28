package com.sb.solutions.api.customer.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.customer.entity.CustomerGeneralDocument;
import com.sb.solutions.api.customer.repository.CustomerGeneralDocumentRepository;

/**
 * @author : Rujan Maharjan on  8/25/2020
 **/
@Service
public class CustomerGeneralDocumentServiceImpl implements CustomerGeneralDocumentService {

    private final CustomerGeneralDocumentRepository customerGeneralDocumentRepository;

    public CustomerGeneralDocumentServiceImpl(
        @Autowired CustomerGeneralDocumentRepository customerGeneralDocumentRepository) {
        this.customerGeneralDocumentRepository = customerGeneralDocumentRepository;
    }

    @Override
    public CustomerGeneralDocument save(CustomerGeneralDocument customerGeneralDocument) {
        return this.customerGeneralDocumentRepository.save(customerGeneralDocument);
    }

    @Override
    public List<CustomerGeneralDocument> saveAll(List<CustomerGeneralDocument> entities) {
        return this.customerGeneralDocumentRepository.saveAll(entities);
    }

    @Override
    public Optional<CustomerGeneralDocument> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<CustomerGeneralDocument> findAll() {
        return null;
    }

    @Override
    public void delete(CustomerGeneralDocument entity) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public Page<CustomerGeneralDocument> findPageableBySpec(Map<String, String> filterParams,
        Pageable pageable) {
        return null;
    }

    @Override
    public List<CustomerGeneralDocument> findAllBySpec(Map<String, String> filterParams) {
        return null;
    }

    @Override
    public Optional<CustomerGeneralDocument> findOneBySpec(Map<String, String> filterParams) {
        return Optional.empty();
    }

    @Override
    public List<CustomerGeneralDocument> findByCustomerInfoId(Long id) {
        return customerGeneralDocumentRepository.findByCustomerInfoId(id);
    }
}
