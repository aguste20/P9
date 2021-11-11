package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainPageController {

    @FXML
    private ScrollPane paneTextEditor;


    public ScrollPane getPaneTextEditor() {
        return paneTextEditor;
    }

}
