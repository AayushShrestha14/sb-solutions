package com.sb.solutions.api.loan.dto;

import com.sb.solutions.api.customer.enums.CustomerIdType;
import com.sb.solutions.api.customer.enums.CustomerType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CustomerListDto {

    private Long id;
    private String name;
    private String idNumber;
    private CustomerType customerType;
    private String contactNo;
    private String email;
    private String idRegPlace;
    private Date idRegDate;
    private Date createdAt;
    private Long associateId;
    public CustomerListDto(Long id, String name, String idNumber,
                           CustomerType customerType, String contactNo,
                           String email, String idRegPlace, Date idRegDate,
                           Date createdAt, Long associateId) {
        this.id = id;
        this.name = name;
        this.idNumber = idNumber;
        this.customerType = customerType;
        this.contactNo = contactNo;
        this.email = email;
        this.idRegPlace = idRegPlace;
        this.idRegDate = idRegDate;
        this.createdAt = createdAt;
        this.associateId = associateId;
    }
}
