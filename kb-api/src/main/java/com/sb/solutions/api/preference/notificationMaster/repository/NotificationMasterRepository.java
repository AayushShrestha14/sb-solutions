package com.sb.solutions.api.preference.notificationMaster.repository;

import com.sb.solutions.api.preference.notificationMaster.entity.NotificationMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationMasterRepository extends JpaRepository<NotificationMaster, Long> {

    Boolean existsByNotifKey(String notifKey);

    void removeByNotifKey(String notifKey);

    NotificationMaster getByNotifKey(String notifKey);
}
