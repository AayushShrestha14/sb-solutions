package com.sb.solutions.api.Loan.entity;

import java.io.IOException;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sb.solutions.api.Loan.LoanStage;
import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.companyInfo.entityInfo.entity.EntityInfo;
import com.sb.solutions.api.customer.entity.Customer;
import com.sb.solutions.api.dms.dmsloanfile.entity.DmsLoanFile;
import com.sb.solutions.api.loanConfig.entity.LoanConfig;
import com.sb.solutions.core.enitity.BaseEntity;
import com.sb.solutions.core.enums.DocStatus;
import com.sb.solutions.core.enums.LoanType;
import com.sb.solutions.core.enums.Priority;

/**
 * @author Rujan Maharjan on 6/4/2019
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerLoan extends BaseEntity<Long> {

    @ManyToOne(cascade = {
        CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Customer customerInfo;

    @OneToOne
    private LoanConfig loan;

    @ManyToOne(cascade = {
        CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private EntityInfo entityInfo;

    private LoanType loanType;

    private DocStatus documentStatus = DocStatus.PENDING;

    @ManyToOne(cascade = {
        CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private DmsLoanFile dmsLoanFile;

    @OneToOne(cascade = CascadeType.ALL)
    private LoanStage currentStage;

    private Priority priority;

    @Transient
    private List previousList;

    @OneToOne
    private Branch branch;

    private String offerLetterUrl;

    @Lob
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String previousStageList;

    private Boolean isValidated = false;

    @Transient
    private List<LoanStage> distinctPreviousList;

    public List<LoanStage> getPreviousList() {
        if (this.getPreviousStageList() != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            try {
                this.previousList = objectMapper.readValue(this.getPreviousStageList(),
                    typeFactory.constructCollectionType(List.class, LoanStage.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.previousList = new ArrayList();
        }
        return this.previousList;
    }

    public List<LoanStage> getDistinctPreviousList() {
        Collection<LoanStage> list =
            CollectionUtils.isEmpty(this.getPreviousList()) || CollectionUtils
                .isEmpty(this.previousList) ? new ArrayList<>() : this.getPreviousList();
        return list.stream()
            .filter(distinctByKey(
                p -> p.getToUser() == null ? p.getToRole().getId() : p.getToUser().getId()))
            .collect(Collectors.toList());
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }


}
