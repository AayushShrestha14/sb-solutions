package com.sb.solutions.api.branch.service;

import com.sb.solutions.api.branch.entity.Branch;
import com.sb.solutions.api.branch.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        return branchRepository.save(branch);
    }

    @Override
    public Page<Branch> findAllPageable(Branch branch,Pageable pageable) {
        return branchRepository.findAll(pageable);
    }
}
