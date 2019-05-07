package com.sb.solutions.api.clientInfo.repository;

import com.sb.solutions.api.clientInfo.entity.ClientInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientInfoRepository extends JpaRepository <ClientInfo, Long> {

    @Query(value = "select c from ClientInfo c where c.name like concat(:name,'%')")
    Page<ClientInfo> clientInfoFilter(@Param("name")String name, Pageable pageable);
}
