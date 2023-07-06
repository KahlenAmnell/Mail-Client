package pl.bernat.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import pl.bernat.EmailManager;
import pl.bernat.model.EmailMessage;
import pl.bernat.model.EmailTreeItem;
import pl.bernat.view.ViewFactory;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {
        @FXML
        private WebView emailWebView;

        @FXML
        private TableView<EmailMessage> emailsTableView;
        @FXML
        private TableColumn<EmailMessage, String> recipientCol;
        @FXML
        private TableColumn<EmailMessage, String> senderCol;
        @FXML
        private TableColumn<EmailMessage, Integer> sizeCol;
        @FXML
        private TableColumn<EmailMessage, String> subjectCol;
        @FXML
        private TableColumn<EmailMessage, Date> dateCol;

        @FXML
        private TreeView<String> emailsTreeView;

        public MainWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
                super(emailManager, viewFactory, fxmlName);
        }
        @FXML
        void addAcountAction() {
                viewFactory.showLoginWindow();
        }

        @FXML
        void optionsAction() {
              viewFactory.showOptionsWindow();
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                setUpEmailsTreeView();
                setUpEmailsTableView();
                setUpFolderSelection();
        }

        private void setUpFolderSelection() {
                emailsTreeView.setOnMouseClicked(e->{
                        EmailTreeItem<String> item = (EmailTreeItem<String>) emailsTreeView.getSelectionModel().getSelectedItem();
                        if(item != null) {
                                emailsTableView.setItems(item.getEmailMessages());
                        }
                });
        }

        private void setUpEmailsTableView() {
                senderCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("sender"));
                subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("subject"));
                recipientCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("recipient"));
                sizeCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, Integer>("size"));
                dateCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, Date>("data"));

        }

        private void setUpEmailsTreeView() {
                emailsTreeView.setRoot(emailManager.getFoldersRoot());
                emailsTreeView.setShowRoot(false);
        }
}
