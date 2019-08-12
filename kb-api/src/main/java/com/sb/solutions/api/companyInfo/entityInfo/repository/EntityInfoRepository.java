package com.sb.solutions.api.companyInfo.entityInfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.solutions.api.companyInfo.entityInfo.entity.EntityInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EntityInfoRepository extends JpaRepository<EntityInfo, Long>, JpaSpecificationExecutor<EntityInfo> {

    EntityInfo findEntityInfoByRegistrationNumber(String registrationNumber);

}
