package com.sb.solutions.api.proposal.service;

import com.sb.solutions.api.proposal.entity.Proposal;
import com.sb.solutions.api.proposal.repository.ProposalRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProposalServiceImpl implements ProposalService {

    private final ProposalRepository proposalRepository;

    @Override
    public List<Proposal> findAll() {
        return proposalRepository.findAll();
    }

    @Override
    public Proposal findOne(Long id) {
        return proposalRepository.getOne(id);
    }

    @Override
    public Proposal save(Proposal proposal) {
        return proposalRepository.save(proposal);
    }

    @Override
    public Page<Proposal> findAllPageable(Object t, Pageable pageable) {
        return null;
    }
}
