package pl.bernat.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;
import pl.bernat.EmailManager;
import pl.bernat.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {
        @FXML
        private WebView emailWebView;

        @FXML
        private TableView<?> emailsTableView;

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
        }

        private void setUpEmailsTreeView() {
                emailsTreeView.setRoot(emailManager.getFoldersRoot());
                emailsTreeView.setShowRoot(false);
        }
}
