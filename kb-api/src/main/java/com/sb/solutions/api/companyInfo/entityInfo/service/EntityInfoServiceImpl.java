package com.sb.solutions.api.companyInfo.entityInfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.companyInfo.entityInfo.entity.EntityInfo;
import com.sb.solutions.api.companyInfo.entityInfo.repository.EntityInfoRepository;


@Service
public class EntityInfoServiceImpl implements EntityInfoService {

    private final EntityInfoRepository entityInfoRepository;

    public EntityInfoServiceImpl(@Autowired EntityInfoRepository entityInfoRepository) {
        this.entityInfoRepository = entityInfoRepository;
    }

    @Override
    public List<EntityInfo> findAll() {
        return entityInfoRepository.findAll();
    }

    @Override
    public EntityInfo findOne(Long id) {
        return entityInfoRepository.getOne(id);
    }

    @Override
    public EntityInfo save(EntityInfo entityInfo) {
        return entityInfoRepository.save(entityInfo);
    }

    @Override
    public Page<EntityInfo> findAllPageable(Object t, Pageable pageable) {
        return null;
    }
}
