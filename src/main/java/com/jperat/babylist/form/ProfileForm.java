package com.jperat.babylist.form;

import com.jperat.babylist.entity.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class ProfileForm {

    @NotNull
    @Length(max = 255)
    private String firstname;

    @NotNull
    @Length(max = 255)
    private String lastname;

    public ProfileForm() {}

    public ProfileForm(User user) {
        firstname = user.getFirstname();
        lastname = user.getLastname();
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
