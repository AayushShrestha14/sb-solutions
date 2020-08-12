package com.sb.solutions.api.customer.repository;

import com.sb.solutions.api.customer.entity.CustomerInfo;
import com.sb.solutions.core.repository.BaseRepository;

/**
 * @author : Rujan Maharjan on  8/10/2020
 **/
public interface CustomerInfoRepository extends BaseRepository<CustomerInfo, Long> {

    CustomerInfo findByAssociateId(Long id);
}
