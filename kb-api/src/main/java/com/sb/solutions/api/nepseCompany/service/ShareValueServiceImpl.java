package com.sb.solutions.api.nepseCompany.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.nepseCompany.entity.ShareValue;
import com.sb.solutions.api.nepseCompany.repository.ShareValueRepository;
import com.sb.solutions.core.enums.Status;

@Service
public class ShareValueServiceImpl implements ShareValueService {

    private final ShareValueRepository shareValueRepository;

    public ShareValueServiceImpl(
        ShareValueRepository shareValueRepository) {
        this.shareValueRepository = shareValueRepository;
    }

    @Override
    public List<ShareValue> findAll() {
        return shareValueRepository.findAll();
    }

    @Override
    public ShareValue findOne(Long id) {
        return null;
    }

    @Override
    public ShareValue save(ShareValue shareValue) {
        ShareValue previousShareValue = shareValueRepository.findFirstByOrderByIdDesc();
        previousShareValue.setStatus(Status.INACTIVE);
        shareValue.setStatus(Status.ACTIVE);
        shareValueRepository.save(previousShareValue);
        return shareValueRepository.save(shareValue);
    }

    @Override
    public Page<ShareValue> findAllPageable(Object t, Pageable pageable) {
        return shareValueRepository.getALlPageable(pageable);
    }

    @Override
    public List<ShareValue> saveAll(List<ShareValue> list) {
        return null;
    }

    @Override
    public List<ShareValue> findTopList() {
        return shareValueRepository.findAllByOrderByIdDesc();
    }
}
