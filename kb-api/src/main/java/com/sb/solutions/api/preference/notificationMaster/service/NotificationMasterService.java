package com.sb.solutions.api.preference.notificationMaster.service;

import com.sb.solutions.api.preference.notificationMaster.entity.NotificationMaster;
import com.sb.solutions.core.service.BaseService;

public interface NotificationMasterService extends BaseService<NotificationMaster> {

    int getValue(String notifKey);
}
