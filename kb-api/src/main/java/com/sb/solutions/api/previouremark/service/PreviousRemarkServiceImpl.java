package com.sb.solutions.api.previouremark.service;

import com.sb.solutions.api.previouremark.entity.PreviousRemarks;
import com.sb.solutions.api.previouremark.repository.PreviousRemarksRepository;
import com.sb.solutions.core.repository.BaseRepository;
import com.sb.solutions.core.repository.BaseSpecBuilder;
import com.sb.solutions.core.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Map;

@Service
public class PreviousRemarkServiceImpl extends BaseServiceImpl<PreviousRemarks, Long> implements PreviousRemarkService {

    private PreviousRemarksRepository repository;

    protected PreviousRemarkServiceImpl(PreviousRemarksRepository repository) {
        super(repository);
    }


    @Override
    protected BaseSpecBuilder<PreviousRemarks> getSpec(Map<String, String> filterParams) {
        return null;
    }
}
