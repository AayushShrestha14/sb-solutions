package com.sb.solutions.api.basehttp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by bishallama on 4/26/2018.
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "base_http")
public class BaseHttp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "base_http_id")
    private int baseHttpId;
    @Column(name = "base_url")
    private String baseUrl;
    private int flag;

}
