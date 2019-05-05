package com.sb.solutions.api.openingForm.service;

import com.google.gson.Gson;
import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.openingForm.entity.OpeningForm;
import com.sb.solutions.api.openingForm.repository.OpeingFormRepository;
import lombok.AllArgsConstructor;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OpeningServiceImpl implements OpeningFormService {
    private OpeingFormRepository opeingFormRepository;
    private Gson gson;
    @Override
    public List<OpeningForm> findAll() {
        return opeingFormRepository.findAll();
    }

    @Override
    public OpeningForm findOne(Long id) {
        return opeingFormRepository.getOne(id);
    }

    @Override
    public OpeningForm save(OpeningForm openingForm) {
        System.out.println(gson.toJson(openingForm.getOpeningCustomers()));
        openingForm.setCustomerDetailsJson(gson.toJson(openingForm.getOpeningCustomers()));
        return opeingFormRepository.save(openingForm);
    }

    @Override
    public Page<OpeningForm> findAllPageable(Object t, Pageable pageable) {
        return opeingFormRepository.findAll(pageable);
    }

    @Override
    public Page<OpeningForm> findAllByBranch(Branch branch, Pageable pageable) {
        return opeingFormRepository.findAllByBranch(branch, pageable);
    }
}
