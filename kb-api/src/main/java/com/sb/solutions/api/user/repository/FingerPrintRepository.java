package com.sb.solutions.api.user.repository;

import com.sb.solutions.api.user.entity.FingerPrint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface FingerPrintRepository extends JpaRepository<FingerPrint,Long> {
    @Query(value = "select f.path from FingerPrint f")
    Set<String> getAllPath();
    FingerPrint findByPath(String path);
}
