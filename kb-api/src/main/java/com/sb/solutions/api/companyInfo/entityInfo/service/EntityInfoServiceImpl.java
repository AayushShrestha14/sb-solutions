package com.sb.solutions.api.companyInfo.entityInfo.service;

import com.sb.solutions.api.companyInfo.entityInfo.entity.EntityInfo;
import com.sb.solutions.api.companyInfo.entityInfo.repository.EntityInfoRepository;
import com.sb.solutions.api.companyInfo.managementTeam.entity.ManagementTeam;
import com.sb.solutions.api.companyInfo.proprietor.entity.Proprietor;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class EntityInfoServiceImpl implements  EntityInfoService {
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
        entityInfo.setLastModified(date);
        entityInfo.getCapital().setLastModified(date);
        entityInfo.getLegalStatus().setLastModified(date);
        entityInfo.getSwot().setLastModified(date);
        if(entityInfo.getManagementTeamList().size()<=0){
            entityInfo.setManagementTeamList(null);
        }else {
            for (ManagementTeam managementTeam : entityInfo.getManagementTeamList()) {
                managementTeam.setLastModified(date);
            }
        }
        if(entityInfo.getProprietorsList().size()<=0){
            entityInfo.setProprietorsList(null);
        }else{
            for(Proprietor proprietor : entityInfo.getProprietorsList()){
                proprietor.setLastModified(date);
            }
        }
        return entityInfoRepository.save(entityInfo);
    }

    @Override
    public Page<EntityInfo> findAllPageable(Object t, Pageable pageable) {
        return null;
    }
}
