package com.sb.solutions.web.navigation.mapper;

import com.sb.solutions.api.rolePermissionRight.entity.RolePermissionRights;
import com.sb.solutions.api.rolePermissionRight.entity.SubNav;
import com.sb.solutions.web.navigation.dto.MenuDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rujan Maharjan on 5/17/2019
 */
@Component
public class MenuMapper {


    public List<MenuDto> menuDtoList(List<RolePermissionRights> rolePermissionRightsList){
        List<MenuDto> menuDtoList = new ArrayList<>();
        for (RolePermissionRights rolePermissionRights : rolePermissionRightsList) {
            MenuDto m  = new MenuDto();
            m.setTitle(rolePermissionRights.getPermission().getPermissionName());
            m.setLink(rolePermissionRights.getPermission().getFrontUrl());
            m.setIcon(rolePermissionRights.getPermission().getFaIcon());
            List<MenuDto>  subNavList = new ArrayList<>();
           for(SubNav subNav1 :rolePermissionRights.getPermission().getSubNavs()){
                MenuDto subNav  = new MenuDto();
                subNav.setTitle(subNav1.getSubNavName());
                subNav.setLink(subNav1.getFrontUrl());
                subNav.setIcon(subNav1.getFaIcon());
                subNavList.add(subNav);
            }
            m.setChildren(subNavList);
            menuDtoList.add(m);
        }

        return menuDtoList;
    }
}
