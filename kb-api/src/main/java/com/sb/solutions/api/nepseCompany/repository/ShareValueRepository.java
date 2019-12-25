package com.sb.solutions.api.nepseCompany.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sb.solutions.api.nepseCompany.entity.ShareValue;

public interface ShareValueRepository extends JpaRepository<ShareValue, Long> {

    ShareValue findFirstByOrderByIdDesc();

    List<ShareValue> findAllByOrderByIdDesc();

    @Query(value = "select s from ShareValue s order by s.id desc")
    Page<ShareValue> getALlPageable(Pageable pageable);

}
