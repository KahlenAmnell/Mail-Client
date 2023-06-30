package pl.bernat.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.bernat.Config;
import pl.bernat.EmailManager;
import pl.bernat.controller.services.LoginService;
import pl.bernat.model.EmailAccount;
import pl.bernat.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController extends BaseController implements Initializable {

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
                    case FAILED_BY_CREDENTIALS:
                        errorLabel.setText("invalid credentials");
                    case FAILED_BY_UNEXPECTED_ERROR:
                        errorLabel.setText("unexpected error");
                    case FAILED_BY_NETWORK:
                        errorLabel.setText("no internet connection");
                    default:
                        errorLabel.setText("unexpected error");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textField.setText(Config.EMAIL_PASSWORD);
        passwordField.setText(Config.EMAIL_ADDRESS);
    }
}

