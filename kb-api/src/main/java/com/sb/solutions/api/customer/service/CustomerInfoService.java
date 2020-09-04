package com.sb.solutions.api.customer.service;

import java.util.Date;

import com.sb.solutions.api.customer.entity.CustomerInfo;
import com.sb.solutions.api.customer.enums.CustomerIdType;
import com.sb.solutions.api.customer.enums.CustomerType;
import com.sb.solutions.core.service.Service;

/**
 * @author : Rujan Maharjan on  8/10/2020
 **/
public interface CustomerInfoService extends Service<CustomerInfo, Long> {

    CustomerInfo saveObject(Object o);

    CustomerInfo saveLoanInformation(Object o, Long customerInfoId, String template);

    CustomerInfo findByAssociateIdAndCustomerType(Long id, CustomerType customerType);

    CustomerInfo findByCustomerTypeAndIdNumberAndIdRegPlaceAndIdTypeAndIdRegDate(
        CustomerType customerType, String idNumber, String idRegPlace,
        CustomerIdType customerIdType, Date date);

}
