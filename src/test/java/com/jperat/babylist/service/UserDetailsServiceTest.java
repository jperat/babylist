package com.jperat.babylist.service;

import com.jperat.babylist.entity.Role;
import com.jperat.babylist.entity.User;
import com.jperat.babylist.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.when;

public class UserDetailsServiceTest {

    private final static String EMAIL = "email@email.com";
    private final static String FIRSTNAME = "firstname";
    private final static String LASTNAME = "lastname";
    private final static String PASSWORD = "password";
    private final static boolean ENABLE = true;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsService userDetailsService;

    @BeforeEach
    public void initMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadUserByUsername() {
        when(userRepository.findOneByEmail(EMAIL)).thenReturn(getUser());
        UserDetails user = userDetailsService.loadUserByUsername(EMAIL);
        Assertions.assertEquals(EMAIL, user.getUsername());
        Assertions.assertEquals(PASSWORD, user.getPassword());
        Assertions.assertTrue(user.isEnabled());
        Assertions.assertEquals(1,user.getAuthorities().size());
    }

    private User getUser() {
        User user = new User();
        Role role =  new Role();
        role.setRole("ROLE_ADMIN");
        user.addRole(role);
        user.setEmail(EMAIL);
        user.setFirstname(FIRSTNAME);
        user.setLastname(LASTNAME);
        user.setPassword(PASSWORD);
        user.setEnable(ENABLE);
        return user;
    }
}
