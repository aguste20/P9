package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainPageController {

    //We annotate the containers of the mainPage.fxml
    @FXML
    private ScrollPane paneTextEditor;

    @FXML
    private ScrollPane paneOverviewSubPage;

    @FXML
    private ScrollPane paneContentsPlaceholders;



    public ScrollPane getPaneTextEditor() {
        return paneTextEditor;
    }

    public ScrollPane getPaneOverviewSubPage() { return paneOverviewSubPage; }

    public ScrollPane getPaneContentsPlaceholders() { return paneContentsPlaceholders; }
}
