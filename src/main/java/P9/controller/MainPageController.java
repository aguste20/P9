package P9.controller;


import P9.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable{

    //We annotate the containers of the mainPage.fxml
    @FXML private ScrollPane paneTextEditor;
    @FXML private ScrollPane paneOverviewSubPage;
    @FXML private ScrollPane paneContentsPlaceholders;



    // ---- Getters ----
    // Returns the containers of the mainPage.fxml
    public ScrollPane getPaneTextEditor() { return paneTextEditor; }
    public ScrollPane getPaneOverviewSubPage() { return paneOverviewSubPage; }
    public ScrollPane getPaneContentsPlaceholders() { return paneContentsPlaceholders; }


    /**
     * This method initializes a controller after its root element has already been processed.
     * I think this means that this method is needed to keep content in the view pages updated visually.
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        //Might have to move some of the content that is outside this method, in to this method, in order to keep the interface updated. - Bjørn
    }



    /**
     * Methods for changing the contents of the middle pane of the mainPage.fxml.
     * When user presses one of the buttons, the interface shows the associated viewfile.
     * @param event action from button element
     */
    public void switchToPlaceholdersSubPage (ActionEvent event){
        paneContentsPlaceholders.setContent(Main.getPlaceholdersSubPageParent());
    }
    public void switchToContentsSubPage (ActionEvent event){
        paneContentsPlaceholders.setContent(Main.getContentsSubPageParent());
    }

}


