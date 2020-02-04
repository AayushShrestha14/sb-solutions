package com.sb.solutions.api.preference.interestrate.repository;
import com.sb.solutions.api.preference.interestrate.entity.BaseInterestRate;
import com.sb.solutions.core.enums.Status;

import org.codehaus.jackson.map.Serializers.Base;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseInterestRepository extends JpaRepository<BaseInterestRate, Long> {
    BaseInterestRate findFirstByOrderByIdDesc();

    Page<BaseInterestRate> findAllByOrderByIdDesc(Pageable pageable);

    BaseInterestRate findAllByStatus(Status staus);
}
