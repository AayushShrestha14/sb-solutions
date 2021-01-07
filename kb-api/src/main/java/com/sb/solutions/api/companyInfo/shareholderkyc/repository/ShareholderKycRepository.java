package com.sb.solutions.api.companyInfo.shareholderkyc.repository;

import com.sb.solutions.api.companyInfo.shareholderkyc.entity.ShareholderKyc;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Mohammad Hussain
 * created on 12/30/2020
 */
public interface ShareholderKycRepository extends JpaRepository<ShareholderKyc, Long> {
}
