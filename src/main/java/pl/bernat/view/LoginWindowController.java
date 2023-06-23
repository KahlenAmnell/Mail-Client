package pl.bernat.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginWindowController {

    @FXML
    private PasswordField PasswordField;

    @FXML
    private TextField TextField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginBtn;

    @FXML
    void loginButtonAction(ActionEvent event) {
        System.out.println("click");
    }

}

