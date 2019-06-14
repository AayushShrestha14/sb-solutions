package com.sb.solutions.api.openingForm.service;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.branch.service.BranchService;
import com.sb.solutions.api.openingForm.entity.OpeningAccount;
import com.sb.solutions.api.openingForm.entity.OpeningCustomer;
import com.sb.solutions.api.openingForm.entity.OpeningCustomerRelative;
import com.sb.solutions.api.openingForm.entity.OpeningForm;
import com.sb.solutions.api.openingForm.repository.OpeningFormRepository;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.date.validation.DateValidation;
import com.sb.solutions.core.enums.AccountStatus;
import com.sb.solutions.core.exception.ApiException;
import com.sb.solutions.core.utils.JsonConverter.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OpeningFormServiceImpl implements OpeningFormService {
    private OpeningFormRepository openingFormRepository;
    private DateValidation dateValidation;
    private BranchService branchService;
    private JsonConverter jsonConverter = new JsonConverter();

    @Autowired
    public OpeningFormServiceImpl(OpeningFormRepository openingFormRepository, DateValidation dateValidation, BranchService branchService) {
        this.openingFormRepository = openingFormRepository;
        this.dateValidation = dateValidation;
        this.branchService = branchService;
    }

    @Override
    public List<OpeningForm> findAll() {
        return openingFormRepository.findAll();
    }

    @Override
    public OpeningForm findOne(Long id) {
        OpeningForm openingForm = openingFormRepository.getOne(id);
        openingForm.setOpeningAccount(jsonConverter.convertToJson(openingForm.getCustomerDetailsJson(), OpeningAccount.class));
        return openingForm;
    }

    @Override
    public OpeningForm save(OpeningForm openingForm) {
        openingForm.setBranch(branchService.findOne(openingForm.getBranch().getId()));
        if (openingForm.getOpeningAccount().getNominee().getDateOfBirth() != null) {
            if (!dateValidation.checkDate(openingForm.getOpeningAccount().getNominee().getDateOfBirth())) {
                throw new ApiException("Invalid Date of Birth of Nominee");
            }
        }
        if (openingForm.getOpeningAccount().getBeneficiary().getDateOfBirth() != null) {
            if (!dateValidation.checkDate(openingForm.getOpeningAccount().getBeneficiary().getDateOfBirth())) {
                throw new ApiException("Invalid Date of Birth of Beneficiaries");
            }
        }
        for (OpeningCustomer openingCustomer : openingForm.getOpeningAccount().getOpeningCustomers()) {
            if (openingCustomer.getDateOfBirthAD() != null) {
                if (!dateValidation.checkDate(openingCustomer.getDateOfBirthAD())) {
                    throw new ApiException("Invalid Date of Birth of Customer");
                }
            }
            if (openingCustomer.getCitizenIssuedDate() != null) {
                if (!dateValidation.checkDate(openingCustomer.getCitizenIssuedDate())) {
                    throw new ApiException("Invalid Citizen Issued Date of Customer");
                }
            }
            if (openingCustomer.getPassportIssuedDate() != null) {
                if (!dateValidation.checkDate(openingCustomer.getPassportIssuedDate())) {
                    throw new ApiException("Invalid Password Issued Date of Customer");
                }
            }
            if (openingCustomer.getPassportExpireDate() != null) {
                if (dateValidation.checkDate(openingCustomer.getPassportExpireDate())) {
                    throw new ApiException("Invalid Passport Expire Date of Customer");
                }
            }
            if (openingCustomer.getIdCardIssuedDate() != null) {
                if (!dateValidation.checkDate(openingCustomer.getIdCardIssuedDate())) {
                    throw new ApiException("Invalid Id Issued Date of Customer");
                }
            }
            if (openingCustomer.getIdCardExpireDate() != null) {
                if (dateValidation.checkDate(openingCustomer.getIdCardExpireDate())) {
                    throw new ApiException("Invalid Id Expire Date of Customer");
                }
            }
            if (openingCustomer.getVisaIssueDate() != null) {
                if (!dateValidation.checkDate(openingCustomer.getVisaIssueDate())) {
                    throw new ApiException("Invalid Visa Issued Date of Customer");
                }
            }
            if (openingCustomer.getVisaValidity() != null) {
                if (dateValidation.checkDate(openingCustomer.getVisaValidity())) {
                    throw new ApiException("Invalid Visa Validity Date of Customer");
                }
            }
            for (OpeningCustomerRelative openingCustomerRelative : openingCustomer.getKyc().getCustomerRelatives()) {
                if (openingCustomerRelative.getCitizenshipIssuedDate() != null) {
                    if (!dateValidation.checkDate(openingCustomerRelative.getCitizenshipIssuedDate())) {
                        throw new ApiException("Invalid Citizen Issued Date of Customer Relative");
                    }
                }
            }
        }
        if (openingForm.getId() == null) {
            openingForm.setRequestedDate(new Date());
            openingForm.setStatus(AccountStatus.NEW_REQUEST);
            try {
                String url = UploadDir.accountRequest + openingForm.getBranch().getName() + "/";
                openingForm.setCustomerDetailsJson(writeJsonFile(url, openingForm));
            } catch (Exception exception) {
                throw new ApiException("File Fail to Save");
            }

        }else{
            try {
                String url = UploadDir.accountRequest + openingForm.getBranch().getName() + "/";
                writeJsonFile(url, openingForm);
            } catch (Exception exception) {
                throw new ApiException("File Fail to Save");
            }
        }

        return openingFormRepository.save(openingForm);
    }

    @Override
    public Page<OpeningForm> findAllPageable(Object t, Pageable pageable) {
        return openingFormRepository.findAll(pageable);
    }

    @Override
    public Page<OpeningForm> findAllByBranchAndAccountStatus(Branch branch, Pageable pageable, String accountStatus) {
        AccountStatus status = AccountStatus.valueOf(accountStatus);
        return openingFormRepository.findAllByBranchAndStatus(branch, pageable, status);
    }

    @Override
    public Map<Object, Object> getStatus(Long branchId) {
        return openingFormRepository.openingFormStatusCount(branchId);
    }

    @Override
    public OpeningForm updateOpeningCustomer(Long id, String status) {
        OpeningForm openingForm = openingFormRepository.getOne(id);
        openingForm.setStatus(AccountStatus.valueOf(status));
        return openingFormRepository.save(openingForm);
    }

    public String writeJsonFile(String url, OpeningForm openingForm){
        String jsonPath;
        Path path = Paths.get(url);
        if (!Files.exists(path)) {
            new File(url).mkdirs();
        }
        jsonPath = url + System.currentTimeMillis() + ".json";
        File file = new File(jsonPath);
        file.getParentFile().mkdirs();
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.write(jsonConverter.convertToJson(openingForm.getOpeningAccount()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.flush();
            return jsonPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
