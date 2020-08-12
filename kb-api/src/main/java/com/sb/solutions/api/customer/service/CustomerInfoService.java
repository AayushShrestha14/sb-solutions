package com.sb.solutions.api.customer.service;

import com.sb.solutions.api.customer.entity.CustomerInfo;
import com.sb.solutions.core.service.Service;

/**
 * @author : Rujan Maharjan on  8/10/2020
 **/
public interface CustomerInfoService extends Service<CustomerInfo, Long> {

    CustomerInfo saveObject(Object o);

    CustomerInfo findByAssociateId(Long id);

}
