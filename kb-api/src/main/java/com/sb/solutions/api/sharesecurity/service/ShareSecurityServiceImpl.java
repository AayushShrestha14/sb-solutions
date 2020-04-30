package com.sb.solutions.api.sharesecurity.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.loan.repository.CustomerLoanRepository;
import com.sb.solutions.api.loanflag.entity.CustomerLoanFlag;
import com.sb.solutions.api.loanflag.service.CustomerLoanFlagService;
import com.sb.solutions.api.nepseCompany.entity.CustomerShareData;
import com.sb.solutions.api.nepseCompany.entity.NepseCompany;
import com.sb.solutions.api.nepseCompany.entity.NepseMaster;
import com.sb.solutions.api.nepseCompany.repository.NepseCompanyRepository;
import com.sb.solutions.api.nepseCompany.repository.NepseMasterRepository;
import com.sb.solutions.api.sharesecurity.ShareSecurity;
import com.sb.solutions.api.sharesecurity.repository.ShareSecurityRepo;
import com.sb.solutions.core.constant.AppConstant;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.enums.LoanFlag;
import com.sb.solutions.core.enums.ShareType;
import com.sb.solutions.core.enums.Status;

@Service("shareSecurityService")
public class ShareSecurityServiceImpl implements ShareSecurityService {

    private static final Logger logger = LoggerFactory.getLogger(ShareSecurityService.class);
    private final CustomerLoanFlagService customerLoanFlagService;
    private final NepseMasterRepository nepseMasterRepository;
    private final NepseCompanyRepository nepseCompanyRepository;
    private final CustomerLoanRepository customerLoanRepository;
    private final ShareSecurityRepo shareSecurityRepository;

    public ShareSecurityServiceImpl(
        ShareSecurityRepo shareSecurityRepository,
        NepseMasterRepository nepseMasterRepository,
        NepseCompanyRepository nepseCompanyRepository,
        CustomerLoanRepository customerLoanRepository,
        CustomerLoanFlagService customerLoanFlagService) {
        this.shareSecurityRepository = shareSecurityRepository;
        this.nepseMasterRepository = nepseMasterRepository;
        this.nepseCompanyRepository = nepseCompanyRepository;
        this.customerLoanRepository = customerLoanRepository;
        this.customerLoanFlagService = customerLoanFlagService;
    }

    @Override
    public List<ShareSecurity> findAll() {
        return shareSecurityRepository.findAll();
    }

    @Override
    public ShareSecurity findOne(Long id) {
        return shareSecurityRepository.getOne(id);
    }

    @Override
    public ShareSecurity save(ShareSecurity shareSecurity) {
        return shareSecurityRepository.save(shareSecurity);
    }

    @Override
    public Page<ShareSecurity> findAllPageable(Object t, Pageable pageable) {
        return shareSecurityRepository.findAll(pageable);
    }

    @Override
    public void execute(Optional<Long> optional) {
        final NepseMaster master = nepseMasterRepository.findByStatus(Status.ACTIVE);
        final Map<ShareType, Double> masterMap = new HashMap<ShareType, Double>() {{
            put(ShareType.ORDINARY, master.getOrdinary() / 100);
            put(ShareType.PROMOTER, master.getPromoter() / 100);
        }};
        final Map<String, Double> marketPriceMap = nepseCompanyRepository.findAll().stream()
            .collect(
                Collectors.toMap(NepseCompany::getCompanyCode, NepseCompany::getAmountPerUnit));
        List<CustomerLoan> loan;
        if (optional.isPresent()) {
            loan = new ArrayList<>(
                Arrays.asList(customerLoanRepository.getOne(optional.get())));
        } else {
            loan = getLoanHavingShareTemplate();
        }
        loan.stream()
            .filter(customerLoan -> (null != customerLoan.getProposal().getProposedLimit()
                && customerLoan.getProposal().getProposedLimit().compareTo(BigDecimal.ZERO)
                > 0))
            .forEach(customerLoan -> {
                logger.info("Customer loan id {} ", customerLoan.getId());
                ShareSecurity shareSecurity = customerLoan.getShareSecurity();
                if (null != shareSecurity) {
                    List<CustomerShareData> shareDataList = shareSecurity.getCustomerShareData();
                    AtomicReference<BigDecimal> reCalculateAmount = new AtomicReference<>(
                        BigDecimal.ZERO);
                    shareDataList.forEach(customerShareData -> {

                        String companyCode = customerShareData.getCompanyCode();
                        if (marketPriceMap.containsKey(companyCode)) {
                            BigDecimal newValue = reCalculateAmount.get()
                                .add(BigDecimal.valueOf(marketPriceMap.get(companyCode))
                                    .multiply(
                                        BigDecimal.valueOf(customerShareData.getTotalShareUnit()))
                                    .multiply(BigDecimal
                                        .valueOf(masterMap.get(customerShareData.getShareType()))));
                            reCalculateAmount.set(newValue);

                        }
                    });
                    logger.info("Recalculate amount {} ===  {} proposal limit",
                        reCalculateAmount.get(), customerLoan.getProposal().getProposedLimit());

                    CustomerLoanFlag customerLoanFlag = customerLoanFlagService
                        .findCustomerLoanFlagByFlagAndCustomerLoanId(
                            LoanFlag.INSUFFICIENT_SHARE_AMOUNT, customerLoan.getId());
                    boolean flag = customerLoan.getProposal().getProposedLimit()
                        .compareTo(reCalculateAmount.get()) >= 1;
                    if (flag && customerLoanFlag == null) {
                        customerLoanFlag = new CustomerLoanFlag();
                        customerLoanFlag.setCustomerLoan(customerLoan);
                        customerLoanFlag.setFlag(LoanFlag.INSUFFICIENT_SHARE_AMOUNT);
                        customerLoanFlag.setDescription(String
                            .format(LoanFlag.INSUFFICIENT_SHARE_AMOUNT.getValue()[1],
                                reCalculateAmount.get()));
                        customerLoanFlag.setOrder(
                            Integer.parseInt(LoanFlag.INSUFFICIENT_SHARE_AMOUNT.getValue()[0]));
                        customerLoanFlagService.save(customerLoanFlag);
                    } else if (!flag && customerLoanFlag != null) {
                        customerLoanFlagService
                            .deleteCustomerLoanFlagById(customerLoanFlag.getId());
                    }
                }
            });

    }

    @Override
    public List<ShareSecurity> saveAll(List<ShareSecurity> list) {
        return shareSecurityRepository.saveAll(list);
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
