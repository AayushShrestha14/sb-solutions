package com.sb.solutions.core.utils.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailThreadService {

    private final Logger logger = LoggerFactory.getLogger(MailThreadService.class);

    private final MailSenderService mailSenderService;

    public MailThreadService(@Autowired MailSenderService mailSenderService) {

        this.mailSenderService = mailSenderService;
    }

    public void sendMail(Email email) {
        new Thread(() -> {
            try {
                mailSenderService.sendMailWithAttachmentBcc(email);
            } catch (Exception e) {
                logger.error("error sending email", e.getLocalizedMessage());
                e.printStackTrace();
            }
        }).start();


    }

}
