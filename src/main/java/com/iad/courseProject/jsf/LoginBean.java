package com.iad.courseProject.jsf;

import com.iad.courseProject.authentication.Authentication;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

@Named
@ViewScoped
public class LoginBean {
    private String username;
    private String password;

    private final Authentication authentication;

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

    @Autowired
    public LoginBean(Authentication authentication) {
        this.authentication = authentication;
    }

    public String login() {
        switch (authentication.authenticate(username, password, (HttpServletResponse)(FacesContext.getCurrentInstance().getExternalContext().getResponse()))) {
            case OK:
                return "home";
            default:
                return null;
        }
    }
}
