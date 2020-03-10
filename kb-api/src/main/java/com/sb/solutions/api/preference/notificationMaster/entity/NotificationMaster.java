package com.sb.solutions.api.preference.notificationMaster.entity;

import com.sb.solutions.core.enitity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * @author Aashish shrestha on 10th. March, 2020
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NotificationMaster extends BaseEntity<Long> {

    private String notifKey;
    private int value;
}
