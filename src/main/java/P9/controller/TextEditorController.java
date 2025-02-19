package P9.controller;

import P9.Main;
import P9.model.*;
import P9.persistence.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Objects from this class controls the TextEditor GUI element.
 * An object of the class is initialised, when textEditor.fxml is loaded
 */

public class TextEditorController implements Initializable {
    // ----- Properties -----
    // References to other controllers
    private ContentsSubPageController contentsSubPageController;
    private MainPageController mainPageController;
    private PlaceholdersSubPageController placeholdersSubPageController;
    private PreviewSubPageController previewSubPageController;

    // FXML elements
    @FXML private TextArea textArea;
    @FXML private Menu returnButton;

    // Local DAO instances
    private final TextBlockDao txtDao = new TextBlockDao();
    private final ImageBlockDao imgDao = new ImageBlockDao();
    private final EObjectDocDao eObjectDocDao = new EObjectDocDao();

    private EObject eObject; // Eobject, the user is working on
    private EObjectDoc doc; // Documentation the user is working on
    private boolean creatingDoc; // State used to determine if the user is currently creating documentation
    private boolean sourceTextActive; // State used to determine if the source text window is currently active

    // ----- Getters and setters -----
    public boolean isSourceTextActive() {
        return sourceTextActive;
    }
    public TextArea getTextArea() {
        return textArea;
    }
    public boolean isCreatingDoc(){
        return creatingDoc;
    }
    public Menu getReturnButton() {
        return returnButton;
    }
    public void setSourceTextActive(boolean sourceTextActive) {
        this.sourceTextActive = sourceTextActive;
    }
    public void setCreatingDoc(boolean bool){this.creatingDoc = bool;}

    /**
     * Method that sets references to other controllers
     * to be able to pass data between them
     */
    public void setControllers(){
        this.contentsSubPageController = Main.getContentsSubPageController();
        this.mainPageController = Main.getMainPageController();
        this.placeholdersSubPageController = Main.getPlaceholdersSubPageController();
        this.previewSubPageController = Main.getPreviewSubPageController();
    }

    /**
     * Method that initializes a controller object after its root element has been loaded.
     * Inherited from Initializable.
     * Called by the fxmlloader.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        creatingDoc = true;
    }


    /**
     * Method that saves content from text area in the database. The method has two different functions. Firstly,
     * it can save documentation from the user, and secondly it can save content blocks created or edited by the user.
     * This is controlled using a boolean and an if statement
     */
    @FXML
    public void save() {
        if (creatingDoc) {
            saveDocumentation();
        }
        else {
                //Creating an object to hold the object the user selected for editing in the GUI
                Object obj = contentsSubPageController.getSelectedCB();
                //Fetches the text from the TextArea in the GUI
                String txt = textArea.getText();
                //Creating TextBlock used for saving to DB later
                TextBlock txtBlock = new TextBlock();
                //Creating ImageBlock used for saving to DB later
                ImageBlock img = new ImageBlock();

                //Checks if a new Content Block is being created
                if (contentsSubPageController.isNewCB()) {
                    //Checks whether there are any results
                    Dialog<Results> dialog = createDialogBox();
                    Optional<Results> result = dialog.showAndWait();

                    if (result.isEmpty()){
                        return;
                    }
                        result.ifPresent((Results results) -> {
                            //If the user chose the "Text" option in the Combobox this code is executed
                            if (results.choice == null) {
                                throw new NullPointerException();
                            }
                            if (results.choice.equals("Text")) {
                                //The TextBlock Object gets filled with user data and gets saved in DB
                                txtBlock.setName(results.text);
                                txtBlock.setTxt(txt);
                                System.out.println("gemt text");
                                //txtDao.addOrUpdateTxt(txtBlock);
                            } else {
                                //The ImageBlock gets filled with user data and gets saved in DB
                                img.setName(results.choice);
                                img.setImagePath(txt);
                                System.out.println("gemt Img");
                                //imgDao.addOrUpdateImg(img);
                            }
                        });
                    //Setting the boolean used for checking if new content block back to false
                    contentsSubPageController.setNewCB(false);
                    contentsSubPageController.makeContentBlockList();
                }
                //If the ContentBlock the user wanted to edit is a TextBlock this gets executed
                else if (obj instanceof TextBlock) {
                    //Getting the TextBlock from the user inputs and saving in DB
                    System.out.println("Updated TextBlock");
                    updateTextBlock(txt);
                }
                else {
                    //Getting the ImageBlock from the user inputs and saving in DB
                    System.out.println("Updated ImageBlock");
                    updateImgBlock(txt);
                }
        }
        mainPageController.setSavedAlertText("Saved ✅");
        removeSavedAlert();
    }

    /**
     * Sets the textArea to the text of the opened file
     * @param file The file that will be inserted into the view
     */
    public void readText(File file) {
        String text;

        try (BufferedReader buffReader = new BufferedReader(new FileReader(file))) {
            while ((text = buffReader.readLine()) != null) {
                textArea.appendText(text + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that gets xml text from the loaded eObject
     * and inserts it into the textArea
     */
    public void insertTextInTextArea() {

        textArea.clear();
        // Get loaded eObject from mainPageController
        eObject = mainPageController.geteObject();

        // Get doc from eObject
        doc = eObject.getDoc();

        // Ensure that eObject, documentation, and xml text are not null
        if (eObject != null && eObject.getDoc() != null && eObject.getDoc().getText() != null) {
            //Insert the loaded xml text at index 0
            textArea.insertText(0, eObject.getDoc().getText());
        }
    }

    // ----- Private instance methods -----

    /**
     * Saves documentation to DB containing today's date. Is used in the save() method
     */
    private void saveDocumentation(){

        if (!sourceTextActive) {
            System.out.println("Nu er vi inde i If-loop");
            previewSubPageController.createTXTFromWebView();
        }

        // Get text from text area
        String txt = textArea.getText();

        // Set xml text in doc
        doc.setText(txt);

        // Set last edit to today's date
        doc.setLastEdit(Date.valueOf(LocalDate.now()));

        // Update the database with the changed doc
        System.out.println("Save or updated Doc");
        eObjectDocDao.addOrUpdateEObjectDoc(doc);
        mainPageController.populateBox();
    }

    /**
     * Saves TextBlock to the DB. Is called in the save() method
     * @param txt The text from the TextArea that is being saved in DB
     */
    private void updateTextBlock(String txt){
        TextBlock txtBlock = (TextBlock) contentsSubPageController.getSelectedCB();
        txtBlock.setTxt(txt);
        txtDao.addOrUpdateTxt(txtBlock);
    }

    /**
     * Saves ImageBlock to the DB. Is called in the save() method
     * @param txt The image path from the TextArea that is being saved in DB
     */
    private void updateImgBlock(String txt){
        ImageBlock img = (ImageBlock) contentsSubPageController.getSelectedCB();
        img.setImagePath(txt);
        imgDao.addOrUpdateImg(img);
    }

    /**
     * Creates the dialog box that pops-up when the user wants to save a new ContentBlock
     * @return Returns the dialog box containing the result of the user button press.
     */
    private Dialog<Results> createDialogBox(){
        //Creates a Dialog that is displayed in the GUI when the user is creating new Content Block and
        //presses save
        Dialog<Results> td = new Dialog<>();

        //Sets title and header of dialog
        td.setTitle("Content Block name");
        td.setHeaderText("Select name and type of Content Block");
        //Creates a dialogPane for the Dialog
        DialogPane dialogPane = td.getDialogPane();
        //Inserts buttons
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        //Creates a textfield and sets a prompt
        TextField dialogTxt = new TextField();
        dialogTxt.setPromptText("Name of Content Block");
        //Creates a combobox with two options and sets a prompt text
        ObservableList<String> options = FXCollections.observableArrayList("Text", "Image");
        ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.setPromptText("Select Content Block type");
        //Puts the text and combobox into the dialog view in GUI
        dialogPane.setContent(new VBox(8, dialogTxt, comboBox));
        //Checks which button was pressed
        td.setResultConverter((ButtonType button) ->{
            //if "OK" button was pressed it returns a results object with the values from the textarea
            //and combobox
            if (button == ButtonType.OK) {
                return new Results(dialogTxt.getText(), comboBox.getValue());
            }
            return null;
        });
        //Storing the results
        return td;
    }

    /**
     * Removes the saved label from the GUI after 3 seconds. Called by save() method.
     */
    private void removeSavedAlert(){
        //Creates a new TimerTask that will set the "gemt" alert message to false after the TimerTask is over
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                mainPageController.removeSavedAlert();
            }
        };
        //Creates a timer. True means the timer can be forced to close, when user exists application
        Timer timer = new Timer(true);
        //Defines how long the timer will last
        long delay = 3000;
        //Starts the timer
        timer.schedule(task, delay);
    }


    /**
     * Button on the GUI that changes a variety of things in the GUI to reflect that the user
     * is now editing in their documentation again.
     */
    @FXML
    private void returnToDoc() {
        creatingDoc = true;
        returnButton.setVisible(false);
        mainPageController.changeEObject();
        placeholdersSubPageController.updateEObjectValues();
        mainPageController.geteObjectChoice().setVisible(true);
    }

    /**
     * Method to enclose selected text in tags
     * @param tag String for the tag (eg. "h1")
     */
    private void tagSelectedText(String tag){
        // Get index range for selected text in text area
        IndexRange range = textArea.getSelection();

        // Insert closing tag after index range
        textArea.insertText(range.getEnd(),"</"+ tag + "><xsl:text>");

        // Insert opening tag before index range
        textArea.insertText(range.getStart(),"</xsl:text><" + tag + ">");
    }

    /**
     * Method that creates a header from the currently selected text in the text area
     * Event handler on Typography dropdown menuitem
     * @param e Event fired by the dropdown menuitem
     */
    @FXML
    private void createHeader(ActionEvent e) {
        // Get chosen menuitem (header from typography drop down)
        String choice = ((MenuItem) e.getSource()).getId();

        switch (choice) {
            case "h1": // If 'h1' is selected, surround selected text with h1 tag
                tagSelectedText("h1");
                break;
            case "h2": // If 'h2' is selected, surround selected text with h2 tag
                tagSelectedText("h2");
                break;
        }
    }

    /**
     * Inner class used to get and store the user input from the dialog when user tries to save a new ContentBlock
     */
    public class Results{

        final String text;
        final String choice;

        public Results(String text, String choice){
            this.text = text;
            this.choice = choice;
        }
    }
}
