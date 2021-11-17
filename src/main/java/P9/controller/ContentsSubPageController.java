package P9.controller;

import P9.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ContentsSubPageController implements Initializable {

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



    // Attribute to hold the secondary stage for the "Register new Content Block" window
    private Stage registerNewCBlockStage;

    /**
     * Event handler for the button "Registrer ny vare"
     * Opens a modal window to enter details about the product and save in database
     * Contents based on: https://www.codota.com/code/java/methods/javafx.stage.Stage/initModality
     * Last visited: April 22th 2021.
     *
     * @param event action event from button element
     */
    public void openRegisterNewContentBlock(ActionEvent event) {
        // If register new content block window hasn't been opened before
        if (registerNewCBlockStage == null) {
            // Create new stage, set scene with fxml root, set title
            Stage stage = new Stage();
            stage.setScene(new Scene(Main.getRegisterNewContentBlockPageParent()));
            stage.setTitle("Register a new Content Block");


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
                    registerNewCBlockStage.close();
                }
            });

            // If stage already exists, update reference to the stage
            registerNewCBlockStage = stage;
        }

        // Show stage
        registerNewCBlockStage.show();
    }



}



