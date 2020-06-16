package com.jperat.babylist.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

import static org.mockito.Mockito.when;

public class MailerServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private MailerService mailerService;

    @BeforeEach
    public void initMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSendSimpleMail() {
        Session session = Session.getInstance(new Properties());
        when(mailSender.createMimeMessage()).thenReturn(new MimeMessage(session));
        Context context = new Context();
        String template = "template";
        when(templateEngine.process(template, context)).thenReturn("hello wolrd");
        try {
            String[] to = {"test@test.com"};
            mailerService.sendSimpleMail(to, "Subject", "from@test.com", template, context);
        } catch (MessagingException e) {

        }
    }
}
