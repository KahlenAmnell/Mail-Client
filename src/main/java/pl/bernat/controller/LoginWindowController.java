package pl.bernat.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.bernat.EmailManager;
import pl.bernat.view.ViewFactory;

public class LoginWindowController extends BaseController {

    @FXML
    private PasswordField PasswordField;

    @FXML
    private TextField TextField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginBtn;

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void loginButtonAction(ActionEvent event) {

        System.out.println("click");
        viewFactory.showMainWindow();
        Stage stage = (Stage) errorLabel.getScene().getWindow();
        viewFactory.closeStage(stage);
    }

}

