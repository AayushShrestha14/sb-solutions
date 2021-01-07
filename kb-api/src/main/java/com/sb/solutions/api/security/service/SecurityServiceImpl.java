package com.sb.solutions.api.security.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.sb.solutions.api.guarantor.service.GuarantorService;
import com.sb.solutions.api.helper.HelperDto;
import com.sb.solutions.api.helper.HelperIdType;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.loan.repository.CustomerLoanRepository;
import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.api.loanConfig.service.LoanConfigService;
import com.sb.solutions.api.loanflag.entity.CustomerLoanFlag;
import com.sb.solutions.api.loanflag.service.CustomerLoanFlagService;
import com.sb.solutions.api.security.entity.Security;
import com.sb.solutions.api.security.repository.SecurityRepository;
import com.sb.solutions.core.enums.LoanFlag;

@Service
public class SecurityServiceImpl implements SecurityService {

    final SecurityRepository securityRepository;
    final GuarantorService guarantorService;
    final CustomerLoanRepository customerLoanRepository;
    final CustomerLoanFlagService customerLoanFlagService;
    final LoanConfigService loanConfigService;





    @Autowired
    public SecurityServiceImpl(SecurityRepository securityRepository,
        GuarantorService guarantorService,CustomerLoanRepository customerLoanRepository,
        CustomerLoanFlagService customerLoanFlagService,
        LoanConfigService loanConfigService) {
        this.securityRepository = securityRepository;
        this.guarantorService = guarantorService;
        this.customerLoanRepository = customerLoanRepository;
        this.customerLoanFlagService = customerLoanFlagService;
        this.loanConfigService = loanConfigService;
    }

    @Override
    public List<Security> findAll() {
        return securityRepository.findAll();
    }

    @Override
    public Security findOne(Long id) {
        return securityRepository.getOne(id);
    }

    @Override
    public Security save(Security security) {
        return securityRepository.save(security);
    }

    @Override
    public Page<Security> findAllPageable(Object t, Pageable pageable) {
        return null;
    }

    @Override
    public List<Security> saveAll(List<Security> securityList) {
        return securityRepository.saveAll(securityList);
    }

    @Override
    public void execute(Optional<HelperDto<Long>> helperDto) {
        List<CustomerLoan> allLoan = new ArrayList<>();

        if (helperDto.isPresent()) {
            // all loan  under customer info
            if (helperDto.get().getIdType().equals(HelperIdType.CUSTOMER_INFO)) {
                allLoan = customerLoanRepository.getCustomerLoanByAndLoanHolderId(helperDto.get().getId());
            } else if (helperDto.get().getIdType().equals(HelperIdType.LOAN)) {
                // current loan under customer info
                allLoan = Collections
                    .singletonList(customerLoanRepository.getOne(helperDto.get().getId()));
            }

            allLoan.forEach(loan -> {
                CustomerLoanFlag fixedFlag = loan.getLoanHolder().getLoanFlags().stream()
                    .filter(customerLoanFlag -> (customerLoanFlag.getFlag().equals(
                        LoanFlag.INSUFFICIENT_Fixed_AMOUNT) && customerLoanFlag.getCustomerLoanId()
                        .equals(loan.getId()))).collect(CustomerLoanFlag.toSingleton());

                CustomerLoanFlag shareFlag = loan.getLoanHolder().getLoanFlags().stream()
                    .filter(customerLoanFlag -> (customerLoanFlag.getFlag().equals(
                        LoanFlag.INSUFFICIENT_SHARE_AMOUNT) && customerLoanFlag.getCustomerLoanId()
                        .equals(loan.getId()))).collect(CustomerLoanFlag.toSingleton());

                CustomerLoanFlag vehicleFlag = loan.getLoanHolder().getLoanFlags().stream()
                    .filter(customerLoanFlag -> (customerLoanFlag.getFlag().equals(
                        LoanFlag.INSUFFICIENT_VEHICLE_AMOUNT) && customerLoanFlag.getCustomerLoanId()
                        .equals(loan.getId()))).collect(CustomerLoanFlag.toSingleton());

                LoanConfig loanConfigDetail = loanConfigService.findOne(loan.getLoan().getId());
                JSONParser parser = new JSONParser();
                JSONObject json = null;
                try {
                    json = (JSONObject) parser.parse(loan.getLoanHolder().getSecurity().getData());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                JSONArray array = (JSONArray) json.get("selectedArray");

                switch (loanConfigDetail.getLoanTag().name()) {
                    case "GENERAL":
                        customerLoanFlagService
                            .deleteCustomerLoanFlagById(!ObjectUtils.isEmpty(fixedFlag) ? fixedFlag.getId() : null);
                        customerLoanFlagService
                            .deleteCustomerLoanFlagById(!ObjectUtils.isEmpty(shareFlag) ? shareFlag.getId() : null);
                        customerLoanFlagService
                            .deleteCustomerLoanFlagById(!ObjectUtils.isEmpty(vehicleFlag) ? vehicleFlag.getId() : null);
                        break;
                    case "VEHICLE":
                        Boolean vehicleSecurity = array.stream()
                            .anyMatch(a -> a.equals("VehicleSecurity"));
                        BigDecimal requiredCollateralRequired =  (loan.getProposal().getProposedLimit().multiply(BigDecimal.valueOf(loanConfigDetail.getCollateralRequirement()))).divide(new BigDecimal(100));
                        if (vehicleSecurity) {
                            JSONObject initialForm = (JSONObject) json.get("initialForm");
                            JSONArray vehicleDetailsArray = (JSONArray) initialForm
                                .get("vehicleDetails");
                            final BigDecimal[] totalVehicleValuationAmount = {BigDecimal.ZERO};
                            vehicleDetailsArray.forEach(data -> {
                                JSONObject s = (JSONObject) data;
                                totalVehicleValuationAmount[0] = totalVehicleValuationAmount[0].add(!ObjectUtils.isEmpty(s.get("valuationAmount")) ? new BigDecimal(s.get("valuationAmount").toString()) : BigDecimal.ZERO);
                            });
                            boolean flag = requiredCollateralRequired
                                .compareTo(totalVehicleValuationAmount[0]) >= 1; //proposalAmount is greater than vehicleSecurityAmount
                            BigDecimal insufficientValue = requiredCollateralRequired.subtract(totalVehicleValuationAmount[0]);
                            System.out.println(flag);
                            setSecurityFlag(flag,vehicleFlag,loan,insufficientValue,LoanFlag.INSUFFICIENT_VEHICLE_AMOUNT);

                        } else {
                            setSecurityFlag(true,vehicleFlag,loan,requiredCollateralRequired,LoanFlag.INSUFFICIENT_VEHICLE_AMOUNT);
                        }
                        if (shareFlag != null) {
                            customerLoanFlagService
                                .deleteCustomerLoanFlagById(shareFlag.getId());
                        }
                        if (fixedFlag != null) {
                            customerLoanFlagService
                                .deleteCustomerLoanFlagById(fixedFlag.getId());
                        }
                        break;
                    case "FIXED_DEPOSIT":
                        Boolean fixedDeposit = array.stream()
                            .anyMatch(a -> a.equals("FixedDeposit"));
                        BigDecimal requiredFixedCollateralRequired =  (loan.getProposal().getProposedLimit().multiply(BigDecimal.valueOf(loanConfigDetail.getCollateralRequirement()))).divide(new BigDecimal(100));

                        if (fixedDeposit) {
                            JSONObject initialForm = (JSONObject) json.get("initialForm");
                            JSONArray fixedDepositDetailsArray = (JSONArray) initialForm
                                .get("fixedDepositDetails");
                            final BigDecimal[] totalFixedDepositAmount = {BigDecimal.ZERO};
                            fixedDepositDetailsArray.forEach(data -> {
                                JSONObject s = (JSONObject) data;
                                 totalFixedDepositAmount[0] = totalFixedDepositAmount[0].add(!ObjectUtils.isEmpty(s.get("amount")) ? new BigDecimal(s.get("amount").toString()) : BigDecimal.ZERO);
                            });
                            boolean flag = requiredFixedCollateralRequired
                                .compareTo(totalFixedDepositAmount[0]) >= 1; //proposalAmount is greater than fixedSecurityAmount
                            BigDecimal insufficientValue = requiredFixedCollateralRequired.subtract(totalFixedDepositAmount[0]);
                            System.out.println(flag);
                            setSecurityFlag(flag,fixedFlag,loan,insufficientValue,LoanFlag.INSUFFICIENT_Fixed_AMOUNT);

                        } else {
                            setSecurityFlag(true,fixedFlag,loan,requiredFixedCollateralRequired,LoanFlag.INSUFFICIENT_Fixed_AMOUNT);
                        }
                        if (shareFlag != null) {
                            customerLoanFlagService
                                .deleteCustomerLoanFlagById(shareFlag.getId());
                        }
                        if (vehicleFlag != null) {
                            customerLoanFlagService
                                .deleteCustomerLoanFlagById(vehicleFlag.getId());
                        }
                        break;
                }
            });
        }
    }

    public void setSecurityFlag(Boolean flag,CustomerLoanFlag loanFlag,CustomerLoan loan, BigDecimal insufficientValue,LoanFlag flagEnum){
        if (flag && loanFlag == null) {
            loanFlag = new CustomerLoanFlag();
            loanFlag.setCustomerInfo(loan.getLoanHolder());
            loanFlag.setCustomerLoanId(loan.getId());
            loanFlag.setFlag(flagEnum);
            loanFlag.setDescription(String
                .format(flagEnum.getValue()[1],
                    insufficientValue));
            loanFlag.setOrder(
                Integer.parseInt(flagEnum.getValue()[0]));
            customerLoanFlagService.save(loanFlag);
        } else if (!flag && loanFlag != null) {
            customerLoanFlagService
                .deleteCustomerLoanFlagById(loanFlag.getId());
        }
    }
}
