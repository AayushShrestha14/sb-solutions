package com.sb.solutions.api.Loan.service;

import com.sb.solutions.api.Loan.entity.Loan;
import com.sb.solutions.api.Loan.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Rujan Maharjan on 6/4/2019
 */

@Service
public class LonaServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Override
    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    @Override
    public Loan findOne(Long id) {
        return loanRepository.findById(id).get();
    }

    @Override
    public Loan save(Loan loan) {
        return loanRepository.save(loan);
    }

    @Override
    public Page<Loan> findAllPageable(Object t, Pageable pageable) {
        return null;
    }

    @Override
    public void sendForwardBackwardLoan(Loan loan) {
        loanRepository.save(loan);
    }
}
