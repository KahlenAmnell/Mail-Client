package pl.bernat.controller.services;

import pl.bernat.EmailManager;
import pl.bernat.controller.EmailLoginResult;
import pl.bernat.model.EmailAccount;

public class LoginService {
    EmailAccount emailAccount;
    EmailManager emailManager;

    public LoginService(EmailAccount emailAccount, EmailManager emailManager) {
        this.emailAccount = emailAccount;
        this.emailManager = emailManager;
    }

    public EmailLoginResult login(){
        return EmailLoginResult.SUCCESS;
    }
}
