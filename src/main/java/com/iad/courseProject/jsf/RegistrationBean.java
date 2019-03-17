package com.iad.courseProject.jsf;

import com.iad.courseProject.authentication.Registration;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class RegistrationBean {
    private String username;
    private String email;
    private String password;

    private final Registration registration;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

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

    @Autowired
    public RegistrationBean(Registration registration) {
        this.registration = registration;
    }

    public void register() {
        registration.register(username, email, password);
    }
}
