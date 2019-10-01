package com.sb.solutions.api.proposal.service;

import com.sb.solutions.api.proposal.entity.Proposal;
import com.sb.solutions.core.service.BaseService;

import java.util.List;

public interface ProposalService extends BaseService<Proposal> {
    List<Proposal> saveAll(List<Proposal> proposals);
}
