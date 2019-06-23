package com.sb.solutions.api.branch.service;

import com.sb.solutions.api.basehttp.BaseHttpService;
import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.branch.repository.BranchRepository;
import com.sb.solutions.api.branch.repository.specification.BranchSpecBuilder;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.enums.Status;
import com.sb.solutions.core.utils.csv.CsvMaker;
import com.sb.solutions.core.utils.csv.CsvReader;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Rujan Maharjan on 2/13/2019
 */
@Service
public class BranchServiceImpl implements BranchService {
    private static final Logger log = LoggerFactory.getLogger(BranchServiceImpl.class);

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    BaseHttpService baseHttpService;

    @Autowired
    UserService userService;


    @Override
    public List<Branch> findAll() {
        return branchRepository.findAll();
    }

    @Override
    public Branch findOne(Long id) {
        return branchRepository.getOne(id);
    }

    @Override
    @PreAuthorize("hasAuthority('ADD BRANCH') or hasAuthority('EDIT BRANCH')")
    public Branch save(Branch branch) {
        branch.setLastModifiedAt(new Date());
        if (branch.getId() == null) {
            branch.setStatus(Status.ACTIVE);
        }
        branch.setBranchCode(branch.getBranchCode().toUpperCase());
        return branchRepository.save(branch);
    }

    @Override
    @PreAuthorize("hasAuthority('VIEW BRANCH')")
    public Page<Branch> findAllPageable(Object object, Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> s = objectMapper.convertValue(object, Map.class);
        final BranchSpecBuilder branchSpecBuilder = new BranchSpecBuilder(s);
        final Specification<Branch> specification = branchSpecBuilder.build();
        return branchRepository.findAll(specification, pageable);
    }

    @Override
    public Map<Object, Object> branchStatusCount() {
        return branchRepository.branchStatusCount();
    }

    @Override
    @PreAuthorize("hasAuthority('DOWNLOAD CSV')")
    public String csv(SearchDto searchDto) {
        CsvMaker csvMaker = new CsvMaker();
        List branchList = branchRepository.branchCsvFilter(searchDto.getName() == null ? "" : searchDto.getName());
        Map<String, String> header = new LinkedHashMap<>();
        header.put("name", " Name");
        header.put("province,name", "Province");
        header.put("province,district", "District");
        header.put("branchCode", "Branch Code");
        String url = csvMaker.csv("branch", header, branchList, UploadDir.branchCsv);
        return baseHttpService.getBaseUrl() + url;
    }

    @Override
    public void saveExcel(MultipartFile file) {
        CsvReader csvReader = new CsvReader();
        csvReader.excelReader(file);
    }

    @Override
    public List<Branch> getAccessBranchByCurrentUser() {
        return userService.getAuthenticated().getBranch();
    }

    @Override
    public List<Branch> getBranchNoTAssignUser(Long roleId) {
        List<User> userList = userService.findByRoleId(roleId);
        List<Long> branches = new ArrayList<>();
        for (User u : userList) {
            for (Branch b : u.getBranch()) {
                branches.add(b.getId());
            }
        }
        return branchRepository.getByIdNotIn(branches);
    }
}
