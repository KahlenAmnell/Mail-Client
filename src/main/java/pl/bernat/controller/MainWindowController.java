package pl.bernat.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import pl.bernat.EmailManager;
import pl.bernat.controller.services.MessageRendererService;
import pl.bernat.model.EmailMessage;
import pl.bernat.model.EmailTreeItem;
import pl.bernat.model.SizeInteger;
import pl.bernat.view.ViewFactory;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {
        private MenuItem markUnreadMenuItem = new MenuItem("Mark as unread");
        private MenuItem deleteMessageMenuItem = new MenuItem("Delete message");
        private MenuItem showMessageDetailsMenuItem = new MenuItem("Show details");
        @FXML
        private WebView emailWebView;

        @FXML
        private TableView<EmailMessage> emailsTableView;
        @FXML
        private TableColumn<EmailMessage, String> recipientCol;
        @FXML
        private TableColumn<EmailMessage, String> senderCol;
        @FXML
        private TableColumn<EmailMessage, SizeInteger> sizeCol;
        @FXML
        private TableColumn<EmailMessage, String> subjectCol;
        @FXML
        private TableColumn<EmailMessage, Date> dateCol;
        private MessageRendererService messageRendererService;

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

        @FXML
        void composeMessageAction() {
                viewFactory.showComposeMessageWindow();
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                setUpEmailsTreeView();
                setUpEmailsTableView();
                setUpFolderSelection();
                setUpBoldRows();
                setUpMessageRendererService();
                setUpMessageSelection();
                setUpContextMenus();
        }

        private void setUpContextMenus() {
                markUnreadMenuItem.setOnAction(event -> {
                        emailManager.setUnread();
                        }
                );
                deleteMessageMenuItem.setOnAction(event -> {
                        emailManager.deleteSelectedMessage();
                        emailWebView.getEngine().loadContent("");
                });
                showMessageDetailsMenuItem.setOnAction(event -> {
                        viewFactory.showEmailDetailsWindow();
                });
        }

        private void setUpMessageSelection() {
                emailsTableView.setOnMouseClicked(event -> {
                        EmailMessage emailMessage = emailsTableView.getSelectionModel().getSelectedItem();
                        if(emailMessage != null){
                                emailManager.setSelectedMessage(emailMessage);
                                if(!emailMessage.isRead()){
                                        emailManager.setRead();
                                }
                                emailManager.setSelectedMessage(emailMessage);
                                messageRendererService.setEmailMessage(emailMessage);
                                messageRendererService.restart();
                        }
                });
        }

        private void setUpMessageRendererService() {
                messageRendererService = new MessageRendererService(emailWebView.getEngine());
        }

        private void setUpBoldRows() {
                emailsTableView.setRowFactory(new Callback<TableView<EmailMessage>, TableRow<EmailMessage>>() {
                        @Override
                        public TableRow<EmailMessage> call(TableView<EmailMessage> emailMessageTableView) {
                                return new TableRow<EmailMessage>(){
                                        @Override
                                        protected void updateItem(EmailMessage item, boolean empty){
                                                super.updateItem(item, empty);
                                                if(item != null){
                                                        if(item.isRead()){
                                                                setStyle("");
                                                        } else {
                                                                setStyle("-fx-font-weight: bold");
                                                        }
                                                }
                                        }
                                };
                        }
                });
        }

        private void setUpFolderSelection() {
                emailsTreeView.setOnMouseClicked(e->{
                        EmailTreeItem<String> item = (EmailTreeItem<String>) emailsTreeView.getSelectionModel().getSelectedItem();
                        if(item != null) {
                                emailManager.setSelectedFolder(item);
                                emailsTableView.setItems(item.getEmailMessages());
                        }
                });
        }

        private void setUpEmailsTableView() {
                senderCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("sender"));
                subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("subject"));
                recipientCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("recipient"));
                sizeCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, SizeInteger>("size"));
                dateCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, Date>("date"));

                emailsTableView.setContextMenu(new ContextMenu(markUnreadMenuItem, deleteMessageMenuItem, showMessageDetailsMenuItem));
        }

        private void setUpEmailsTreeView() {
                emailsTreeView.setRoot(emailManager.getFoldersRoot());
                emailsTreeView.setShowRoot(false);
        }
}
