package com.sb.solutions.api.customer.repository;

import java.util.Date;

import com.sb.solutions.api.customer.entity.CustomerInfo;
import com.sb.solutions.api.customer.enums.CustomerIdType;
import com.sb.solutions.api.customer.enums.CustomerType;
import com.sb.solutions.core.repository.BaseRepository;

/**
 * @author : Rujan Maharjan on  8/10/2020
 **/
public interface CustomerInfoRepository extends BaseRepository<CustomerInfo, Long> {

    CustomerInfo findByAssociateIdAndCustomerType(Long id, CustomerType customerType);

    CustomerInfo findByCustomerTypeAndIdNumberAndIdRegPlaceAndIdTypeAndIdRegDate(
        CustomerType customerType, String idNumber, String idRegPlace,
        CustomerIdType customerIdType, Date date);
}
