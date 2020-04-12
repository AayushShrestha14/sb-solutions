package com.sb.solutions.api.preference.notificationMaster.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb.solutions.api.preference.notificationMaster.entity.NotificationMaster;
import com.sb.solutions.api.preference.notificationMaster.repository.NotificationMasterRepository;

@Service
public class NotificationMasterServiceImpl implements NotificationMasterService {

    private final NotificationMasterRepository notificationMasterRepository;

    public NotificationMasterServiceImpl(
        NotificationMasterRepository notificationMasterRepository) {
        this.notificationMasterRepository = notificationMasterRepository;
    }

    @Override
    public List<NotificationMaster> findAll() {
        return notificationMasterRepository.findAll();
    }

    @Override
    public NotificationMaster findOne(Long id) {
        return null;
    }

    @Override
    @Transactional
    public NotificationMaster save(NotificationMaster notificationMaster) {

        final String notifKey = notificationMaster.getNotifKey();
        /* remove if the key already exits */
        if (notificationMasterRepository.existsByNotifKey(notifKey)) {
            notificationMasterRepository.removeByNotifKey(notifKey);
        }
        return notificationMasterRepository.save(notificationMaster);
    }

    @Override
    public Page<NotificationMaster> findAllPageable(Object t, Pageable pageable) {
        return null;
    }

    @Override
    public List<NotificationMaster> saveAll(List<NotificationMaster> list) {
        return notificationMasterRepository.saveAll(list);
    }

    @Override
    public int getValue(String notifyKey) {
        return notificationMasterRepository.getByNotifKey(notifyKey).getValue();
    }
}
