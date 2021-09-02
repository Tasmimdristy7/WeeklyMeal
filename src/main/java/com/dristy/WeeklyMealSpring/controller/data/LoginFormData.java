package com.dristy.WeeklyMealSpring.controller.data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class LoginFormData {

    @NotEmpty(message = "Username should not be empty.")
    @Size(min = 5, max = 20, message = "Username should be in the range of 5 to 20 characters.")
    private String username;

    @NotEmpty(message = "Password should not be empty.")
    @Size(min = 5, max = 20, message = "Password should be in the range of 5 to 20 characters.")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
