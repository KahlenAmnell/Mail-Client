package pl.bernat.controller;

import pl.bernat.EmailManager;
import pl.bernat.view.ViewFactory;

public abstract class BaseController {
    private EmailManager emailManager;
    private ViewFactory viewFactory;

    public BaseController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        this.emailManager = emailManager;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    private String fxmlName;
}
