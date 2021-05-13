package com.sb.solutions.api.collateralSiteVisit.service;

import com.sb.solutions.api.collateralSiteVisit.entity.CollateralSiteVisit;
import com.sb.solutions.api.collateralSiteVisit.repository.CollateralSiteVisitRepository;
import com.sb.solutions.api.security.entity.Security;
import com.sb.solutions.api.security.service.SecurityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Mohammad Hussain on May, 2021
 */
@Service
public class CollateralSiteVisitServiceImpl implements CollateralSiteVisitService{

    private final CollateralSiteVisitRepository repository;
    private final SecurityService securityService;

    public CollateralSiteVisitServiceImpl(CollateralSiteVisitRepository repository,
                                          SecurityService securityService) {
        this.repository = repository;
        this.securityService = securityService;
    }

    @Override
    public List<CollateralSiteVisit> findAll() {
        return repository.findAll();
    }

    @Override
    public CollateralSiteVisit findOne(Long id) {
        return repository.getOne(id);
    }

    @Override
    public CollateralSiteVisit save(CollateralSiteVisit collateralSiteVisit) {
        return repository.save(collateralSiteVisit);
    }

    @Override
    public Page<CollateralSiteVisit> findAllPageable(Object t, Pageable pageable) {
        return null;
    }

    @Override
    public List<CollateralSiteVisit> saveAll(List<CollateralSiteVisit> collateralSiteVisits) {
        return repository.saveAll(collateralSiteVisits);
    }

    @Override
    public CollateralSiteVisit saveCollateralSiteVisit(Long securityId, CollateralSiteVisit collateralSiteVisit) {
        Security security = securityService.findOne(securityId);
        if (security != null) {
            collateralSiteVisit.setSecurity(security);
        }
        return repository.save(collateralSiteVisit);
    }

    @Override
    public List<CollateralSiteVisit> getCollateralSiteVisitBySecurityName(String securityName) {
        return repository.findCollateralSiteVisitBySecurityName(securityName);
    }

    @Override
    public CollateralSiteVisit getCollateralBySiteVisitDate(LocalDate siteVisitDate) {
        return repository.findCollateralSiteVisitBySiteVisitDate(siteVisitDate);
    }
}
