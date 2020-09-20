package com.sb.solutions.api.customer.repository;

import org.springframework.stereotype.Repository;

import com.sb.solutions.api.customer.entity.CustomerActivityLog;
import com.sb.solutions.core.repository.BaseRepository;

/**
 * @author Elvin Shrestha on 9/19/2020
 */
@Repository
public interface CustomerActivityLogRepository extends BaseRepository<CustomerActivityLog, Long> {

}
