package com.sb.solutions.api.clientDocument.entity;

import com.sb.solutions.api.clientInfo.entity.ClientInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client_document")
public class ClientDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String url;
    @ManyToOne
    @JoinColumn(name ="client_info_id")
    private ClientInfo clientInfo;
}
