package P9.controller;

import P9.Main;
import P9.model.ContentBlock;
import P9.model.TextBlock;
import P9.persistence.ContentBlockDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ContentsSubPageController implements Initializable {


    @FXML private TableView<ContentBlock> TableViewCBlocksContentsSubPage;
    @FXML private TableColumn<TextBlock, String> CBlockNameColumn;
    @FXML private TableColumn<Button, Button> CBlockInsertButtonColumn;



    /**
     * This method initializes a controller after its root element has already been processed.
     * I think this means that this method is needed to keep content in the view pages updated visually.
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        //Might have to move some of the content that is outside this method, in to this method, in order to keep the interface updated. - Bjørn
        CBlockNameColumn.setCellValueFactory(new PropertyValueFactory<TextBlock, String>("text"));
        CBlockInsertButtonColumn.setCe


        insertCBlocksToList();
    }

    /**
     * Method for inserting each content block into the TableView
     */
    public void insertCBlocksToList() {
        //Get a list of all contentblocks from the databse
        ContentBlockDao dao = new ContentBlockDao();
        List<ContentBlock> contentBlockList = dao.listAll();

        ObservableList<String> observableNameList = FXCollections.observableArrayList("item1", "item2");
        ObservableList<Button> observableButtonList = FXCollections.observableArrayList();

        //System.out.println(contentBlockList);

        //For each ContentBlock in list, we take the name and add it to the name column of our TableView
        for (int i = 0; i < contentBlockList.size(); i++){
            //System.out.println(contentBlockList.get(i));
            ContentBlock contentBlock = contentBlockList.get(i);
            //Label label = new Label(contentBlock.getName());
            Button button = new Button("(>)");

            observableNameList.add(contentBlock.getName());
            TableViewCBlocksContentsSubPage.getItems().add(contentBlock);




            //CBlockNameColumn.setCellValueFactory(new PropertyValueFactory<>(contentBlock.getName()));
            //CBlockInsertButtonColumn.setCellValueFactory();
        }

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



