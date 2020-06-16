package com.jperat.babylist.service;

import com.jperat.babylist.entity.User;
import com.jperat.babylist.exception.UserAllreadyExistException;
import com.jperat.babylist.exception.UserNotFoundException;
import com.jperat.babylist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class UserService {

    private static final String NEW_USER_TEMPLATE = "mail/html/create_user.html";
    private static final String NEW_USER_ROUTE = "/login/create-password";
    private static final String NEW_USER_TITLE = "email.create_user.title";
    private static final String FORGOT_PASWORD_TEMPLATE = "mail/html/forgot_password.html";
    private static final String FORGOT_PASWORD_ROUTE = "/login/reset-password";
    private static final String FORGOT_PASWORD_TITLE = "email.forgot_password.title";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailerService mailerService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Environment environment;

    public void createNewUser(User user) throws UserAllreadyExistException {
        if (userRepository.findOneByEmail(user.getEmail()) != null) {
            throw new UserAllreadyExistException();
        }
        try {
            createToken(user);
            sendEmail(user, NEW_USER_TEMPLATE, NEW_USER_ROUTE, NEW_USER_TITLE);
            user.setEnable(false);
            userRepository.save(user);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void forgotPassword(String email) {
        User user = userRepository.findOneByEmail(email);
        if (user != null) {
            try {
                createToken(user);
                sendEmail(user, FORGOT_PASWORD_TEMPLATE, FORGOT_PASWORD_ROUTE, FORGOT_PASWORD_TITLE);
                userRepository.save(user);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public void updatePassword(User user, String password) {
        user.setToken(null);
        user.setTokenExpiryDate(null);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    private void sendEmail(User user, String template, String route, String subject) throws MessagingException {
        String[] to = { user.getEmail() };
        String from = environment.getRequiredProperty("email.noreply");
        Context context = new Context(LocaleContextHolder.getLocale());
        context.setVariable("user", user);
        context.setVariable("title", subject);
        String base = "";
        try {
            StringBuffer url = request.getRequestURL();
            String uri = request.getRequestURI();
            String ctx = request.getContextPath();
            base = url.substring(0, url.length() - uri.length() + ctx.length());
        } catch (Exception e) {
            base = "http://127.0.0.1:8080";
        }
        context.setVariable("url", base+route);
        mailerService.sendSimpleMail(to, messageSource.getMessage(subject, null, LocaleContextHolder.getLocale()), from, template, context);
    }

    private void createToken(User user) {
        user.setToken(UUID.randomUUID().toString());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 24);
        user.setTokenExpiryDate(calendar.getTime());
    }
}
