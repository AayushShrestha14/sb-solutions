package com.sb.solutions.api.preference.interestrate.repository;
import com.sb.solutions.api.preference.interestrate.entity.BaseInterestRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseInterestRepository extends JpaRepository<BaseInterestRate, Long> {
    BaseInterestRate findFirstByOrderByIdDesc();

    Page<BaseInterestRate> findAllByOrderByIdDesc(Pageable pageable);
}
