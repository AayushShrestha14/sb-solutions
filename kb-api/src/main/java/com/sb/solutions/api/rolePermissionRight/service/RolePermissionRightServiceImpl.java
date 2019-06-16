package com.sb.solutions.api.rolePermissionRight.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.rolePermissionRight.entity.RolePermissionRights;
import com.sb.solutions.api.rolePermissionRight.entity.UrlApi;
import com.sb.solutions.api.rolePermissionRight.repository.RolePermissionRightRepository;
import com.sb.solutions.api.rolePermissionRight.repository.UrlApiRepository;

/**
 * @author Rujan Maharjan on 3/28/2019
 */
@Service
public class RolePermissionRightServiceImpl implements RolePermissionRightService {

    @Autowired
    RolePermissionRightRepository rolePermissionRightRepository;
    @Autowired
    UrlApiRepository urlApiRepository;


    @Override
    public List<RolePermissionRights> findAll() {
        return rolePermissionRightRepository.findAll();
    }

    @Override
    public RolePermissionRights findOne(Long id) {
        return null;
    }

    @Override
    public RolePermissionRights save(RolePermissionRights rolePermissionRights) {
        return rolePermissionRightRepository.save(rolePermissionRights);
    }

    @Override
    public Page<RolePermissionRights> findAllPageable(Object t, Pageable pageable) {
        return null;
    }

    @Override
    public List<RolePermissionRights> getByRoleId(Long id) {
        return rolePermissionRightRepository.findByRole(id);
    }

    @Override
    public void saveList(List<RolePermissionRights> rolePermissionRightsList) {
        List<RolePermissionRights> rolePermissionRightsList1 = new ArrayList<>();

        for (RolePermissionRights r : rolePermissionRightsList) {
            List<UrlApi> tempUrlApis = new ArrayList<>();
            for (UrlApi urlApi : r.getApiRights()) {
                if (!urlApi.isChecked()) {
                    urlApiRepository
                        .deleteRelationRolePermissionApiRights(r.getId() == null ? 0 : r.getId(),
                            urlApi.getId());
                } else {
                    tempUrlApis.add(urlApi);
                }
            }

            r.setLastModifiedAt(new Date());
            if (r.isDel()) {
                if (r.getId() != null) {
                    try {
                        rolePermissionRightRepository.deleteById(r.getId() == null ? 0 : r.getId());
                    } catch (Exception e) {
                    }
                }
                rolePermissionRightRepository.deleteRolePermissionRightsByRole(r.getRole().getId(),
                    r.getPermission().getId());
            } else {
                r.getApiRights().clear();

                r.setApiRights(tempUrlApis);
                rolePermissionRightsList1.add(r);
            }


        }

        rolePermissionRightRepository.saveAll(rolePermissionRightsList1);
    }
}
