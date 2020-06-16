package com.jperat.babylist.service;

import static org.junit.jupiter.api.Assertions.*;

import com.jperat.babylist.entity.User;
import com.jperat.babylist.exception.UserAllreadyExistException;
import com.jperat.babylist.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MailerService mailerService;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private UserService userService;

    @Mock
    private Environment environment;

    @BeforeEach
    public void initMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdatePassword() {
        User user = new User();
        String password = "mypassword";
        when(passwordEncoder.encode(password)).thenReturn(password);
        userService.updatePassword(user, password);
        Assertions.assertEquals(password, user.getPassword());
    }

    @Test
    public void testCreateUserUserAllreadyExistException() {
        User user = new User();
        user.setEmail("wrong");
        when(userRepository.findOneByEmail("wrong")).thenReturn(new User());
        Assertions.assertThrows(UserAllreadyExistException.class, () -> {
           userService.createNewUser(user);
        });
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("test@test.com");
        when(userRepository.findOneByEmail("test@test.com")).thenReturn(null);
        when(environment.getRequiredProperty("email.noreply")).thenReturn("no-reply@test.test");
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://www.test.test"));
        when(httpServletRequest.getRequestURI()).thenReturn("");
        when(httpServletRequest.getContextPath()).thenReturn("");
        when(messageSource.getMessage("email.create_user.title", null, LocaleContextHolder.getLocale())).thenReturn("my title");
        try {
            userService.createNewUser(user);
            Assertions.assertNotNull(user.getTokenExpiryDate());
            Assertions.assertNotNull(user.getToken());
        } catch (UserAllreadyExistException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testForgotPassword() {
        User user = new User();
        user.setEmail("test@test.com");
        when(userRepository.findOneByEmail("test@test.com")).thenReturn(user);
        when(environment.getRequiredProperty("email.noreply")).thenReturn("no-reply@test.test");
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("http://www.test.test"));
        when(httpServletRequest.getRequestURI()).thenReturn("");
        when(httpServletRequest.getContextPath()).thenReturn("");
        when(messageSource.getMessage("email.create_user.title", null, LocaleContextHolder.getLocale())).thenReturn("my title");
        userService.forgotPassword(user.getEmail());
        Assertions.assertNotNull(user.getTokenExpiryDate());
        Assertions.assertNotNull(user.getToken());
    }

}
