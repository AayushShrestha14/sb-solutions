package com.sb.solutions.api.openingForm.service;

import com.google.gson.Gson;
import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.branch.service.BranchService;
import com.sb.solutions.api.openingForm.entity.*;
import com.sb.solutions.api.openingForm.repository.OpeningFormRepository;
import com.sb.solutions.core.constant.FilePath;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.dateValidation.DateValidation;
import com.sb.solutions.core.exception.ApiException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class OpeningFormServiceImpl implements OpeningFormService {
    private OpeningFormRepository openingFormRepository;
    private Gson gson;
    private DateValidation dateValidation;
    private BranchService branchService;
    @Override
    public List<OpeningForm> findAll() {
        return openingFormRepository.findAll();
    }

    @Override
    public OpeningForm findOne(Long id) {
        OpeningForm openingForm =  openingFormRepository.getOne(id);
        openingForm.setOpeningAccount(convertJson(openingForm.getCustomerDetailsJson()));
        return openingForm;
    }

    @Override
    public OpeningForm save(OpeningForm openingForm) {
        String jsonPath = "";
        openingForm.setRequestedDate(new Date());
        openingForm.setBranch(branchService.findOne(openingForm.getBranch().getId()));
        if(openingForm.getOpeningAccount().getNominee().getDateOfBirth() != null){
            if(!dateValidation.checkDate(openingForm.getOpeningAccount().getNominee().getDateOfBirth())) {
                throw new ApiException("Invalid Date of Birth of Nominee");
            }
        }
        if(openingForm.getOpeningAccount().getBeneficiary().getDateOfBirth() != null){
            if(!dateValidation.checkDate(openingForm.getOpeningAccount().getBeneficiary().getDateOfBirth())) {
                throw new ApiException("Invalid Date of Birth of Beneficiaries");
            }
        }
        for(OpeningCustomer openingCustomer: openingForm.getOpeningAccount().getOpeningCustomers()){
            if(openingCustomer.getDateOfBirthAD() != null){
                if(!dateValidation.checkDate(openingCustomer.getDateOfBirthAD())) {
                    throw new ApiException("Invalid Date of Birth of Customer");
                }
            }
            if(openingCustomer.getCitizenIssuedDate() != null){
                if(!dateValidation.checkDate(openingCustomer.getCitizenIssuedDate())) {
                    throw new ApiException("Invalid Citizen Issued Date of Customer");
                }
            }
            if(openingCustomer.getPassportIssuedDate() != null){
                if(!dateValidation.checkDate(openingCustomer.getPassportIssuedDate())) {
                    throw new ApiException("Invalid Password Issued Date of Customer");
                }
            }
            if(openingCustomer.getPassportExpireDate()!= null){
                if(dateValidation.checkDate(openingCustomer.getPassportExpireDate())) {
                    throw new ApiException("Invalid Passport Expire Date of Customer");
                }
            }
            if(openingCustomer.getIdCardIssuedDate() != null){
                if(!dateValidation.checkDate(openingCustomer.getIdCardIssuedDate())) {
                    throw new ApiException("Invalid Id Issued Date of Customer");
                }
            }
            if(openingCustomer.getIdCardExpireDate() != null){
                if(dateValidation.checkDate(openingCustomer.getIdCardExpireDate())) {
                    throw new ApiException("Invalid Id Expire Date of Customer");
                }
            }
            if(openingCustomer.getVisaIssueDate() != null){
                if(!dateValidation.checkDate(openingCustomer.getVisaIssueDate())) {
                    throw new ApiException("Invalid Visa Issued Date of Customer");
                }
            }
            if(openingCustomer.getVisaValidity() != null){
                if(dateValidation.checkDate(openingCustomer.getVisaValidity())) {
                    throw new ApiException("Invalid Visa Validity Date of Customer");
                }
            }
            for(OpeningCustomerRelative openingCustomerRelative: openingCustomer.getKyc().getCustomerRelatives()){
                if(openingCustomerRelative.getCitizenshipIssuedDate() != null){
                    if(!dateValidation.checkDate(openingCustomerRelative.getCitizenshipIssuedDate())) {
                        throw new ApiException("Invalid Citizen Issued Date of Customer Relative");
                    }
                }
            }
        }
        try {
            FilePath filePath = new FilePath();
            String url = filePath.getOSPath() + UploadDir.accountRequest+openingForm.getBranch().getName()+"/";
            Path path = Paths.get(url);
            if(!Files.exists(path)) {
                new File(url).mkdirs();
            }
            jsonPath = url + openingForm.getFullName() +"_"+System.currentTimeMillis()+".json";
            File file = new File(jsonPath);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            writer.write(gson.toJson(openingForm.getOpeningAccount()));
            writer.flush();
        }catch (Exception exception){

        }
        openingForm.setCustomerDetailsJson(jsonPath);
        return openingFormRepository.save(openingForm);
    }

    @Override
    public Page<OpeningForm> findAllPageable(Object t, Pageable pageable) {
        return openingFormRepository.findAll(pageable);
    }

    @Override
    public Page<OpeningForm> findAllByBranch(Branch branch, Pageable pageable) {
        Page<OpeningForm> openingForms = openingFormRepository.findAllByBranch(branch, pageable);
        for(OpeningForm openingForm: openingForms){
            openingForm.setOpeningAccount(convertJson(openingForm.getCustomerDetailsJson()));
        }
        return openingForms;
    }

    public OpeningAccount convertJson(String path){
        StringBuffer jsonCustomerData = new StringBuffer();
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            while((line =bufferedReader.readLine())!=null){
                jsonCustomerData.append(line);
            }
            Gson g = new Gson();
            OpeningAccount openingAccount = g.fromJson(jsonCustomerData.toString(), OpeningAccount.class);
            return openingAccount;
        }catch (Exception e) {
            throw new ApiException(e.toString());
        }
    }
}
