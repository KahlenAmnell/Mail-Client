package pl.bernat.controller;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
import org.w3c.dom.ls.LSOutput;
import pl.bernat.EmailManager;
import pl.bernat.controller.services.MessageRendererService;
import pl.bernat.model.EmailMessage;
import pl.bernat.view.ViewFactory;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;
import java.awt.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmailDetailsController extends BaseController implements Initializable {
    private String LOCATION_OF_DOWNLOAD = "C:\\Users\\X1 Carbon 5th\\Downloads\\";
    @FXML
    private Label attachementsLabel;
    @FXML
    private HBox hBoxDownloads;

    @FXML
    private Label senderLabel;

    @FXML
    private Label subjectLabel;

    @FXML
    private WebView webView;

    public EmailDetailsController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EmailMessage emailMessage = emailManager.getSelectedMessage();
        subjectLabel.setText(emailMessage.getSubject());
        senderLabel.setText(emailMessage.getSender());
        loadAttachments(emailMessage);


        MessageRendererService messageRendererService = new MessageRendererService(webView.getEngine());
        messageRendererService.setEmailMessage(emailMessage);
        messageRendererService.restart();
    }

    private void loadAttachments(EmailMessage emailMessage){
        if(emailMessage.hasAttachments()){
            for(MimeBodyPart mimeBodyPart: emailMessage.getAttachmentList()){
                try {
                    AttachementButton button = new AttachementButton(mimeBodyPart);
                    hBoxDownloads.getChildren().add(button);
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            attachementsLabel.setText("");
        }
    }
    private class AttachementButton extends Button{
        private String fileName;
        private  MimeBodyPart mimeBodyPart;
        private String downloadedFilePath;

        public AttachementButton(MimeBodyPart mimeBodyPart) throws MessagingException, UnsupportedEncodingException {
            this.mimeBodyPart = mimeBodyPart;
            this.fileName = MimeUtility.decodeText(mimeBodyPart.getFileName());
            this.setText(fileName);
            this.downloadedFilePath = LOCATION_OF_DOWNLOAD + fileName;

            this.setOnAction(e -> downloadAttachement());
        }

        private void downloadAttachement(){
            colorBlue();
            Service service = new Service() {
                @Override
                protected Task createTask() {
                    return new Task() {
                        @Override
                        protected Object call() throws Exception {
                            mimeBodyPart.saveFile(downloadedFilePath);
                            return null;
                        }
                    };
                }
            };
            service.restart();
            service.setOnSucceeded(e -> {
                    colorGreen();
                    this.setOnAction(e2 -> {
                        File file = new File(downloadedFilePath);
                        Desktop desktop = Desktop.getDesktop();
                        if(file.exists()){
                            try {
                                desktop.open(file);
                            } catch (Exception exc){
                                exc.printStackTrace();
                            }
                        }
                    });
            });
        }
        private void colorBlue(){
                this.setStyle("-fx-background-color: Blue");
        }
        private void colorGreen(){
            this.setStyle("-fx-background-color: Green");
        }
    }
}