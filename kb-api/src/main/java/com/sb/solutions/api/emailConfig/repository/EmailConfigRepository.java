package com.sb.solutions.api.emailConfig.repository;

import com.sb.solutions.api.emailConfig.entity.EmailConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Rujan Maharjan on 7/1/2019
 */
public interface EmailConfigRepository extends JpaRepository<EmailConfig, Long> {
}
