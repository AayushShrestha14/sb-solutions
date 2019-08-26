package com.sb.solutions.core.utils.email;

import com.sb.solutions.core.constant.EmailConstant;
import com.sb.solutions.core.exception.ServiceValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class MailThreadService {

    private final Logger logger = LoggerFactory.getLogger(MailThreadService.class);

    private final MailSenderService mailSenderService;

    @Value("${bank.name}")
    private String bankName;

    @Value("${bank.website}")
    private String bankWebsite;

    public MailThreadService(@Autowired MailSenderService mailSenderService) {

        this.mailSenderService = mailSenderService;
    }

    public void sendMail(Email email) {
        new Thread(() -> {
            try {
                mailSenderService.sendMailWithAttachmentBcc(email);
            } catch (Exception e) {
                logger.error("error sending email", e);
            }
        }).start();
    }

    public void sendMain(EmailConstant.Template template, Email email) {
        new Thread(() -> {
            try {
                mailSenderService.send(template, email);
            } catch (Exception e) {
                logger.error("error sending email", e);
            }
        }).start();
    }

    public void testMail(Email email) {

        try {
            mailSenderService.sendMailWithAttachmentBcc(email);
        } catch (MessagingException e) {
            logger.error("error sending email", e);
            throw new ServiceValidationException(e.getLocalizedMessage());
        } catch (IOException e) {
            logger.error("error sending email", e);
            throw new ServiceValidationException(e.getLocalizedMessage());
        }
    }

    public void testMail(EmailConstant.Template template, Email email) {
        email.setBankName(bankName);
        email.setBankWebsite(bankWebsite);
        mailSenderService.send(template, email);


    }
}
