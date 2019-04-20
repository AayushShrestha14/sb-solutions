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
        String query = "select ua.api_url,ifnull(r.role_name,'SPADMIN') role_name  from url_api ua\n" +
                " left join role_permission_rights_api_rights apirights\n" +
                " on apirights.api_rights_id = ua.id\n" +
                "left join role_permission_rights rpr on rpr.id= apirights.role_permission_rights_id\n" +
                "left join role r on rpr.role_id = r.id;";

        List<Map<String,Object>> mapList = namedParameterJdbcTemplate.queryForList(query,map);
        return mapList;
    }


}
