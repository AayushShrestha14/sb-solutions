package com.sb.solutions.api.preference.notificationMaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.solutions.api.preference.notificationMaster.entity.NotificationMaster;

public interface NotificationMasterRepository extends JpaRepository<NotificationMaster, Long> {

    Boolean existsByNotificationKey(String notificationKey);

    void removeByNotificationKey(String notificationKey);

    NotificationMaster getByNotificationKey(String notificationKey);
}
