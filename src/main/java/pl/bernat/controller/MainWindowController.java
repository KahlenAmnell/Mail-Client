package pl.bernat.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;
import pl.bernat.EmailManager;
import pl.bernat.view.ViewFactory;

public class MainWindowController extends BaseController {
        @FXML
        private WebView emailWebView;

        @FXML
        private TableView<?> emailsTableView;

        @FXML
        private TreeView<?> emailsTreeView;

        public MainWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
                super(emailManager, viewFactory, fxmlName);
        }

        @FXML
        void optionsAction() {
              viewFactory.showOptionsWindow();
        }
}
