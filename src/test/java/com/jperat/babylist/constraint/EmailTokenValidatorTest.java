package com.jperat.babylist.constraint;

import com.jperat.babylist.entity.User;
import com.jperat.babylist.form.ResetPasswordForm;
import com.jperat.babylist.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.when;

public class EmailTokenValidatorTest {

    private static final String EMAIL = "test@test.test";
    private static final String TOKEN = "token";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EmailTokenValidator emailTokenValidator;

    @Test
    public void testIsValid() {
        MockitoAnnotations.initMocks(this);
        ConstraintValidatorContext constraintValidatorContext = Mockito.mock(ConstraintValidatorContext.class);
        when(userRepository.findOneByEmailAndToken(EMAIL, TOKEN)).thenReturn(initUser());
        Assertions.assertEquals(true, emailTokenValidator.isValid(initResetPasswordForm(), constraintValidatorContext));
    }

    private User initUser() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 24);
        User user = new User();
        user.setEmail(EMAIL);
        user.setToken(TOKEN);
        user.setTokenExpiryDate(calendar.getTime());
        return user;
    }

    private ResetPasswordForm initResetPasswordForm() {
        ResetPasswordForm resetPasswordForm = new ResetPasswordForm();
        resetPasswordForm.setEmail(EMAIL);
        resetPasswordForm.setToken(TOKEN);
        return resetPasswordForm;
    }
}
