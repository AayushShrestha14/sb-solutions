package com.sb.solutions.api.companyInfo.entityInfo.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.companyInfo.entityInfo.entity.EntityInfo;
import com.sb.solutions.api.companyInfo.entityInfo.repository.EntityInfoRepository;
import com.sb.solutions.api.companyInfo.managementTeam.entity.ManagementTeam;
import com.sb.solutions.api.companyInfo.proprietor.entity.Proprietor;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EntityInfoServiceImpl implements EntityInfoService {

    private final EntityInfoRepository entityInfoRepository;

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
        Date date = new Date();
        entityInfo.setLastModifiedAt(date);
        entityInfo.getCapital().setLastModifiedAt(date);
        entityInfo.getLegalStatus().setLastModifiedAt(date);
        entityInfo.getSwot().setLastModifiedAt(date);
        if (entityInfo.getManagementTeamList().size() <= 0) {
            entityInfo.setManagementTeamList(null);
        } else {
            for (ManagementTeam managementTeam : entityInfo.getManagementTeamList()) {
                managementTeam.setLastModifiedAt(date);
            }
        }
        if (entityInfo.getProprietorsList().size() <= 0) {
            entityInfo.setProprietorsList(null);
        } else {
            for (Proprietor proprietor : entityInfo.getProprietorsList()) {
                proprietor.setLastModifiedAt(date);
            }
        }
        return entityInfoRepository.save(entityInfo);
    }

    @Override
    public Page<EntityInfo> findAllPageable(Object t, Pageable pageable) {
        return null;
    }
}
