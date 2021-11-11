package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainPageController {

    @FXML
    private BorderPane paneTextEditor;

    public BorderPane getPaneTextEditor() {
        return paneTextEditor;
    }

}
