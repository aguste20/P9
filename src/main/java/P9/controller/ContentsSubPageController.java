package P9.controller;

import P9.Main;
import P9.model.ContentBlock;
import P9.persistence.ContentBlockDao;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ContentsSubPageController implements Initializable {

    //Configuring tableView table:
    @FXML
    private TableView<Object> CBtableView;
    //Creating each column, telling which parent and input datatype it has.
    @FXML private TableColumn<ContentBlock, String> CBnameTableColumn;
    @FXML private TableColumn<Object, Button> CBinsertButtonTableColumn;

    /**
     * Method for creating and showing content blocks and related buttons in the view
     */
    public void makeContentBlockList(){
        //Get a list of all contentblocks from the databse
        ContentBlockDao dao = new ContentBlockDao();
        List<ContentBlock> existingContentBlocksList = dao.listAll();

        //For each ContentBlock in list, we take the name and add it to the name column of our TableView
        for (int i = 0; i < existingContentBlocksList.size(); i++){
            //System.out.println(contentBlockList.get(i));
            ContentBlock contentBlock = existingContentBlocksList.get(i);
            //Label label = new Label(contentBlock.getName());
            Button button = new Button("(>)");

            CBtableView.getItems().add(contentBlock);
            //*****************************************************************//
        }

        Callback<TableColumn<Object, Button>, TableCell<Object, Button>> cellFactory = new Callback<TableColumn<Object, Button>, TableCell<Object, Button>>() {
            @Override
            public TableCell<Object, Button> call(final TableColumn<Object, Button> param) {
                final TableCell<Object, Button> cell = new TableCell<Object, Button>() {

                    private final Button btn = new Button("Action");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            //Data data = getTableView().getItems().get(getIndex());
                            System.out.println("Gaming?");
                        });
                    }

                    @Override
                    public void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        CBinsertButtonTableColumn.setCellFactory(cellFactory);

        //CBtableView.getColumns().add(CBinsertButtonTableColumn);
        CBtableView.getItems().add(CBinsertButtonTableColumn);

    }


    /**
     * This method initializes a controller after its root element has already been processed.
     * I think this means that this method is needed to keep content in the view pages updated visually.
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        CBnameTableColumn.setCellValueFactory(new PropertyValueFactory<ContentBlock, String>("name"));

        makeContentBlockList();

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



