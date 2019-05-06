package com.sb.solutions.api.openingForm.service;

import com.google.gson.Gson;
import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.openingForm.entity.OpeningForm;
import com.sb.solutions.api.openingForm.repository.OpeningFormRepository;
import com.sb.solutions.core.dateValidation.DateValidation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OpeningFormServiceImpl implements OpeningFormService {
    private OpeningFormRepository openingFormRepository;
    private Gson gson;
    private DateValidation dateValidation;
    @Override
    public List<OpeningForm> findAll() {
        return openingFormRepository.findAll();
    }

    @Override
    public OpeningForm findOne(Long id) {
        return openingFormRepository.getOne(id);
    }

    @Override
    public OpeningForm save(OpeningForm openingForm) {
        System.out.println(openingForm.getOpeningCustomers());
        System.out.println(gson.toJson(openingForm.getOpeningCustomers()));
        openingForm.setCustomerDetailsJson(gson.toJson(openingForm.getOpeningCustomers()));
        return openingFormRepository.save(openingForm);
    }

    @Override
    public Page<OpeningForm> findAllPageable(Object t, Pageable pageable) {
        return openingFormRepository.findAll(pageable);
    }

    @Override
    public Page<OpeningForm> findAllByBranch(Branch branch, Pageable pageable) {
        return openingFormRepository.findAllByBranch(branch, pageable);
    }
}
