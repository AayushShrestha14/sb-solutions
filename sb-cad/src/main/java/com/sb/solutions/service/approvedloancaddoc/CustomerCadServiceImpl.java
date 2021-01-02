package com.sb.solutions.service.approvedloancaddoc;

import com.sb.solutions.entity.CustomerApprovedLoanCadDocumentation;
import com.sb.solutions.repository.CustomerCadRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("customerCadService")
@Transactional
public class CustomerCadServiceImpl implements CustomerCadService {
    private final CustomerCadRepository customerCadRepository;

    public CustomerCadServiceImpl(CustomerCadRepository customerCadRepository) {
        this.customerCadRepository = customerCadRepository;
    }

    @Override
    public List<CustomerApprovedLoanCadDocumentation> findAll() {
        return customerCadRepository.findAll();
    }

    @Override
    public CustomerApprovedLoanCadDocumentation findOne(Long id) {
        return customerCadRepository.getOne(id);
    }

    @Override
    public CustomerApprovedLoanCadDocumentation save(CustomerApprovedLoanCadDocumentation c) {
        return customerCadRepository.save(c);
    }

    @Override
    public Page<CustomerApprovedLoanCadDocumentation> findAllPageable(Object t, Pageable pageable) {
        return customerCadRepository.findAll(pageable);
    }

    @Override
    public List<CustomerApprovedLoanCadDocumentation> saveAll(List<CustomerApprovedLoanCadDocumentation> list) {
        return customerCadRepository.saveAll(list);
    }
}
