package com.sb.solutions.api.emailConfig.service;

import com.sb.solutions.api.emailConfig.entity.EmailConfig;
import com.sb.solutions.api.emailConfig.repository.EmailConfigRepository;
import com.sb.solutions.core.utils.email.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Rujan Maharjan on 7/1/2019
 */

@Service
public class EmailServiceImpl implements EmailConfigService {

    private final EmailConfigRepository emailConfigRepository;
    private final MailSenderService mailSenderService;

    public EmailServiceImpl(@Autowired EmailConfigRepository emailConfigRepository,
                            @Autowired MailSenderService mailSenderService) {
        this.emailConfigRepository = emailConfigRepository;
        this.mailSenderService = mailSenderService;
    }

    @Override
    public List<EmailConfig> findAll() {
        return emailConfigRepository.findAll();
    }

    @Override
    public EmailConfig findOne(Long id) {
        return emailConfigRepository.getOne(id);
    }

    @Override
    public EmailConfig save(EmailConfig emailConfig) {
        return emailConfigRepository.save(emailConfig);
    }

    @Override
    public Page<EmailConfig> findAllPageable(Object t, Pageable pageable) {
        return null;
    }
}
