package com.sb.solutions.api.companyInfo.entityInfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.solutions.api.companyInfo.entityInfo.entity.EntityInfo;

public interface EntityInfoRepository extends JpaRepository<EntityInfo, Long> {

}
