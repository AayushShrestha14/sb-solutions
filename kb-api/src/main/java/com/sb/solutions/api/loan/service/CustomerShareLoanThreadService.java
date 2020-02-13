package com.sb.solutions.api.loan.service;

import com.sb.solutions.api.loan.dto.LoanRemarkDto;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.loan.repository.CustomerLoanRepository;
import com.sb.solutions.api.nepseCompany.entity.CustomerShareData;
import com.sb.solutions.api.nepseCompany.entity.NepseCompany;
import com.sb.solutions.api.nepseCompany.entity.NepseMaster;
import com.sb.solutions.api.nepseCompany.repository.NepseCompanyRepository;
import com.sb.solutions.api.nepseCompany.repository.NepseMasterRepository;
import com.sb.solutions.api.sharesecurity.ShareSecurity;
import com.sb.solutions.core.constant.AppConstant;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.enums.ShareType;
import com.sb.solutions.core.enums.Status;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.google.gson.Gson;

/**
 * @author Sunil Babu Shrestha on 1/29/2020
 */
@Service
@AllArgsConstructor
public class CustomerShareLoanThreadService implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CustomerShareLoanThreadService.class);
    private static final byte LIMIT_EXCEED_YES = 1;
    private static final byte LIMIT_EXCEED_NO = 0;


    private final CustomerLoanRepository customerLoanRepository;
    private final NepseCompanyRepository nepseCompanyRepository;
    private final NepseMasterRepository nepseMasterRepository;
    private Map<String, Double> marketPriceMap = null;
    private Map<ShareType, Double> masterMap = null;

    @Override
    public void run() {
        logger.info("Share Loan Verification Started");
        NepseMaster master = nepseMasterRepository.findByStatus(Status.ACTIVE);
        masterMap = new HashMap<ShareType, Double>() {{
            put(ShareType.ORDINARY, master.getOrdinary() / 100);
            put(ShareType.PROMOTER, master.getPromoter() / 100);
        }};
        marketPriceMap = nepseCompanyRepository.findAll().stream()
                .collect(Collectors.toMap(NepseCompany::getCompanyCode, NepseCompany::getAmountPerUnit));

        verifyShareTemplateLoan();
        logger.info("Share Loan Verification Ended");
    }

    private void verifyShareTemplateLoan() {
        List<CustomerLoan> loan = getLoanHavingShareTemplate();
        loan.stream().filter(customerLoan -> (null != customerLoan.getProposal()
                && customerLoan.getProposal().getProposedLimit().compareTo(BigDecimal.ZERO) > 0))
                .forEach(customerLoan -> {
                    logger.info("Customer loan id {} ", customerLoan.getId());
                    ShareSecurity shareSecurity = customerLoan.getShareSecurity();
                    if (null != shareSecurity) {
                        List<CustomerShareData> shareDataList = shareSecurity.getCustomerShareData();
                        AtomicReference<BigDecimal> reCalculateAmount = new AtomicReference<>(BigDecimal.ZERO);
                        shareDataList.stream().forEach(customerShareData -> {

                            String companyCode = customerShareData.getCompanyCode();
                            if (marketPriceMap.containsKey(companyCode)) {
                                BigDecimal newValue = reCalculateAmount.get().add(BigDecimal.valueOf(marketPriceMap.get(companyCode))
                                        .multiply(BigDecimal.valueOf(customerShareData.getTotalShareUnit())).multiply(BigDecimal.valueOf(masterMap.get(customerShareData.getShareType()))));
                                reCalculateAmount.set(newValue);

                            }
                        });
                        logger.info(" Recalculate amount {} ===  {} proposal limt", reCalculateAmount.get(), customerLoan.getProposal().getProposedLimit());
                        byte limitExccedFlag = LIMIT_EXCEED_NO;
                        if (customerLoan.getProposal().getProposedLimit().compareTo(reCalculateAmount.get()) >= 1) {
                            limitExccedFlag = LIMIT_EXCEED_YES;
                        }
                        if (customerLoan.getLimitExceed() != limitExccedFlag) {
                            Gson gson = new Gson();
                            LoanRemarkDto loanRemarkDto = customerLoan.getLoanRemarks() == null
                                ? new LoanRemarkDto()
                                : gson.fromJson(customerLoan.getLoanRemarks(), LoanRemarkDto.class);
                            loanRemarkDto.setLimitExceed(limitExccedFlag == LIMIT_EXCEED_YES
                                ? "Loan cannot be forwarded due to insufficient collateral or security considered value"
                                : null);
                            String remark = gson.toJson(loanRemarkDto);
                            customerLoanRepository
                                .updateLimitExceed(limitExccedFlag, remark, customerLoan.getId());
                        }

                    }
                });

    }

    private List<CustomerLoan> getLoanHavingShareTemplate() {
        final Specification<CustomerLoan> specification = (Specification<CustomerLoan>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate nonClosedLoanFilter = criteriaBuilder
                    .and(criteriaBuilder
                            .notEqual(root.get("documentStatus"),
                                    DocStatus.CLOSED));
            Predicate nonRejectedLoanFilter = criteriaBuilder
                    .and(criteriaBuilder
                            .notEqual(root.get("documentStatus"),
                                    DocStatus.REJECTED));
            Predicate predicateForShareTemplate = criteriaBuilder.
                    isMember(AppConstant.TEMPLATE_SHARE_SECURITY, root.join("loan").get("templateList"));
            return criteriaBuilder.and(nonClosedLoanFilter, nonRejectedLoanFilter, predicateForShareTemplate);
        };
        return customerLoanRepository.findAll(specification);
    }
}
