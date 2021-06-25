package com.sb.solutions.api.loan.repository;

import com.sb.solutions.api.customer.entity.CustomerGeneralDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.solutions.api.loan.entity.CustomerDocument;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.xml.bind.ValidationEvent;

/**
 * @author yunish on 11/5/2019
 */
public interface CustomerDocumentRepository extends JpaRepository<CustomerDocument, Long> {

//    CustomerDocument findCustomerDocumentByDocumentId(Long docId)


}
