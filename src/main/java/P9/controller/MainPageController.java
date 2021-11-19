package P9.controller;

import P9.Main;
import P9.model.EObject;
import P9.persistence.EObjectDao;
import P9.persistence.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable{

    //We annotate the containers of the mainPage.fxml
    @FXML private ScrollPane paneTextEditor;
    @FXML private ScrollPane paneOverviewSubPage;
    @FXML private ScrollPane paneContentsPlaceholders;

    // Reference to the engineering object that the user is working on
    EObject eObject;
    // Reference to the DAO for our user.
    UserDao userDAO = new UserDao();

    // ---- Getters ----
    // Returns the containers of the mainPage.fxml
    public ScrollPane getPaneTextEditor() { return paneTextEditor; }
    public ScrollPane getPaneOverviewSubPage() { return paneOverviewSubPage; }
    public ScrollPane getPaneContentsPlaceholders() { return paneContentsPlaceholders; }

    public EObject geteObject() {
        return eObject;
    }
    public UserDao getUserDAO() { return userDAO; }

    /**
     * This method initializes a controller after its root element has already been processed.
     * I think this means that this method is needed to keep content in the view pages updated visually.
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        //Might have to move some of the content that is outside this method, in to this method, in order to keep the interface updated. - Bjørn

        // Load engineering object from database with id = 1
        EObjectDao dao = new EObjectDao();
        //TODO Anne - skal jo ikke være hardcoded i virkelig løsning.
        // Men giver ikke mening at gøre dynamisk lige nu
        eObject = dao.getById(1);
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
        //These 2 lines are a messy solution, but in lack of a better one xd.
        //Before setting our pane to contain the ContentsSubPage, we SOME change
        // to the one currently being showed. We add 0.001 width to the showed pane,
        // so JavaFX registers a change to the content of the Node.
        Scene placeholderScene = Main.getPlaceholdersSubPageParent().getScene();
        placeholderScene.getWindow().setWidth(placeholderScene.getWidth() + 0.001);

        //Make the switch
        paneContentsPlaceholders.setContent(Main.getContentsSubPageParent());

        //TODO: Bjørn efterlod det her. Lortet kan ikke skifte frem og tilbage XD

    }

}


