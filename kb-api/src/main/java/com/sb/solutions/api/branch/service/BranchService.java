package com.sb.solutions.api.branch.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.service.BaseService;

/**
 * @author Rujan Maharjan on 2/13/2019
 */
public interface BranchService extends BaseService<Branch> {

    Map<Object, Object> branchStatusCount();

    String csv(Object searchDto);

    void saveExcel(MultipartFile file);

    List<Branch> getAccessBranchByCurrentUser();

    List<Branch> getBranchNoTAssignUser(Long roleId);

}
