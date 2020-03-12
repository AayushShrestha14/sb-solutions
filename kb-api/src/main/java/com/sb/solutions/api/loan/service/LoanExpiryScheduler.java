package com.sb.solutions.api.loan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableScheduling
public class LoanExpiryScheduler {

    private CustomerLoanService customerLoanService;

    public LoanExpiryScheduler(
            CustomerLoanService customerLoanService) {
        this.customerLoanService = customerLoanService;
    }

    @Scheduled(cron = "*/10 * * * * *") // every 10s
    public void runScheduler() {
        try {
            this.customerLoanService.runScheduler();
        } catch (Exception e) {
            System.out.println("Exception " + e);
        }
    }
}

