package pl.bernat.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.bernat.EmailManager;
import pl.bernat.controller.services.LoginService;
import pl.bernat.model.EmailAccount;
import pl.bernat.view.ViewFactory;

public class LoginWindowController extends BaseController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField textField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginBtn;

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void loginButtonAction() {
        System.out.println("click");
        if(filedsAreValid()){
            EmailAccount emailAccount = new EmailAccount(textField.getText(), passwordField.getText());
            LoginService loginService = new LoginService(emailAccount, emailManager);
            loginService.start();
            loginService.setOnSucceeded(event -> {
                EmailLoginResult emailLoginResult = loginService.getValue();
                switch (emailLoginResult){
                    case SUCCESS:
                        System.out.println("login succesfull!!" + emailAccount);
                        viewFactory.showMainWindow();
                        Stage stage = (Stage) errorLabel.getScene().getWindow();
                        viewFactory.closeStage(stage);
                        return;
                }
            });
        }
    }

    private boolean filedsAreValid() {
        if(textField.getText().isEmpty()) {
            errorLabel.setText("Please fill email");
            return false;
        }
        if(passwordField.getText().isEmpty()) {
            errorLabel.setText("Please fill password");
            return false;
        }
        return true;
    }

}

