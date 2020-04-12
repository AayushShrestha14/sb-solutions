package com.sb.solutions.api.preference.notificationMaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.solutions.api.preference.notificationMaster.entity.NotificationMaster;

public interface NotificationMasterRepository extends JpaRepository<NotificationMaster, Long> {

    Boolean existsByNotifKey(String notifKey);

    void removeByNotifKey(String notifKey);

    NotificationMaster getByNotifKey(String notifKey);
}
