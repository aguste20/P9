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
import java.util.stream.Collectors;

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

    public String txt;

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
            Button button = new Button("⬅");
            button.setPrefWidth(65);
            //Creating a variable to get the correct object in the cbList
            int finalI1 = i;
            //Setting actionEvent on the button
            button.setOnAction(actionEvent -> {
                    //If the current object in the cbList is a TextBlock this is executed
                    if(cbList.get(finalI1) instanceof TextBlock){
                        if (textEditorController.isTextEditorActive()) {
                            //Inserting the text at the caret position
                            text.insertText(getCaretPosition(), ((TextBlock) cbList.get(finalI1)).getTxt());
                        }
                        else {txt = ((TextBlock) cbList.get(finalI1)).getTxt().lines().collect(Collectors.joining(" ")); insertContentBlockInHTML(txt);}
                    }
                    else {
                        if (textEditorController.isTextEditorActive()) {
                            //Inserting the image at the caret position
                            text.insertText(getCaretPosition(), ((ImageBlock) cbList.get(finalI1)).getImagePath());
                        }
                        else {txt = ((ImageBlock) cbList.get(finalI1)).getImagePath().lines().collect(Collectors.joining(" ")); insertContentBlockInHTML(txt);}
                    }
                    });

            //Adds the object and the button to the displayCB list
            displayCB.add(new DisplayContentBlock(cbList.get(i), button));
        }

        cBlockNameColumn.setCellValueFactory(cb -> cb.getValue().getContentBlock().nameProperty());
        insertCBlockButton.setCellValueFactory(new PropertyValueFactory<>("Button"));

        contentBlockTableView.getItems().addAll(displayCB);

    }

    public void insertContentBlockInHTML(String cb) {
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
        placeholdersSubPageController.callLabelsVisible();

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




