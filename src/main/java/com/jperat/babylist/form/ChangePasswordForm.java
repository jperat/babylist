package com.jperat.babylist.form;

import com.jperat.babylist.constraint.DuplicateField;
import com.jperat.babylist.constraint.Password;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@DuplicateField(getters = {"getPassword()", "getRepeatedPassword()"})
public class ChangePasswordForm {

    @NotNull
    @Length(max = 255)
    @Password()
    private String password;

    @NotNull()
    @Length(max = 255)
    private String repeatedPassword;

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
}
