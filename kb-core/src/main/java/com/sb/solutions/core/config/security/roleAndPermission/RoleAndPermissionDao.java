package com.sb.solutions.core.config.security.roleAndPermission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rujan Maharjan on 4/19/2019
 */

@Repository
public class RoleAndPermissionDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    DataSource dataSource;

    public List<Map<String,Object>> getRole(){
        Map<String,Object> map = new HashMap<>();
        String query = "select r.role_name,p.permission_name,urlapi.api_url from role r\n" +
                "  join role_permission_rights rpr on r.id=rpr.role_id\n" +
                "  join permission p on p.id=rpr.permission_id\n" +
                "join permission_api_list apl on apl.permission_id=p.id\n" +
                "join url_api urlapi on urlapi.id = apl.api_list_id";

        List<Map<String,Object>> mapList = namedParameterJdbcTemplate.queryForList(query,map);
        return mapList;
    }


}
