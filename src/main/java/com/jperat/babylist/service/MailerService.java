package com.jperat.babylist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailerService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    /*
     * Send HTML mail (simple)
     */
    public void sendSimpleMail(String[] to, String subject, String from, String template, Context context)
            throws MessagingException {

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");

        message.setSubject(subject);
        message.setFrom(from);
        message.setTo(to);

        final String text = this.templateEngine.process(template, context);

        message.setText(text, true);

        this.mailSender.send(mimeMessage);
    }
}
