package P9.controller;

import P9.Main;
import P9.model.ContentBlock;
import P9.model.DisplayContentBlock;
import P9.model.ImageBlock;
import P9.model.TextBlock;
import P9.persistence.ContentBlockDao;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ContentsSubPageController implements Initializable {

    // Attribute to hold the secondary stage for the "Register new Content Block" window
    //private Stage registerNewCBlockStage;

    @FXML private TableView<DisplayContentBlock> contentBlockTableView;
    @FXML private TableColumn<DisplayContentBlock, String> cBlockNameColumn;
    @FXML private TableColumn<DisplayContentBlock, String> insertCBlockButton;
    @FXML public ComboBox<ContentBlock> cbEdit;

    ContentBlockDao cbdao = new ContentBlockDao();
    ObservableList<ContentBlock> cbList = FXCollections.observableArrayList(cbdao.listAll());
    ObservableList<DisplayContentBlock> displayCB = FXCollections.observableArrayList();
    TextArea text = Main.getTextEditorController().getTextArea();


    /**
     * This method initializes a controller after its root element has already been processed.
     * I think this means that this method is needed to keep content in the view pages updated visually.
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){

        //CBlockNameColumnContentsSubPage.setCellValueFactory(new PropertyValueFactory("name"));

        //ContentBlockTableViewContentsSubPage.setPlaceholder(new Text("No content blocks currently exists. Use the 'Create new content blcok'-button to create new block."));


        makeContentBlockList();

        populateBox();

    }

    //Method that will generate the list of content blocks and show them in the view.
    public void makeContentBlockList(){

        for (int i=0;i<cbList.size();i++){
            Button button = new Button("⬅");
            button.setPrefWidth(65);
            int finalI1 = i;
            button.setOnAction(actionEvent -> {
                    if(cbList.get(finalI1) instanceof TextBlock){
                    text.insertText(getCaretPosition(), ((TextBlock) cbList.get(finalI1)).getTxt());
                    }
                    else {
                        text.insertText(getCaretPosition(), ((ImageBlock) cbList.get(finalI1)).getImagePath());
                    }
                    });

            displayCB.add(new DisplayContentBlock(cbList.get(i), button));
        }

        cBlockNameColumn.setCellValueFactory(cb -> cb.getValue().getContentBlock().nameProperty());
        insertCBlockButton.setCellValueFactory(new PropertyValueFactory<>("Button"));

        contentBlockTableView.getItems().addAll(displayCB);
        /*
        //List of ALL (Both the content blocks and the buttons)
        //ArrayList tableViewContentsList = (ArrayList) ContentBlockTableViewContentsSubPage.getItems();

        //Empty list meant to hold the content blocks we create when we load them from the database
        //List contentBlockList;

        //Empty list meant to hold the buttons we create for each loaded content block from the database
        //ArrayList contentButtonList = new ArrayList();

        for (int i = 0; i < contentBlockList.size(); i++){
            //System.out.println(contentBlockList.get(i));
            ContentBlock contentBlock = contentBlockList.get(i);
            //Label label = new Label(contentBlock.getName());
            Button button = new Button("(>)" + i);
            //TableView<String> test = new TableView(FXCollections.observableList(new ArrayList<String>().add(contentButtonList.get(i).toString())));

            //observableNameList.add(contentBlock.getName());
            contentBlockList.add(contentBlock);
            contentButtonList.add(button);


            //ContentBlockTableViewContentsSubPage.getItems().add(button);

        }


        Callback<TableColumn<Button, Void>, TableCell<Button, Void>> cellFactory = new Callback<TableColumn<Button, Void>, TableCell<Button, Void>>() {
            @Override
            public TableCell<Button, Void> call(final TableColumn<Button, Void> param) {
                final TableCell<Button, Void> cell = new TableCell<Button, Void>() {

                    private final Button btn = new Button("Action");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            //Button data = getTableView().getItems().get(getIndex());
                            System.out.println("Gaming?");
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
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

        InsertCBlockButtonContentsSubPage.setCellFactory(cellFactory);

        //ContentBlockTableViewContentsSubPage.getColumns().add(InsertCBlockButtonContentsSubPage);


        ContentBlockTableViewContentsSubPage.getColumns().add(CBlockNameColumnContentsSubPage);
        ContentBlockTableViewContentsSubPage.getColumns().add(InsertCBlockButtonContentsSubPage);

*/
    }

    public int getCaretPosition(){
        return Main.getTextEditorController().getTextArea().getCaretPosition();
    }

    public void populateBox(){
        List<ContentBlock> cbList;
        cbList = cbdao.listAll();
        ObservableList<ContentBlock> ocbList = FXCollections.observableArrayList(cbList);
        cbEdit.setItems(ocbList);
        cbEdit.setConverter(new StringConverter<ContentBlock>() {
            @Override
            public String toString(ContentBlock contentBlock) {
                return contentBlock.getName();
            }

            @Override
            public ContentBlock fromString(String s) {
                return cbEdit.getItems().stream().filter(
                        cb -> cb.getName().equals(s)).findFirst().orElse(null);
            }
        });
    }

    public void editContentBlock() {
        openRegisterNewContentBlock();
        if(cbEdit.getValue() instanceof TextBlock txtBlock) {
            text.setText(txtBlock.getTxt());
        }
        else {
            ImageBlock img = (ImageBlock) cbEdit.getValue();
            text.setText(img.getImagePath());
        }
    }

    /**
     * Event handler for the button "Registrer ny vare"
     * Opens a modal window to enter details about the product and save in database
     * Contents based on: https://www.codota.com/code/java/methods/javafx.stage.Stage/initModality
     * Last visited: April 22th 2021.
     */

    public void openRegisterNewContentBlock() {
        Main.getTextEditorController().setCreatingDoc(false);
        text.clear();
        Main.getMainPageController().eObjectLabel.setText("You are creating a Content Block");
        Main.getTextEditorController().returnButton.setVisible(true);
        Main.getPlaceholdersSubPageController().callLabelsNull();



        /*
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

         */
    }

}




