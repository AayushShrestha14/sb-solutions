package com.sb.solutions.api.nepseCompany.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
        return shareValueRepository.getOne(id);
    }

    @Override
    public ShareValue save(ShareValue shareValue) {
        ShareValue previousShareValue = shareValueRepository.findFirstByOrderByIdDesc();
        if (!ObjectUtils.isEmpty(previousShareValue)) {
            previousShareValue.setStatus(Status.INACTIVE);
            shareValueRepository.save(previousShareValue);
        }
        shareValue.setStatus(Status.ACTIVE);
        return shareValueRepository.save(shareValue);
    }

    @Override
    public Page<ShareValue> findAllPageable(Object t, Pageable pageable) {
        return shareValueRepository.findAllByOrderByIdDesc(pageable);
    }

    @Override
    public List<ShareValue> saveAll(List<ShareValue> shareValueList) {
        return shareValueRepository.saveAll(shareValueList);
    }
}
