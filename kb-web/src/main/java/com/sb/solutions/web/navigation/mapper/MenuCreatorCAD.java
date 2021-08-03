package com.sb.solutions.web.navigation.mapper;

import java.util.ArrayList;
import java.util.List;

import com.sb.solutions.core.enums.RoleType;
import com.sb.solutions.web.navigation.dto.MenuDto;

/**
 * @author : Rujan Maharjan on  1/4/2021
 **/
public class MenuCreatorCAD {


    public static MenuDto cadNav() {
        MenuDto menuDto = new MenuDto();
        menuDto.setTitle("Credit Administration");
        menuDto.setIcon("book-open-outline");
        menuDto.setChildren(subNav());
        return menuDto;

    }


    private static List<MenuDto> subNav() {
        List<MenuDto> menuDtoList = new ArrayList<>();

        MenuDto menuDto = new MenuDto();
        menuDto.setTitle("Unassigned Approved Loan");
        menuDto.setIcon("file-text-outline");
        menuDto.setLink("/home/credit");
        menuDtoList.add(menuDto);

        menuDto = new MenuDto();
        menuDto.setTitle("Offer Letter Pending");
        menuDto.setIcon("edit-2-outline");
        menuDto.setLink("/home/credit/offer-pending");
        menuDtoList.add(menuDto);

        menuDto = new MenuDto();
        menuDto.setTitle("Offer Letter Approved");
        menuDto.setIcon("checkmark-square-outline");
        menuDto.setLink("/home/credit/offer-approved");
        menuDtoList.add(menuDto);

        menuDto = new MenuDto();
        menuDto.setTitle("Legal Review Pending");
        menuDto.setIcon("edit-2-outline");
        menuDto.setLink("/home/credit/legal-pending");
        menuDtoList.add(menuDto);

        menuDto = new MenuDto();
        menuDto.setTitle("Disbursement Pending");
        menuDto.setIcon("edit-2-outline");
        menuDto.setLink("/home/credit/disbursement-pending");
        menuDtoList.add(menuDto);

        menuDto = new MenuDto();
        menuDto.setTitle("Disbursement Approved");
        menuDto.setIcon("checkmark-square-outline");
        menuDto.setLink("/home/credit/disbursement-approved");
        menuDtoList.add(menuDto);

        menuDto = new MenuDto();
        menuDto.setTitle("All CAD Files");
        menuDto.setIcon("file-text-outline");
        menuDto.setLink("/home/credit/cad-documents");
        menuDtoList.add(menuDto);

        return menuDtoList;

    }

}
