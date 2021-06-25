package com.sb.solutions.api.loan.service;

import java.sql.Array;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;
import com.sb.solutions.api.loan.entity.CustomerLoan;
import com.sb.solutions.api.loan.repository.CustomerLoanRepository;
import com.sb.solutions.core.utils.file.DeleteFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.loan.entity.CustomerDocument;
import com.sb.solutions.api.loan.repository.CustomerDocumentRepository;

/**
 * @author yunish on 11/5/2019
 */
@Service
public class CustomerDocumentServiceImpl implements CustomerDocumentService {

    private final CustomerDocumentRepository customerDocumentRepository;
    private final CustomerLoanService customerLoanService;
    private final CustomerLoanRepository customerLoanRepository;
    private Gson gson;

    public CustomerDocumentServiceImpl(
            @Autowired CustomerDocumentRepository customerDocumentRepository, CustomerLoanService customerLoanService, CustomerLoanRepository customerLoanRepository) {
        this.customerDocumentRepository = customerDocumentRepository;
        this.customerLoanService = customerLoanService;
        this.customerLoanRepository = customerLoanRepository;
    }

    @Override
    public List<CustomerDocument> findAll() {
        return customerDocumentRepository.findAll();
    }

    @Override
    public CustomerDocument findOne(Long id) {
        return customerDocumentRepository.getOne(id);
    }

    @Override
    public CustomerDocument save(CustomerDocument customerDocument) {
        return customerDocumentRepository.save(customerDocument);
    }

    @Override
    public Page<CustomerDocument> findAllPageable(Object t, Pageable pageable) {
        return null;
    }

    @Override
    public List<CustomerDocument> saveAll(List<CustomerDocument> list) {
        return null;
    }

    @Override
    public String deleteById(Long customerDocId, String path, Long actualLoanId) {
//        Optional<CustomerDocument> optionalCustomerDocument = customerDocumentRepository.findById(actualLoanId);

//        CustomerDocument docId=customerDocumentRepository.findById(customerDocId).get();
        if ((null != actualLoanId) && (null != actualLoanId)) {
              customerLoanRepository.deleteFromChildTable(customerDocId);


                customerDocumentRepository.deleteById(customerDocId);

        }
//            Optional<CustomerDocument> optionalCustomerDocument = customerLoan.getCustomerDocument().stream().filter( d -> d.getId() == customerDocId).findAny();
////            for (CustomerDocument doc : customerLoan.getCustomerDocument()) {
////                doc.re
////            }
//            if (!optionalCustomerDocument.isPresent()) {
//                List<CustomerDocument> newCustomerDocuments = customerLoan.getCustomerDocument();
//                optionalCustomerDocument.get();
////                newCustomerDocuments.retainAll();
//                tempCustomerDoc = newCustomerDocuments;
//            }
////            customerLoan.setCustomerDocument(tempCustomerDoc);
//            customerDocumentRepository.deleteById(customerDocId);
//        }

        DeleteFileUtils.deleteFile(path);
        return "SUCCESSFULLY DELETED";
    }
}
