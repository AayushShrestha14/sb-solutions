package com.sb.solutions.api.openingForm.service;

import com.google.gson.Gson;
import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.openingForm.entity.*;
import com.sb.solutions.api.openingForm.repository.OpeningFormRepository;
import com.sb.solutions.core.dateValidation.DateValidation;
import com.sb.solutions.core.exception.ApiException;
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
        for(OpeningCustomer openingCustomer: openingForm.getOpeningCustomers()){
            if(!dateValidation.checkDate(openingCustomer.getDateOfBirthAD())){
                throw new ApiException("Invalid Date of Birth of Customer");
            }
            if(dateValidation.checkDate(openingCustomer.getCitizenIssuedDate())){
                throw new ApiException("Invalid Citizen Issued Date of Customer");
            }
            if(dateValidation.checkDate(openingCustomer.getPassportIssuedDate())){
                throw new ApiException("Invalid Password Issued Date of Customer");
            }
            if(!dateValidation.checkDate(openingCustomer.getPassportExpireDate())){
                throw new ApiException("Invalid Passport Expire Date of Customer");
            }
            if(dateValidation.checkDate(openingCustomer.getIdCardIssuedDate())){
                throw new ApiException("Invalid Id Issued Date of Customer");
            }
            if(!dateValidation.checkDate(openingCustomer.getIdCardExpireDate())){
                throw new ApiException("Invalid Id Expire Date of Customer");
            }
            if(dateValidation.checkDate(openingCustomer.getVisaIssueDate())){
                throw new ApiException("Invalid Visa Issued Date of Customer");
            }
            if(!dateValidation.checkDate(openingCustomer.getVisaValidity())){
                throw new ApiException("Invalid Visa Validity Date of Customer");
            }
            if(!dateValidation.checkDate(openingCustomer.getNominee().getDateOfBirth())){
                throw new ApiException("Invalid Date of Birth of Nominee");
            }
            for(OpeningBeneficiary openingBeneficiary: openingCustomer.getBeneficiaries()){
                if(!dateValidation.checkDate(openingBeneficiary.getDateOfBirth())){
                    throw new ApiException("Invalid Date of Birth of Beneficiaries");
                }
            }
            for(OpeningCustomerRelative openingCustomerRelative: openingCustomer.getKyc().getCustomerRelatives()){
                if(dateValidation.checkDate(openingCustomerRelative.getCitizenshipIssuedDate())){
                    throw new ApiException("Invalid Citizen Issued Date of Customer Relative");
                }
            }
        }
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
