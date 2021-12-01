package P9.controller;

import P9.Main;
import P9.model.ContentBlock;
import P9.model.DisplayContentBlock;
import P9.model.ImageBlock;
import P9.model.TextBlock;
import P9.persistence.ContentBlockDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ContentsSubPageController implements Initializable {

    // References to other controllers
    private TextEditorController textEditorController;
    private MainPageController mainPageController;
    private PlaceholdersSubPageController placeholdersSubPageController;

    // Attribute to hold the secondary stage for the "Register new Content Block" window
    //private Stage registerNewCBlockStage;

    @FXML private TableView<DisplayContentBlock> contentBlockTableView;
    @FXML private TableColumn<DisplayContentBlock, String> cBlockNameColumn;
    @FXML private TableColumn<DisplayContentBlock, String> insertCBlockButton;
    @FXML public ComboBox<ContentBlock> cbEdit;

    // TODO Anne: ryd op og gør private, måske ikke instantiation og declaration i samme linje?
    ContentBlockDao cbdao = new ContentBlockDao();
    ObservableList<ContentBlock> cbList = FXCollections.observableArrayList(cbdao.listAll());
    ObservableList<DisplayContentBlock> displayCB = FXCollections.observableArrayList();
    private TextArea text;
    private boolean newCB = false;


    public void setText(TextArea text) {
        this.text = text;
    }

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

    public boolean isNewCB() {
        return newCB;
    }

    public void setNewCB(boolean newCB) {
        this.newCB = newCB;
    }

    /**
     * Populates the listview in the GUI with all the ContentBlocks in the DB.
     * Also adds a corresponding button that inserts the ContentBlock into the TextArea.
     */
    public void makeContentBlockList(){

        cbList = FXCollections.observableArrayList(cbdao.listAll());
        contentBlockTableView.getItems().clear();

        //Iterating over the size of the ContentBlock list
        for (int i=0;i<cbList.size();i++){
            //Creating a button and setting width
            Button button = new Button("⬅");
            button.setPrefWidth(65);
            //Creating a variable to get the correct object in the cbList
            int finalI1 = i;
            //Setting actionEvent on the button
            button.setOnAction(actionEvent -> {
                    //If the current object in the cbList is a TextBlock this is executed
                    if(cbList.get(finalI1) instanceof TextBlock){
                        //Inserting the text at the caret position
                        text.insertText(getCaretPosition(), ((TextBlock) cbList.get(finalI1)).getTxt());
                    }
                    else {
                        //Inserting the image at the caret position
                        text.insertText(getCaretPosition(), ((ImageBlock) cbList.get(finalI1)).getImagePath());
                    }
                    });

            //Adds the object and the button to the displayCB list
            displayCB.add(new DisplayContentBlock(cbList.get(i), button));
        }

        cBlockNameColumn.setCellValueFactory(cb -> cb.getValue().getContentBlock().nameProperty());
        insertCBlockButton.setCellValueFactory(new PropertyValueFactory<>("Button"));

        contentBlockTableView.getItems().addAll(displayCB);
        cbList.clear();
        displayCB.clear();
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

    /**
     * Gets caret position from TextEditorController
     * @return Returns caret position
     */
    public int getCaretPosition(){
        return textEditorController.getTextArea().getCaretPosition();
    }

    /**
     * Populates the combobox used to display all the ContentBlocks that the user can edit
     */
    public void populateBox(){
        List<ContentBlock> cbList;
        cbList = cbdao.listAll();
        ObservableList<ContentBlock> ocbList = FXCollections.observableArrayList(cbList);
        cbEdit.setItems(ocbList);
        //Creates a StringConverter that allows us to display the name of the content block in the GUI (toString)
        //and find the Content Block again when the user selects a name String in the GUI (fromString)
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

    /**
     * Called when user clicks on a ContentBlock they want to edit in the GUI
     */
    public void editContentBlock() {
        createOrEditContentBlock();
        //If the ContentBlock they clicked on is a TextBlock this is executed
        if(cbEdit.getValue() instanceof TextBlock txtBlock) {
            text.setText(txtBlock.getTxt());
        }
        else {
            ImageBlock img = (ImageBlock) cbEdit.getValue();
            text.setText(img.getImagePath());
        }
    }

    /**
     * Changes a number of things in the GUI to show user they are creating a ContentBlock
     * Also removes and changes certain functionality
     */
    public void createOrEditContentBlock() {
        textEditorController.setCreatingDoc(false);
        text.clear();
        mainPageController.eObjectLabel.setText("You are creating a Content Block");
        textEditorController.returnButton.setVisible(true);
        placeholdersSubPageController.callLabelsNull();


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

    /**
     * Called when user clicks "create new ContentBlock" in the GUI
     */
    public void createNewContentBlock() {
        createOrEditContentBlock();
        //Sets boolean which is used to check if a new ContentBlock is being created to true
        newCB = true;
    }

    /**
     * Method that gets references to other controllers
     * to be able to pass data between them
     */
    public void setControllers(){
        this.textEditorController = Main.getTextEditorController();
        this.mainPageController = Main.getMainPageController();
        this.placeholdersSubPageController = Main.getPlaceholdersSubPageController();
    }
}




