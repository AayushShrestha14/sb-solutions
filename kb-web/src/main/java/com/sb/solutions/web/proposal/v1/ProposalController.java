package com.sb.solutions.web.proposal.v1;

import com.sb.solutions.api.proposal.entity.Proposal;
import com.sb.solutions.api.proposal.service.ProposalService;
import com.sb.solutions.core.dto.RestResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/proposal")
public class ProposalController {

    private ProposalService proposalService;

    @PostMapping
    public ResponseEntity<?> saveBasicInfo(@RequestBody Proposal proposal, BindingResult bindingResult){
        return new RestResponseDto().successModel(proposalService.save(proposal));
    }
}
