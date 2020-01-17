package com.sb.solutions.api.nepseCompany.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.solutions.api.nepseCompany.entity.ShareValue;
import com.sb.solutions.core.enums.Status;

public interface ShareValueRepository extends JpaRepository<ShareValue, Long> {

    ShareValue findFirstByOrderByIdDesc();

    Page<ShareValue> findAllByOrderByIdDesc(Pageable pageble);

    List<ShareValue> findAllByStatus(Status status);
}
