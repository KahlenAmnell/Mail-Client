package pl.bernat.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.bernat.EmailManager;
import pl.bernat.controller.services.EmailSenderService;
import pl.bernat.model.EmailAccount;
import pl.bernat.view.ViewFactory;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ComposeMessageController extends BaseController implements Initializable {
    @FXML
    private HBox attachmentsHBox;
    private List<File> attachments = new ArrayList<File>();
    @FXML
    private Label errorLabel;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private TextField recipientTextField;

    @FXML
    private Button sendButton;

    @FXML
    private TextField subjectTextField;

    @FXML
    private ChoiceBox<EmailAccount> emailAccountChoice;

    @FXML
    void sendButtonAction() {
        EmailSenderService emailSenderService = new EmailSenderService(
            emailAccountChoice.getValue(),
                subjectTextField.getText(),
                recipientTextField.getText(),
                htmlEditor.getHtmlText(),
                attachments
        );
        emailSenderService.start();
        emailSenderService.setOnSucceeded(e -> {
            EmailSendingResult emailSendingResult = emailSenderService.getValue();
            switch(emailSendingResult){
                case SUCCESS:
                    Stage stage = (Stage) recipientTextField.getScene().getWindow();
                    viewFactory.closeStage(stage);
                    break;
                case FAILED_BY_PROVIDER:
                    errorLabel.setText("Provirer error");
                    break;
                case FAILED_BY_UNEXPECTED_ERROR:
                    errorLabel.setText("Unexpected error");
                    break;
            }
        });
    }

    @FXML
    void addAttach() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null ){
            attachments.add(selectedFile);
            attachmentsHBox.getChildren().add(setLabel(selectedFile.getName()));
        }
    }
    public ComposeMessageController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emailAccountChoice.setItems(emailManager.getEmailAccounts());
        emailAccountChoice.setValue(emailManager.getEmailAccounts().get(0));
    }
    private Label setLabel(String fileName){
        Label label = new Label(fileName);
        label.setPadding(new Insets(4));
        label.setStyle("-fx-background-color: White");
        return label;
    }
}
