package com.iad.courseProject.authentication;

import com.iad.courseProject.MessageController;
import com.iad.courseProject.data.entities.AccountConfirmation;
import com.iad.courseProject.data.entities.User;
import com.iad.courseProject.data.entities.types.AccessLevel;
import com.iad.courseProject.data.entities.types.AccountStatus;
import com.iad.courseProject.data.services.AccountConfirmationService;
import com.iad.courseProject.data.services.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.inject.Named;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;

@Named
public class Registration {
    private final UserService userService;
    private final AccountConfirmationService accountConfirmationService;
    private final MessageController messageController;

    private final static int URL_CONFIRMATION_SIZE = 20;
    private final static int NEW_PASSWORD_SIZE = 10;
    private final static String SERVER_URL = "http://localhost:9090/api";

    @Autowired
    public Registration(UserService userService, AccountConfirmationService accountConfirmationService, MessageController messageController) {
        this.userService = userService;
        this.accountConfirmationService = accountConfirmationService;
        this.messageController = messageController;
    }

    public RegistrationResult register(String username, String email, String password) {
        return register(username, email, password, AccessLevel.USER, AccountStatus.NOT_CONFIRMED);
    }
    public RegistrationResult register(String username, String email, String password, AccessLevel accessLevel, AccountStatus accountStatus) {
        if (userService.getByUsername(username) != null) {
            return RegistrationResult.USERNAME_EXISTS;
        }
        if (userService.getByEmail(email) != null) {
            return RegistrationResult.EMAIL_EXISTS;
        }
        User user = new User(username, email, password, accessLevel, accountStatus);
        user = userService.save(user);
        sendConfirmationEmail(user);
        return RegistrationResult.OK;
    }

    private void sendConfirmationEmail(User user) {
        String generatedString = RandomStringUtils.randomAlphabetic(URL_CONFIRMATION_SIZE);
        try {
            MimeMessage msg = messageController.getBaseMessage();
            msg.setText(String.format("Confirm account: %s/confirm/%s", SERVER_URL, generatedString));
            msg.setRecipients(Message.RecipientType.TO, user.getEmail());
            msg.setSubject("Registration");
            Transport.send(msg);
            accountConfirmationService.add(new AccountConfirmation(user, generatedString, new Timestamp(System.currentTimeMillis())));
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendGeneratedPasswordEmail(User user) {
        String generatedString = RandomStringUtils.randomAlphabetic(NEW_PASSWORD_SIZE);
        try {
            MimeMessage msg = messageController.getBaseMessage();
            msg.setText(String.format("Your new password: %s", generatedString));
            msg.setRecipients(Message.RecipientType.TO, user.getEmail());
            msg.setSubject("Password");
            Transport.send(msg);
            user.setPassword(BCrypt.hashpw(generatedString, BCrypt.gensalt(12)));
            userService.save(user);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }
}
