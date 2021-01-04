package com.sb.solutions.web.navigation.mapper;

import java.util.ArrayList;
import java.util.List;

import com.sb.solutions.core.enums.RoleType;
import com.sb.solutions.web.navigation.dto.MenuDto;

/**
 * @author : Rujan Maharjan on  1/4/2021
 **/
public class MenuCreatorCAD {


    public MenuDto cadNav(RoleType type){
        MenuDto menuDto  = new MenuDto();
        menuDto.setTitle("Credit Administration");
        menuDto.setIcon("");
        menuDto.setChildren(menuByRoleType(type));
        return menuDto;

    }


    private List<MenuDto> menuByRoleType(RoleType type){
        List<MenuDto> menuDtoList = new ArrayList<>();
        switch (type){
            case CAD_ADMIN:
                MenuDto menuDto  = new MenuDto();
                menuDto.setTitle("Unassigned Approved Loan");
                menuDto.setIcon("");
                menuDto.setLink("");
                menuDtoList.add(menuDto);
                menuDto  = new MenuDto();
                menuDto.setTitle("Disbursement Pending");
                menuDto.setIcon("");
                menuDto.setLink("");
                menuDtoList.add(menuDto);
                menuDto  = new MenuDto();
                menuDto.setTitle("CAD Documents");
                menuDto.setIcon("");
                menuDto.setLink("");
                menuDtoList.add(menuDto);
                return menuDtoList;
        }
        return menuDtoList;
    }

}
