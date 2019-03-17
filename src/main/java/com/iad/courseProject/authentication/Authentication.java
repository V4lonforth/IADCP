package com.iad.courseProject.authentication;

import com.iad.courseProject.data.entities.User;
import com.iad.courseProject.data.entities.types.AccessLevel;
import com.iad.courseProject.data.entities.types.AccountStatus;
import com.iad.courseProject.data.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Named
@RequestScoped
public class Authentication {
    private final UserService userService;
    private final TokenController tokenController;

    @Autowired
    public Authentication(UserService userService) {
        this.userService = userService;
        this.tokenController = new TokenController();
    }

    public boolean isUserAuthenticated(HttpServletRequest request) {
        return tokenController.isTokenValid(tokenController.resolveToken(request));
    }

    public AuthenticationResult authenticate(String username, String password, HttpServletResponse response) {
        User user = userService.getByUsername(username);
        if (user == null) {
            return AuthenticationResult.USERNAME_NOT_FOUND;
        }
        if (!user.checkPassword(password)) {
            return AuthenticationResult.WRONG_PASSWORD;
        }
        switch (user.getAccountStatus()) {
            case OK:
                TokenController tokenController = new TokenController();
                tokenController.setTokenCookie(response, user);
                return AuthenticationResult.OK;
            case NOT_CONFIRMED:
                return AuthenticationResult.USER_NOT_CONFIRMED;
            default:
                return AuthenticationResult.USERNAME_NOT_FOUND;
        }
    }
}
