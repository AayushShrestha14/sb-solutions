package com.sb.solutions.api.accountPurpose.service;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.accountPurpose.entity.AccountPurpose;
import com.sb.solutions.api.accountPurpose.repository.AccountPurposeRepository;
import com.sb.solutions.api.accountPurpose.repository.spec.AccountPurposeSpecBuilder;

@Service
public class AccountPurposeServiceImpl implements AccountPurposeService {

    private final AccountPurposeRepository accountPurposeRepository;

    public AccountPurposeServiceImpl(
        @Autowired AccountPurposeRepository accountPurposeRepository
    ) {
        this.accountPurposeRepository = accountPurposeRepository;
    }

    @Override
    public List<AccountPurpose> findAll() {
        return accountPurposeRepository.findAll();
    }

    @Override
    public AccountPurpose findOne(Long id) {
        return accountPurposeRepository.getOne(id);
    }

    @Override
    public AccountPurpose save(AccountPurpose accountPurpose) {
        return accountPurposeRepository.save(accountPurpose);
    }

    @Override
    public Page<AccountPurpose> findAllPageable(Object t, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = objectMapper.convertValue(t, Map.class);
        final AccountPurposeSpecBuilder builder = new AccountPurposeSpecBuilder(map);
        final Specification<AccountPurpose> specification = builder.build();
        return accountPurposeRepository.findAll(specification, pageable);
    }

    @Override
    public List<AccountPurpose> saveAll(List<AccountPurpose> list) {
        return accountPurposeRepository.saveAll(list);
    }
}
