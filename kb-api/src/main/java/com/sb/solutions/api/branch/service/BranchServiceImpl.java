package com.sb.solutions.api.branch.service;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.branch.repository.BranchRepository;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.enums.Status;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Rujan Maharjan on 2/13/2019
 */
@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    BranchRepository branchRepository;

    @Override
    public List<Branch> findAll() {
        return branchRepository.findAll();
    }

    @Override
    public Branch findOne(Long id) {
        return branchRepository.getOne(id);
    }

    @Override
    public Branch save(Branch branch) {
        branch.setLastModified(new Date());
        if(branch.getId()==null){
            branch.setStatus(Status.ACTIVE);
        }
        branch.setBranchCode(branch.getBranchCode().toUpperCase());
        return branchRepository.save(branch);
    }

    @Override
    public Page<Branch> findAllPageable(Object object,Pageable pageable) {
        ObjectMapper objectMapper = new ObjectMapper();
        SearchDto s = objectMapper.convertValue(object,SearchDto.class);
        return branchRepository.branchFilter(s.getName()==null?"":s.getName(),pageable);
    }

    @Override
    public Map<Object,Object> branchStatusCount(){
        System.out.println(branchRepository.branchStatusCount());
        return branchRepository.branchStatusCount();
    }
}
