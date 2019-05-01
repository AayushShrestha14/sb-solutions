package com.sb.solutions.api.companyInfo.entityInfo.repository;

import com.sb.solutions.api.companyInfo.entityInfo.entity.EntityInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityInfoRepository extends JpaRepository<EntityInfo, Long> {
}
