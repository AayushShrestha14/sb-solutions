package com.sb.solutions.api.preference.notificationMaster.entity;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.sb.solutions.core.enitity.BaseEntity;

/**
 * @author Aashish shrestha on 10th. March, 2020
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NotificationMaster extends BaseEntity<Long> {

    private String notificationKey;
    private int value;
}
