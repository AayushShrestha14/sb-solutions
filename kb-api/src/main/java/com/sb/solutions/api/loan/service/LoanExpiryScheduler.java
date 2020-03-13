package com.sb.solutions.api.loan.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Aashish Shrestha, 12/Mar/2020
 */

@Configuration
public class LoanExpiryScheduler {

    private CustomerLoanService customerLoanService;

    public LoanExpiryScheduler(
            CustomerLoanService customerLoanService) {
        this.customerLoanService = customerLoanService;
    }

    @Scheduled(cron = "0 0 23 * * ?") // every day at 11pm
    public void runScheduler() {
        System.out.println("Scheduler running !!");
        try {
            this.customerLoanService.runScheduler();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}

