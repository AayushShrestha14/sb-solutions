package com.sb.solutions.api.insurance.repository;

import org.springframework.stereotype.Repository;

import com.sb.solutions.api.insurance.entity.InsuranceHistory;
import com.sb.solutions.core.repository.BaseRepository;

/**
 * @author Elvin Shrestha on 4/19/2020
 */
@Repository
public interface InsuranceHistoryRepository extends BaseRepository<InsuranceHistory, Long> {

}
