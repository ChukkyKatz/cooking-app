package com.chukkykatz.cooking.service;

import com.chukkykatz.cooking.domain.Dish;
import com.chukkykatz.cooking.domain.Receipt;
import com.chukkykatz.cooking.domain.Recipient;
import com.chukkykatz.cooking.repository.RecipientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;
    private TemplateEngine templateEngine;
    private RecipientRepository recipientRepository;
    @Value("${spring.mail.username}")
    private String serviceEmail;
    @Value("${application.email.template}")
    private String emailTemplate;
    @Value("${application.email.encoding}")
    private String emailEncoding;
    @Value("${application.email.subject}")
    private String emailSubject;

    @Autowired
    public EmailServiceImpl(final JavaMailSender javaMailSender,
                            final TemplateEngine templateEngine,
                            final RecipientRepository recipientRepository
    ) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.recipientRepository = recipientRepository;
    }

    @Override
    public void sendAdviceMessage(Map<Dish, Receipt> advices) {
        final Context context = new Context();
        context.setVariable("advices", advices);
        final String htmlContent = templateEngine.process(emailTemplate, context);
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, emailEncoding);
        final List<Recipient> recipients = recipientRepository.findAll();
        final List<String> addresses = new LinkedList<>();
        recipients.forEach(recipient -> addresses.add(recipient.getEmail()));
        final String[] addressesArr = new String[addresses.size()];
        try {
            mimeMessageHelper.setFrom(serviceEmail);
            mimeMessageHelper.setTo(addresses.toArray(addressesArr));
            mimeMessageHelper.setSubject(emailSubject);
            mimeMessageHelper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Fail to create email", e);
        }
    }
}
