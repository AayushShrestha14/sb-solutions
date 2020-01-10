package com.sb.solutions.api.sharesecurity.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sb.solutions.api.sharesecurity.ShareSecurity;
import com.sb.solutions.api.sharesecurity.repository.ShareSecurityRepo;

@Service("shareSecurityService")
public class ShareSecurityServiceImpl implements ShareSecurityService {

    private ShareSecurityRepo shareSecurityRepo;

    public ShareSecurityServiceImpl(
        ShareSecurityRepo shareSecurityRepo) {
        this.shareSecurityRepo = shareSecurityRepo;
    }

    @Override
    public List<ShareSecurity> findAll() {
        return shareSecurityRepo.findAll();
    }

    @Override
    public ShareSecurity findOne(Long id) {
        return shareSecurityRepo.getOne(id);
    }

    @Override
    public ShareSecurity save(ShareSecurity shareSecurity) {
        return shareSecurityRepo.save(shareSecurity);
    }

    @Override
    public Page<ShareSecurity> findAllPageable(Object t, Pageable pageable) {
        return null;
    }

    @Override
    public List<ShareSecurity> saveAll(List<ShareSecurity> list) {
        return shareSecurityRepo.saveAll(list);
    }
}
