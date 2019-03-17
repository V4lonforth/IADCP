package com.iad.courseProject.authentication;

import com.iad.courseProject.data.entities.User;
import com.iad.courseProject.data.entities.AccountConfirmation;
import com.iad.courseProject.data.entities.types.AccountStatus;
import com.iad.courseProject.data.services.AccountConfirmationService;
import com.iad.courseProject.data.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class AccountConfirmationController {
    private final AccountConfirmationService accountConfirmationService;
    private final UserService userService;
    private final TokenController tokenController;

    private static final String WELCOME_PAGE_URL = "/";
    private static final int ERROR_CODE = 400;

    @Autowired
    public AccountConfirmationController(AccountConfirmationService accountConfirmationService, UserService userService) {
        this.accountConfirmationService = accountConfirmationService;
        this.userService = userService;
        tokenController = new TokenController();
    }

    @RequestMapping("/confirm/{confirmationId}")
    public void confirm(@PathVariable String confirmationId, HttpServletResponse response) throws IOException {
        AccountConfirmation accountConfirmation = accountConfirmationService.getByConfirmationId(confirmationId);
        if (accountConfirmation != null) {
            User user = accountConfirmation.getUser();
            tokenController.setTokenCookie(response, user);
            response.sendRedirect(WELCOME_PAGE_URL);
            user.setAccountStatus(AccountStatus.OK);
            userService.save(user);
            accountConfirmationService.delete(accountConfirmation);
        } else {
            response.setStatus(ERROR_CODE);
        }
    }
}
