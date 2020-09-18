package com.sb.solutions.api.customerActivity.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import com.sb.solutions.api.customerActivity.aop.Activity;
import com.sb.solutions.api.user.entity.User;

/**
 * @author : Rujan Maharjan on  9/18/2020
 **/
@Entity
@Data
public class CustomerActivity {

    private Long id;

    @Column(name = "profile", nullable = false, updatable = false)
    private String profile;

    @Column(name = "data", updatable = false)
    private String data;

    @CreatedBy
    @OneToOne
    private User modifiedBy;


    @Column(name = "activity", nullable = false, updatable = false)
    private Activity activity;

    @CreatedDate
    @Column(name = "modified_on", nullable = false, updatable = false)
    private Date modifiedOn;

}
