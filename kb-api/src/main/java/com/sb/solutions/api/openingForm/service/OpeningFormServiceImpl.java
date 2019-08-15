package com.sb.solutions.api.openingForm.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.branch.service.BranchService;
import com.sb.solutions.api.openingForm.entity.OpeningAccount;
import com.sb.solutions.api.openingForm.entity.OpeningCustomer;
import com.sb.solutions.api.openingForm.entity.OpeningCustomerRelative;
import com.sb.solutions.api.openingForm.entity.OpeningForm;
import com.sb.solutions.api.openingForm.repository.OpeningFormRepository;
import com.sb.solutions.api.openingForm.repository.specification.OpeningFormSpecBuilder;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.constant.FilePath;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.date.validation.DateValidation;
import com.sb.solutions.core.enums.AccountStatus;
import com.sb.solutions.core.exception.ServiceValidationException;
import com.sb.solutions.core.utils.jsonConverter.JsonConverter;

@Service
public class OpeningFormServiceImpl implements OpeningFormService {

    private OpeningFormRepository openingFormRepository;
    private DateValidation dateValidation;
    private BranchService branchService;
    private UserService userService;
    private JsonConverter jsonConverter = new JsonConverter();

    @Autowired
    public OpeningFormServiceImpl(OpeningFormRepository openingFormRepository,
        DateValidation dateValidation,
        BranchService branchService,
        UserService userService) {
        this.openingFormRepository = openingFormRepository;
        this.dateValidation = dateValidation;
        this.branchService = branchService;
        this.userService = userService;
    }

    @Override
    public List<OpeningForm> findAll() {
        return openingFormRepository.findAll();
    }

    @Override
    public OpeningForm findOne(Long id) {
        OpeningForm openingForm = openingFormRepository.getOne(id);
        openingForm.setOpeningAccount(jsonConverter
            .convertToJson(FilePath.getOSPath() + openingForm.getCustomerDetailsJson(),
                OpeningAccount.class));
        return openingForm;
    }

    @Override
    public OpeningForm save(OpeningForm openingForm) {
        openingForm.setBranch(branchService.findOne(openingForm.getBranch().getId()));
        if (openingForm.getOpeningAccount().getNominee().getDateOfBirth() != null) {
            if (!dateValidation
                .checkDate(openingForm.getOpeningAccount().getNominee().getDateOfBirth())) {
                throw new ServiceValidationException("Invalid Date of Birth of Nominee");
            }
        }
        if (openingForm.getOpeningAccount().getBeneficiary().getDateOfBirth() != null) {
            if (!dateValidation
                .checkDate(openingForm.getOpeningAccount().getBeneficiary().getDateOfBirth())) {
                throw new ServiceValidationException("Invalid Date of Birth of Beneficiaries");
            }
        }
        for (OpeningCustomer openingCustomer : openingForm.getOpeningAccount()
            .getOpeningCustomers()) {
            if (openingCustomer.getDateOfBirthAD() != null) {
                if (!dateValidation.checkDate(openingCustomer.getDateOfBirthAD())) {
                    throw new ServiceValidationException("Invalid Date of Birth of Customer");
                }
            }
            if (openingCustomer.getCitizenIssuedDate() != null) {
                if (!dateValidation.checkDate(openingCustomer.getCitizenIssuedDate())) {
                    throw new ServiceValidationException("Invalid Citizen Issued Date of Customer");
                }
            }
            if (openingCustomer.getPassportIssuedDate() != null) {
                if (!dateValidation.checkDate(openingCustomer.getPassportIssuedDate())) {
                    throw new ServiceValidationException(
                        "Invalid Password Issued Date of Customer");
                }
            }
            if (openingCustomer.getPassportExpireDate() != null) {
                if (dateValidation.checkDate(openingCustomer.getPassportExpireDate())) {
                    throw new ServiceValidationException(
                        "Invalid Passport Expire Date of Customer");
                }
            }
            if (openingCustomer.getIdCardIssuedDate() != null) {
                if (!dateValidation.checkDate(openingCustomer.getIdCardIssuedDate())) {
                    throw new ServiceValidationException("Invalid Id Issued Date of Customer");
                }
            }
            if (openingCustomer.getIdCardExpireDate() != null) {
                if (dateValidation.checkDate(openingCustomer.getIdCardExpireDate())) {
                    throw new ServiceValidationException("Invalid Id Expire Date of Customer");
                }
            }
            if (openingCustomer.getVisaIssueDate() != null) {
                if (!dateValidation.checkDate(openingCustomer.getVisaIssueDate())) {
                    throw new ServiceValidationException("Invalid Visa Issued Date of Customer");
                }
            }
            if (openingCustomer.getVisaValidity() != null) {
                if (dateValidation.checkDate(openingCustomer.getVisaValidity())) {
                    throw new ServiceValidationException("Invalid Visa Validity Date of Customer");
                }
            }
            for (OpeningCustomerRelative openingCustomerRelative : openingCustomer.getKyc()
                .getCustomerRelatives()) {
                if (openingCustomerRelative.getCitizenshipIssuedDate() != null) {
                    if (!dateValidation
                        .checkDate(openingCustomerRelative.getCitizenshipIssuedDate())) {
                        throw new ServiceValidationException(
                            "Invalid Citizen Issued Date of Customer Relative");
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
                throw new ServiceValidationException("File Fail to Save");
            }

        } else {
            try {
                String url = openingForm.getCustomerDetailsJson();
                openingForm.setCustomerDetailsJson(updateJsonFile(url, openingForm));
            } catch (Exception exception) {
                throw new ServiceValidationException("File Fail to Save");
            }
        }

        return openingFormRepository.save(openingForm);
    }

    @Override
    public Page<OpeningForm> findAllPageable(Object t, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> s = objectMapper.convertValue(t, Map.class);
        String currentUserBranches = userService.getRoleAccessFilterByBranch().stream()
            .map(Object::toString).collect(Collectors.joining(","));
        s.put("branch", s.getOrDefault("branch", currentUserBranches));
        OpeningFormSpecBuilder builder = new OpeningFormSpecBuilder(s);
        Specification<OpeningForm> specification = builder.build();
        return openingFormRepository.findAll(specification, pageable);
    }

    @Override
    public Map<Object, Object> getStatus() {
        List<Long> currentUserBranches = userService.getRoleAccessFilterByBranch();
        return openingFormRepository.openingFormStatusCount(currentUserBranches);
    }

    public String writeJsonFile(String url, OpeningForm openingForm) {
        String jsonPath;
        Path path = Paths.get(FilePath.getOSPath() + url);
        if (!Files.exists(path)) {
            new File(FilePath.getOSPath() + url).mkdirs();
        }
        jsonPath = url + System.currentTimeMillis() + ".json";
        File file = new File(FilePath.getOSPath() + jsonPath);
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

    public String updateJsonFile(String url, OpeningForm openingForm) {
        String jsonPath = url;
        try {
            FileWriter writer = new FileWriter(FilePath.getOSPath() + url);
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
        } catch (Exception e) {
            jsonPath = writeJsonFile(
                UploadDir.accountRequest + openingForm.getBranch().getName() + "/", openingForm);
            return jsonPath;
        }
        return null;
    }
}
