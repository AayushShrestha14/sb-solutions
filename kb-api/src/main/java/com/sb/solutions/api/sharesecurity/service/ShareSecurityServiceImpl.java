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
import com.sb.solutions.api.nepseCompany.entity.CustomerShareData;
import com.sb.solutions.api.nepseCompany.entity.NepseCompany;
import com.sb.solutions.api.nepseCompany.entity.NepseMaster;
import com.sb.solutions.api.nepseCompany.repository.NepseCompanyRepository;
import com.sb.solutions.api.nepseCompany.repository.NepseMasterRepository;
import com.sb.solutions.api.sharesecurity.ShareSecurity;
import com.sb.solutions.api.sharesecurity.repository.ShareSecurityRepo;
import com.sb.solutions.core.constant.AppConstant;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.enums.ShareType;
import com.sb.solutions.core.enums.Status;

@Service("shareSecurityService")
public class ShareSecurityServiceImpl implements ShareSecurityService {

    private static final Logger logger = LoggerFactory.getLogger(ShareSecurityService.class);
    private ShareSecurityRepo shareSecurityRepo;
    private final NepseMasterRepository nepseMasterRepository;
    private final NepseCompanyRepository nepseCompanyRepository;
    private final CustomerLoanRepository customerLoanRepository;

    public ShareSecurityServiceImpl(
        ShareSecurityRepo shareSecurityRepo,
        NepseMasterRepository nepseMasterRepository,
        NepseCompanyRepository nepseCompanyRepository,
        CustomerLoanRepository customerLoanRepository) {
        this.shareSecurityRepo = shareSecurityRepo;
        this.nepseMasterRepository = nepseMasterRepository;
        this.nepseCompanyRepository = nepseCompanyRepository;
        this.customerLoanRepository = customerLoanRepository;
    }

    @Override
    public List<ShareSecurity> findAll() {
        return shareSecurityRepo.findAll();
    }

    @Override
    public ShareSecurity findOne(Long id) {
        return shareSecurityRepo.getOne(id);
    }

    @Override
    public ShareSecurity save(ShareSecurity shareSecurity) {
        return shareSecurityRepo.save(shareSecurity);
    }

    @Override
    public Page<ShareSecurity> findAllPageable(Object t, Pageable pageable) {
        return shareSecurityRepo.findAll(pageable);
    }

    @Override
    public void execute(Optional<Long> optional) {
        final byte LIMIT_EXCEED_YES = 1;
        final byte LIMIT_EXCEED_NO = 0;
        final NepseMaster master = nepseMasterRepository.findByStatus(Status.ACTIVE);
        final Map<ShareType, Double> masterMap = new HashMap<ShareType, Double>() {{
            put(ShareType.ORDINARY, master.getOrdinary() / 100);
            put(ShareType.PROMOTER, master.getPromoter() / 100);
        }};
        final Map<String, Double> marketPriceMap = nepseCompanyRepository.findAll().stream()
            .collect(
                Collectors.toMap(NepseCompany::getCompanyCode, NepseCompany::getAmountPerUnit));
        final String MSG_LOG = "Insufficient Security considered value. Maximum considered amount is %s";
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
                    shareDataList.stream().forEach(customerShareData -> {

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
                    logger.info(" Recalculate amount {} ===  {} proposal limt",
                        reCalculateAmount.get(), customerLoan.getProposal().getProposedLimit());
                    byte limitExccedFlag = LIMIT_EXCEED_NO;
                    if (customerLoan.getProposal().getProposedLimit()
                        .compareTo(reCalculateAmount.get()) >= 1) {
                        limitExccedFlag = LIMIT_EXCEED_YES;
                    }
                    if (customerLoan.getLimitExceed() != limitExccedFlag) {
                        String remark = limitExccedFlag == LIMIT_EXCEED_YES
                            ? String.format(MSG_LOG, reCalculateAmount.get())
                            : null;
                        customerLoanRepository
                            .updateLimitExceed(limitExccedFlag, remark, customerLoan.getId());
                    }
                }
            });

    }

    @Override
    public List<ShareSecurity> saveAll(List<ShareSecurity> list) {
        return shareSecurityRepo.saveAll(list);
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
