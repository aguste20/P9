package P9.controller;


import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
//import jdk.jfr.Event;
import javafx.event.Event;

import java.io.IOException;



public class MainPageController {

    //We annotate the containers of the mainPage.fxml
    @FXML
    private ScrollPane paneTextEditor;

    @FXML
    private ScrollPane paneOverviewSubPage;

    @FXML
    private ScrollPane paneContentsPlaceholders;

    TextArea textArea;

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }

    public ScrollPane getPaneTextEditor() {
        return paneTextEditor;
    }

    public ScrollPane getPaneOverviewSubPage() { return paneOverviewSubPage; }

    public ScrollPane getPaneContentsPlaceholders() { return paneContentsPlaceholders; }

    public void getSelected(){
            System.out.println("selected text:"
                    + textArea.getSelectedText());
    }
}
