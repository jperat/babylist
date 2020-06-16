package com.jperat.babylist.constraint;

import com.jperat.babylist.entity.User;
import com.jperat.babylist.form.ResetPasswordForm;
import com.jperat.babylist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class EmailTokenValidator implements ConstraintValidator<EmailToken, ResetPasswordForm> {

    @Autowired
    UserRepository userRepository;

    @Override
    public void initialize(EmailToken constraintAnnotation) {

    }

    @Override
    public boolean isValid(ResetPasswordForm resetPasswordForm, ConstraintValidatorContext constraintValidatorContext) {
        User user = userRepository.findOneByEmailAndToken(resetPasswordForm.getEmail(), resetPasswordForm.getToken());
        return (user != null && user.getTokenExpiryDate().after(new Date()));
    }
}
