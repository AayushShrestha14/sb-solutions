package com.sb.solutions.api.loanflag.entity;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.core.enums.LoanFlag;

/**
 * @author Elvin Shrestha on 4/28/2020
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerLoanFlag extends AbstractPersistable<Long> {

    private LoanFlag flag;

    private String description;

    @Column(name = "flag_order")
    private int order;

    private Boolean notifiedByEmail;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private CustomerLoan customerLoan;

    public static <T> Collector<T, ?, T> toSingleton() {
        return Collectors.collectingAndThen(
            Collectors.toList(),
            list -> {
                if (list == null || list.isEmpty()) {
                    return null;
                }
                if (list.size() > 1) {
                    throw new IllegalStateException("Cannot have same flag more than once");
                }
                return list.get(0);
            }
        );
    }
}