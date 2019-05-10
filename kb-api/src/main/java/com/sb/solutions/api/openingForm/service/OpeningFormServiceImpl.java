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

import java.util.Date;
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
        openingForm.setRequestedDate(new Date());
        for(OpeningCustomer openingCustomer: openingForm.getOpeningCustomers()){
            if(openingCustomer.getDateOfBirthAD() != null){
                dateValidation.checkDateBefore(openingCustomer.getDateOfBirthAD());
                //throw new ApiException("Invalid Date of Birth of Customer");
            }
            if(openingCustomer.getCitizenIssuedDate() != null){
                dateValidation.checkDateBefore(openingCustomer.getCitizenIssuedDate());
                //throw new ApiException("Invalid Citizen Issued Date of Customer");
            }
            if(openingCustomer.getPassportIssuedDate() != null){
                dateValidation.checkDateBefore(openingCustomer.getPassportIssuedDate());
                //throw new ApiException("Invalid Password Issued Date of Customer");
            }
            if(openingCustomer.getPassportExpireDate()!= null){
                dateValidation.checkDateAfter(openingCustomer.getPassportExpireDate());
                //throw new ApiException("Invalid Passport Expire Date of Customer");
            }
            if(openingCustomer.getIdCardIssuedDate() != null){
                dateValidation.checkDateBefore(openingCustomer.getIdCardIssuedDate());
                //throw new ApiException("Invalid Id Issued Date of Customer");
            }
            if(openingCustomer.getIdCardExpireDate() != null){
                dateValidation.checkDateAfter(openingCustomer.getIdCardExpireDate());
                //throw new ApiException("Invalid Id Expire Date of Customer");
            }
            if(openingCustomer.getVisaIssueDate() != null){
                dateValidation.checkDateBefore(openingCustomer.getVisaIssueDate());
                //throw new ApiException("Invalid Visa Issued Date of Customer");
            }
            if(openingCustomer.getVisaValidity() != null){
                dateValidation.checkDateAfter(openingCustomer.getVisaValidity());
                //throw new ApiException("Invalid Visa Validity Date of Customer");
            }
            for(OpeningNominee openingNominee: openingCustomer.getNominees()){
                if(openingNominee.getDateOfBirth() != null){
                    dateValidation.checkDateBefore(openingNominee.getDateOfBirth());
                    //throw new ApiException("Invalid Date of Birth of Nominee");
                }
            }
            if(openingCustomer.getBeneficiary().getDateOfBirth() != null){
                dateValidation.checkDateBefore(openingCustomer.getBeneficiary().getDateOfBirth());
                //throw new ApiException("Invalid Date of Birth of Beneficiaries");
            }
            for(OpeningCustomerRelative openingCustomerRelative: openingCustomer.getKyc().getCustomerRelatives()){
                if(openingCustomerRelative.getCitizenshipIssuedDate() != null){
                    dateValidation.checkDateBefore(openingCustomerRelative.getCitizenshipIssuedDate());
                    //throw new ApiException("Invalid Citizen Issued Date of Customer Relative");
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
