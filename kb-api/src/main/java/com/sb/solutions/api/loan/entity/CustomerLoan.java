package com.sb.solutions.api.loan.entity;

import static com.sb.solutions.core.constant.AppConstant.DATE_FORMAT;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.sb.solutions.api.approvallimit.emuns.LoanApprovalType;
import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.companyInfo.model.entity.CompanyInfo;
import com.sb.solutions.api.creditRiskGrading.entity.CreditRiskGrading;
import com.sb.solutions.api.customer.entity.Customer;
import com.sb.solutions.api.dms.dmsloanfile.entity.DmsLoanFile;
import com.sb.solutions.api.financial.entity.Financial;
import com.sb.solutions.api.group.entity.Group;
import com.sb.solutions.api.loan.LoanStage;
import com.sb.solutions.api.loan.dto.CustomerOfferLetterDto;
import com.sb.solutions.api.loan.dto.LoanStageDto;
import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.api.proposal.entity.Proposal;
import com.sb.solutions.api.security.entity.Security;
import com.sb.solutions.api.siteVisit.entity.SiteVisit;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.enums.LoanType;
import com.sb.solutions.core.enums.Priority;

/**
 * @author Rujan Maharjan on 6/4/2019
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners({AuditingEntityListener.class})
@Audited
public class CustomerLoan extends BaseEntity<Long> {

    @Audited
    @ManyToOne
    private Customer customerInfo;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @OneToOne
    private LoanConfig loan;

    @Audited
    @ManyToOne
    private CompanyInfo companyInfo;

    private LoanType loanType = LoanType.NEW_LOAN;

    private DocStatus documentStatus = DocStatus.DISCUSSION;

    private LoanApprovalType loanCategory;

    @AuditJoinTable(name = "customer_document_Path_customer_Loan_audit")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "customer_document_Path_customer_Loan")
    private List<CustomerDocument> customerDocument;

    @NotAudited
    @ManyToOne
    private DmsLoanFile dmsLoanFile;

    @Audited
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "site_visit_id")
    private SiteVisit siteVisit;

    @Audited
    @OneToOne(cascade = CascadeType.ALL)
    private LoanStage currentStage;

    @NotAudited
    private Priority priority;

    @NotAudited
    private Long parentId;

    @NotAudited
    private Long childId;

    @NotAudited
    private Boolean isCloseRenew;

    @Transient
    private List previousList;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @OneToOne
    private Branch branch;

    @NotAudited
    private Boolean notify;

    @NotAudited
    private Long notedBy;

    private String offerLetterUrl;

    @Audited
    @OneToOne
    private Proposal proposal;

    @Audited
    @OneToOne
    private Financial financial;

    @Audited
    @OneToOne
    private Security security;

    @NotAudited
    @OneToOne
    private Group group;

    @Lob
    private String previousStageList;

    @NotAudited
    private Boolean isValidated = false;

    @Transient
    private Boolean pulled = false;

    @Transient
    private CustomerOfferLetterDto customerOfferLetter;

    @Transient
    private List<LoanStageDto> distinctPreviousList;

    @Transient
    private int offerLetterStat = 0;

    @Transient
    private int uploadedOfferLetterStat = 0;

    @Audited
    @OneToOne
    private CreditRiskGrading creditRiskGrading;

    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public List<LoanStageDto> getPreviousList() {
        if (this.getPreviousStageList() != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            try {
                this.previousList = objectMapper.readValue(this.getPreviousStageList(),
                    typeFactory.constructCollectionType(List.class, LoanStageDto.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.previousList = new ArrayList();
        }
        return this.previousList;
    }

    public List<LoanStageDto> getDistinctPreviousList() {
        Collection<LoanStageDto> list =
            CollectionUtils.isEmpty(this.getPreviousList()) || CollectionUtils
                .isEmpty(this.previousList) ? new ArrayList<>() : this.getPreviousList();
        return list.stream()
            .filter(distinctByKey(
                p -> (p.getToUser() == null ? p.getToRole().getId() : p.getToUser().getId())))
            .filter(p -> !p.getToUser().getIsDefaultCommittee())
            .collect(Collectors.toList());
    }

    public Customer getCustomerInfo() {
        return this.customerInfo;
    }

    public LoanConfig getLoan() {
        return this.loan;
    }

    public CompanyInfo getCompanyInfo() {
        return this.companyInfo;
    }

    public LoanType getLoanType() {
        return this.loanType;
    }

    public DocStatus getDocumentStatus() {
        return this.documentStatus;
    }

    public LoanApprovalType getLoanCategory() {
        return this.loanCategory;
    }

    public List<CustomerDocument> getCustomerDocument() {
        return this.customerDocument;
    }

    public DmsLoanFile getDmsLoanFile() {
        return this.dmsLoanFile;
    }

    public SiteVisit getSiteVisit() {
        return this.siteVisit;
    }

    public LoanStage getCurrentStage() {
        return this.currentStage;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public Long getChildId() {
        return this.childId;
    }

    public Boolean getIsCloseRenew() {
        return this.isCloseRenew;
    }

    public Branch getBranch() {
        return this.branch;
    }

    public Boolean getNotify() {
        return this.notify;
    }

    public Long getNotedBy() {
        return this.notedBy;
    }

    public String getOfferLetterUrl() {
        return this.offerLetterUrl;
    }

    public Proposal getProposal() {
        return this.proposal;
    }

    public Financial getFinancial() {
        return this.financial;
    }

    public Security getSecurity() {
        return this.security;
    }

    public Group getGroup() {
        return this.group;
    }

    public String getPreviousStageList() {
        return this.previousStageList;
    }

    public Boolean getIsValidated() {
        return this.isValidated;
    }

    public Boolean getPulled() {
        return this.pulled;
    }

    public CustomerOfferLetterDto getCustomerOfferLetter() {
        return this.customerOfferLetter;
    }

    public int getOfferLetterStat() {
        return this.offerLetterStat;
    }

    public int getUploadedOfferLetterStat() {
        return this.uploadedOfferLetterStat;
    }

    public CreditRiskGrading getCreditRiskGrading() {
        return this.creditRiskGrading;
    }

    public void setCustomerInfo(Customer customerInfo) {
        this.customerInfo = customerInfo;
    }

    public void setLoan(LoanConfig loan) {
        this.loan = loan;
    }

    public void setCompanyInfo(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public void setDocumentStatus(DocStatus documentStatus) {
        this.documentStatus = documentStatus;
    }

    public void setLoanCategory(LoanApprovalType loanCategory) {
        this.loanCategory = loanCategory;
    }

    public void setCustomerDocument(List<CustomerDocument> customerDocument) {
        this.customerDocument = customerDocument;
    }

    public void setDmsLoanFile(DmsLoanFile dmsLoanFile) {
        this.dmsLoanFile = dmsLoanFile;
    }

    public void setSiteVisit(SiteVisit siteVisit) {
        this.siteVisit = siteVisit;
    }

    public void setCurrentStage(LoanStage currentStage) {
        this.currentStage = currentStage;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    public void setIsCloseRenew(Boolean isCloseRenew) {
        this.isCloseRenew = isCloseRenew;
    }

    public void setPreviousList(List previousList) {
        this.previousList = previousList;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public void setNotify(Boolean notify) {
        this.notify = notify;
    }

    public void setNotedBy(Long notedBy) {
        this.notedBy = notedBy;
    }

    public void setOfferLetterUrl(String offerLetterUrl) {
        this.offerLetterUrl = offerLetterUrl;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }

    public void setFinancial(Financial financial) {
        this.financial = financial;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setPreviousStageList(String previousStageList) {
        this.previousStageList = previousStageList;
    }

    public void setIsValidated(Boolean isValidated) {
        this.isValidated = isValidated;
    }

    public void setPulled(Boolean pulled) {
        this.pulled = pulled;
    }

    public void setCustomerOfferLetter(CustomerOfferLetterDto customerOfferLetter) {
        this.customerOfferLetter = customerOfferLetter;
    }

    public void setDistinctPreviousList(List<LoanStageDto> distinctPreviousList) {
        this.distinctPreviousList = distinctPreviousList;
    }

    public void setOfferLetterStat(int offerLetterStat) {
        this.offerLetterStat = offerLetterStat;
    }

    public void setUploadedOfferLetterStat(int uploadedOfferLetterStat) {
        this.uploadedOfferLetterStat = uploadedOfferLetterStat;
    }

    public void setCreditRiskGrading(CreditRiskGrading creditRiskGrading) {
        this.creditRiskGrading = creditRiskGrading;
    }

    public String toString() {
        return "CustomerLoan(customerInfo=" + this.getCustomerInfo() + ", loan=" + this.getLoan()
            + ", companyInfo=" + this.getCompanyInfo() + ", loanType=" + this.getLoanType()
            + ", documentStatus=" + this.getDocumentStatus() + ", loanCategory=" + this
            .getLoanCategory() + ", customerDocument=" + this.getCustomerDocument()
            + ", dmsLoanFile=" + this.getDmsLoanFile() + ", siteVisit=" + this.getSiteVisit()
            + ", currentStage=" + this.getCurrentStage() + ", priority=" + this.getPriority()
            + ", parentId=" + this.getParentId() + ", childId=" + this.getChildId()
            + ", isCloseRenew=" + this.getIsCloseRenew() + ", previousList=" + this
            .getPreviousList() + ", branch=" + this.getBranch() + ", notify=" + this.getNotify()
            + ", notedBy=" + this.getNotedBy() + ", offerLetterUrl=" + this.getOfferLetterUrl()
            + ", proposal=" + this.getProposal() + ", financial=" + this.getFinancial()
            + ", security=" + this.getSecurity() + ", group=" + this.getGroup()
            + ", previousStageList=" + this.getPreviousStageList() + ", isValidated=" + this
            .getIsValidated() + ", pulled=" + this.getPulled() + ", customerOfferLetter=" + this
            .getCustomerOfferLetter() + ", distinctPreviousList=" + this.getDistinctPreviousList()
            + ", offerLetterStat=" + this.getOfferLetterStat() + ", uploadedOfferLetterStat=" + this
            .getUploadedOfferLetterStat() + ", creditRiskGrading=" + this.getCreditRiskGrading()
            + ")";
    }
}
