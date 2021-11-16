package controller;


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



    public ScrollPane getPaneTextEditor() {
        return paneTextEditor;
    }
    public ScrollPane getPaneOverviewSubPage() {
        return paneOverviewSubPage; }

    public ScrollPane getPaneContentsPlaceholders() {
        return paneContentsPlaceholders; }



    @Override
    public void initialize(URL arg0, ResourceBundle arg1){

    }

    /**
     *
     * @param event action from button element
     */




    /*
     public void switchToPlaceholdersSubPage (ActionEvent event){
        paneContentsPlaceholders.setContent();
        // getPaneContentsPlaceholders().setContent(paneContentsPlaceholders);
    }

     public void switchToContentsSubPage (ActionEvent event){
        getPaneContentsPlaceholders().setContent(place);

    }

     */


}


