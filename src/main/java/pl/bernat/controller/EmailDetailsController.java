package pl.bernat.controller;

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
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmailDetailsController extends BaseController implements Initializable {
    private String LOCATION_OF_DOWNLOAD = System.getProperty("user.home") + "/Downloads/";
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

                    Button button = new Button(MimeUtility.decodeText(mimeBodyPart.getFileName()));
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
}