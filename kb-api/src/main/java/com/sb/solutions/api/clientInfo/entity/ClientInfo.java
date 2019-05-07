package com.sb.solutions.api.clientInfo.entity;

import com.sb.solutions.api.clientDocument.entity.ClientDocument;
import com.sb.solutions.api.clientInfo.enums.Securities;
import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client_info")
public class ClientInfo extends BaseEntity<Long> {
    @NotNull(message = "name should not be null")
    private String name;
    private String citizenshipNumber;
    private String contactNumber;
    private double interestRate;
    private String security;
    @Transient
    private Set<Securities> securities;
    @OneToMany(mappedBy = "clientInfo", cascade = CascadeType.ALL)
    private List<ClientDocument> documents;
}
