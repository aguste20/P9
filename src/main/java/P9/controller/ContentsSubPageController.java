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

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

//TODO Anne/cleanup: Mangler dokumentation

public class ContentsSubPageController implements Initializable {

    // ----- Properties -----
    // References to other controllers
    private TextEditorController textEditorController;
    private MainPageController mainPageController;
    private PlaceholdersSubPageController placeholdersSubPageController;
    private OverviewSubPageController overviewSubPageController;

    // FXML elements
    @FXML private TableView<DisplayContentBlock> contentBlockTableView;
    @FXML private TableColumn<DisplayContentBlock, String> cBlockNameColumn;
    @FXML private TableColumn<DisplayContentBlock, String> insertCBlockButton;
    @FXML private TableColumn<DisplayContentBlock, String> editCBColumn;
    @FXML private ComboBox<ContentBlock> cbEdit; // TODO kan denne slettes?

    // Local DAO instance
    private final ContentBlockDao cbdao = new ContentBlockDao();

    private ObservableList<ContentBlock> cbList; // List of content blocks from the database
    private ObservableList<DisplayContentBlock> displayCB; // List of content blocks to display
    private TextArea text; // Reference to the text area in text editor
    private boolean newCB; // Is the user currently creating a new content block?
    private ContentBlock selectedCB; // Selected content block from content block list
    private String txt;


    // ----- Getters and setters -----
    public ContentBlock getSelectedCB() {
        return selectedCB;
    }
    public boolean isNewCB() {
        return newCB;
    }
    public void setText(TextArea text) {
        this.text = text;
    }
    public void setNewCB(boolean newCB) {
        this.newCB = newCB;
    }

    /**
     * Method that sets references to other controllers
     * to be able to pass data between them
     */
    public void setControllers(){
        this.textEditorController = Main.getTextEditorController();
        this.mainPageController = Main.getMainPageController();
        this.placeholdersSubPageController = Main.getPlaceholdersSubPageController();
        this.overviewSubPageController = Main.getOverviewSubPageController();
    }

    /**
     * Method that initializes a controller object after its root element has been loaded.
     * Inherited from Initializable.
     * Called by the fxmlloader.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        cbList = FXCollections.observableArrayList(cbdao.listAll());
        displayCB = FXCollections.observableArrayList();
        newCB = false;
    }

    // ----- Instance methods -----
    /**
     * Populates the listview in the GUI with all the ContentBlocks in the DB.
     * Also adds a corresponding button that inserts the ContentBlock into the TextArea.
     */
    public void makeContentBlockList(){
        cbList.clear();
        displayCB.clear();
        contentBlockTableView.getItems().clear();

        cbList = FXCollections.observableArrayList(cbdao.listAll());

        //Iterating over the size of the ContentBlock list
        for (int i=0;i<cbList.size();i++){
            //Creating a button and setting width
            Button insertBtn = new Button("⬅");
            Button editBtn = new Button("Edit");
            insertBtn.setPrefWidth(65);
            //Creating a variable to get the correct object in the cbList
            int finalI1 = i;
            //Setting actionEvent on the button
            insertBtn.setOnAction(actionEvent -> {
                    //If the current object in the cbList is a TextBlock this is executed
                    if(cbList.get(finalI1) instanceof TextBlock){
                        if (textEditorController.isSourceTextActive()) {
                            //Inserting the text at the caret position
                            text.insertText(getCaretPosition(), ((TextBlock) cbList.get(finalI1)).getTxt());
                        }
                        else {txt = ((TextBlock) cbList.get(finalI1)).getTxt().lines().collect(Collectors.joining(" ")); insertContentBlockInHTML(txt);}
                    }
                    else {
                        if (textEditorController.isSourceTextActive()) {
                            //Inserting the image at the caret position
                            text.insertText(getCaretPosition(), "</xsl:text><p><img src=\"" +
                                    ((ImageBlock) cbList.get(finalI1)).getImagePath() + "\" width=\"500\"/></p><xsl:text>");
                        }
                        else {txt = ((ImageBlock) cbList.get(finalI1)).getImagePath().lines().collect(Collectors.joining(" "));
                            placeholdersSubPageController.insertImage(txt);}
                    }
                    });

            editBtn.setOnAction(actionEvent -> {
                selectedCB = cbList.get(finalI1);
                editContentBlock();
            });

            //Adds the object and the button to the displayCB list
            displayCB.add(new DisplayContentBlock(cbList.get(i), insertBtn, editBtn));
        }

        cBlockNameColumn.setCellValueFactory(cb -> cb.getValue().getContentBlock().nameProperty());
        insertCBlockButton.setCellValueFactory(new PropertyValueFactory<>("button"));
        editCBColumn.setCellValueFactory(new PropertyValueFactory<>("editBtn"));


        contentBlockTableView.getItems().addAll(displayCB);

    }

    // ----- Private instance methods -----
    // TODO Anne/cleanup: Mangler dokumentation
    private void insertContentBlockInHTML(String cb) {
        Main.getEngine().executeScript("var range = window.getSelection().getRangeAt(0);" +
                "var selectionContents = range.extractContents();" +
                "var span = document.createElement(\"span\");" +
                "span.style.backgroundColor = \"" + "transparent" + "\";" +
                "span.textContent = \"" + cb + "\";" +
                "span.appendChild(selectionContents);" +
                "range.insertNode(span);");
    }

    /**
     * Gets caret position from TextEditorController
     * @return Returns caret position
     */
    private int getCaretPosition(){
        return textEditorController.getTextArea().getCaretPosition();
    }

    /**
     * Called when user clicks on a ContentBlock they want to edit in the GUI
     */
    private void editContentBlock() {
        createOrEditContentBlock();
        //If the ContentBlock they clicked on is a TextBlock this is executed
        if(selectedCB instanceof TextBlock txtBlock) {
            text.setText(txtBlock.getTxt());
        }
        else {
            ImageBlock img = (ImageBlock) selectedCB;
            text.setText(img.getImagePath());
        }
        overviewSubPageController.updateToc();
    }

    /**
     * Changes a number of things in the GUI to show user they are creating a ContentBlock
     * Also removes and changes certain functionality
     */
    private void createOrEditContentBlock() {
        if(!textEditorController.isSourceTextActive()){
            mainPageController.switchToTextEditorPage();
        }
        // Make eobject choice combo box invisble in main page
        mainPageController.geteObjectChoice().setVisible(false);

        textEditorController.setCreatingDoc(false);
        text.clear();
        mainPageController.geteObjectLabel().setText("You are creating a Content Block");
        textEditorController.getReturnButton().setVisible(true);
        placeholdersSubPageController.callLabelsVisible();
    }

    /**
     * Called when user clicks "create new ContentBlock" in the GUI
     */
    @FXML private void createNewContentBlock() {
        createOrEditContentBlock();
        //Sets boolean which is used to check if a new ContentBlock is being created to true
        newCB = true;
        //mainPageController.setCheckedPreview(false);
        overviewSubPageController.updateToc();
    }
}




