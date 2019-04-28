package com.sb.solutions.core.config.security.roleAndPermission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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

    public List<Map<String, Object>> getRole() {
        Map<String, Object> map = new HashMap<>();
        String query = "select ua.api_url,group_concat(DISTINCT ifnull(r.role_name,'admin')) role_name from url_api ua\n" +
                " left join role_permission_rights_api_rights apirights\n" +
                " on apirights.api_rights_id = ua.id\n" +
                "left join role_permission_rights rpr on rpr.id= apirights.role_permission_rights_id\n" +
                "left join role r on rpr.role_id = r.id group by ua.id;";

        List<Map<String, Object>> mapList = namedParameterJdbcTemplate.queryForList(query, map);
        return mapList;
    }

    public Long getCurrentUserId(String username) {
        Map<String, Object> map = new HashMap<>();
        String query = "SELECT id from user where user_name = :username";
        map.put("username", username);
        Long id = namedParameterJdbcTemplate.queryForObject(query, map, Long.class);
        return id;
    }

    public void restrictUrl(HttpSecurity http) throws Exception {
        System.out.println("refreshed");
        List<Map<String, Object>> mapList = this.getRole();
        for (Map<String, Object> map : mapList) {
            if (map.get("api_url") != null)
                http.authorizeRequests().
                        antMatchers(map.get("api_url").toString()).hasAnyAuthority(map.get("role_name").toString());
        }
    }

}
