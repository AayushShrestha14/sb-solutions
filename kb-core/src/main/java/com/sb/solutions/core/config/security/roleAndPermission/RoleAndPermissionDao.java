package com.sb.solutions.core.config.security.roleAndPermission;

import com.sb.solutions.core.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rujan Maharjan on 4/19/2019
 */

@Component
public class RoleAndPermissionDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    DataSource dataSource;

    private String username = "_blank";

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

    public boolean checkApiPermission(String api) {

        Map<String, Object> map = new HashMap<>();
        map.put("api", api);
        String query = "select api_url from url_api where api_url=:api";
        List<Map<String, Object>> mapApi = namedParameterJdbcTemplate.queryForList(query, map);
        if (mapApi.isEmpty()) {
            return true;
        }
        if(this.getCurrentUserRole().equalsIgnoreCase("admin")){
            return true;
        }
        List<Map<String, Object>> mapApiChk = this.chkPermissionInRole(api);
        if (!mapApiChk.isEmpty()) {
            return true;
        }

        return false;
    }

    public List<Map<String, Object>> chkPermissionInRole(String api) {
        String role = this.getCurrentUserRole();
        Map<String, Object> map = new HashMap<>();
        map.put("role", role);
        map.put("api", api);
        String query = "select ua.api_url from url_api ua\n" +
                "                 left join role_permission_rights_api_rights apirights\n" +
                "                 on apirights.api_rights_id = ua.id\n" +
                "                left join role_permission_rights rpr on rpr.id= apirights.role_permission_rights_id\n" +
                "                left join role r on rpr.role_id = r.id\n" +
                "where r.role_name=:role and ua.api_url =:api\n" +
                "group by ua.id;";

        List<Map<String, Object>> mapList = namedParameterJdbcTemplate.queryForList(query, map);
        return mapList;
    }

    public String getCurrentUserRole() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User userDetail = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            username = userDetail.getUsername();
        } else {
            throw new ApiException("Invalid Token");
        }
        Map<String, Object> map = new HashMap<>();
        String query = "SELECT r.role_name from user u join role r on r.id = u.role_id where user_name = :username";
        map.put("username", username);
        String roleName = namedParameterJdbcTemplate.queryForObject(query, map, String.class);
        return roleName;
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
