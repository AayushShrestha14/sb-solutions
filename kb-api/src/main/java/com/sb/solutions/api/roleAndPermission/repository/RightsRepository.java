package com.sb.solutions.api.roleAndPermission.repository;

import com.sb.solutions.api.roleAndPermission.entity.Rights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Rujan Maharjan on 3/18/2019.
 */

public interface RightsRepository extends JpaRepository<Rights, Long> {

    @Query(value = "select * from rights r join role_permission_rights rpr on r.id = rpr.right_id where rpr.role_id=:rid and rpr.permission_id=:pid",nativeQuery = true)
    List<Rights> getRightByPermissionAndRole(@Param("rid")Long rid,@Param("pid")Long pid);

}

