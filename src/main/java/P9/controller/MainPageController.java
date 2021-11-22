package P9.controller;

import P9.Main;
import P9.model.EObject;
import P9.persistence.EObjectDao;
import P9.persistence.UserDao;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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

        // If eObject has no doc, create one for it
        if (eObject.getDoc() == null){
            eObject.createNewDoc();
        }
    }

    // Attribute to hold the secondary stage for the "Register new Content Block" window
    private Stage newWebView;

    /*
    public void openPreview(ActionEvent event) {
        // If register new content block window hasn't been opened before
        if (newWebView == null) {
            // Create new stage, set scene with fxml root, set title
            Stage stage = new Stage();
            stage.setScene(new Scene(Main.getPreviewSubPageParent()));
            stage.setTitle("Register a new preview");


            //https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html#initModality-javafx.stage.Modality-
            //Set stage to have the modality of WINDOW_MODAL.
            //The stage blocks input events from being delivered to all windows from its owner (parent) to its root. Its root is the closest ancestor window without an owner.
            stage.initModality(Modality.WINDOW_MODAL);
            //initOwner specifies the owner Window for this stage. In this case we set dataInsertionPage to be the owner.
            //This one 'locks' the user to the window, so they can't click elsewhere.
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());

            //When the user tries to close the window
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    //TODO: Specify here what we want to happen when the window is closed.
                    // An example is, that we want to update the original list of content blocks.

                    //Close stage/window
                    newWebView.close();
                }
            });

            // If stage already exists, update reference to the stage
            newWebView = stage;
        }

        // Show stage
        newWebView.show();
    }

     */


    /**
     * Methods for changing the contents of the middle pane of the mainPage.fxml.
     * When user presses one of the buttons, the interface shows the associated viewfile.
     * @param event action from button element
     */
    public void switchToPlaceholdersSubPage (ActionEvent event){
        paneContentsPlaceholders.setContent(Main.getPlaceholdersSubPageParent());
    }

    public void switchToPreviewSubPage (ActionEvent event){
        Main.getPreviewSubPageController().getWebGridPane().add(Main.getWebview(), 0 , 0);
        paneTextEditor.setContent(Main.getPreviewSubPageParent());
    }

    public void switchToContentsSubPage (ActionEvent event){
        paneContentsPlaceholders.setContent(Main.getContentsSubPageParent());
    }

}


