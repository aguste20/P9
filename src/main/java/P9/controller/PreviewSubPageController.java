package P9.controller;

import P9.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class PreviewSubPageController implements Initializable {

    @FXML
    public GridPane webGridPane;

    //private WebEngine engine;

    public GridPane getWebGridPane() { return webGridPane; }

    /**
     * This method initializes a controller after its root element has already been processed.
     * I think this means that this method is needed to keep content in the view pages updated visually.
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
    }

    public void createXslFromTextArea(){

        // Get textarea string

        // Create string with xsl document tag

        // Create string with start tag

        // Create end tag

        // Write to file with string


    }

}
