package com.sb.solutions.api.customerActivity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.solutions.api.customerActivity.entity.CustomerActivity;

/**
 * @author : Rujan Maharjan on  9/18/2020
 **/
public interface CustomerActivityRepository extends JpaRepository<CustomerActivity, Long> {

}
