package com.jperat.babylist.form;

import com.jperat.babylist.constraint.DuplicateField;
import com.jperat.babylist.constraint.EmailToken;
import com.jperat.babylist.constraint.Password;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@DuplicateField(getters = {"getPassword()", "getRepeatedPassword()"})
@EmailToken
public class ResetPasswordForm {

    @NotNull
    @Email
    @Length(max = 255)
    private String email;

    @NotNull
    @Length(max = 255)
    @Password()
    private String password;

    @NotNull()
    @Length(max = 255)
    private String repeatedPassword;

    @NotNull
    private String token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
