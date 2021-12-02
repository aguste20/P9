package P9.controller;

import P9.Main;
import P9.model.*;
import P9.persistence.*;
import com.sun.jdi.InvocationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class TextEditorController implements Initializable {

    // References to other controllers
    private ContentsSubPageController contentsSubPageController;
    private MainPageController mainPageController;
    private OverviewSubPageController overviewSubPageController;
    private PlaceholdersSubPageController placeholdersSubPageController;
    private PreviewSubPageController previewSubPageController;
    private RegisterNewContentBlockController registerNewContentBlockController;
    private TextEditorController textEditorController;


    @FXML
    public TextArea textArea;

    @FXML
    public Label lastEditLabel;

    @FXML
    public Label lastUserLabel;
    @FXML
    public Menu returnButton;

    private Stage stage;
    private final FileChooser fileChooser = new FileChooser();

    private EObject eObject;
    private EObjectDoc doc;
    private boolean creatingDoc = true;
    private boolean textEditorActive;
    private TextBlockDao txtDao = new TextBlockDao();
    private ImageBlockDao imgDao = new ImageBlockDao();

    public void setCreatingDoc(boolean bool){
        this.creatingDoc = bool;
    }

    public boolean getCreatingDoc(){
        return creatingDoc;
    }

    // ----- Getters and setters -----
    public boolean isTextEditorActive() {
        return textEditorActive;
    }
    public void setTextEditorActive(boolean textEditorActive) {
        this.textEditorActive = textEditorActive;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //TODO Anne - ryd op her, det bruges vel ikke?
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser
                .getExtensionFilters()
                .addAll(
                        new FileChooser.ExtensionFilter("xml", "*.xml"),
                        new FileChooser.ExtensionFilter("All Files", "*.*"));

        textEditorActive = true;

    }

    public void init(Stage myStage) {
        this.stage = myStage;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    /**
     * Method that saves content from text area in the database. The method has two different functions. Firstly,
     * it can save documentation from the user, and secondly it can save content blocks created or edited by the user.
     * This is controlled using a boolean and an if statement
     */
    @FXML
    private void save() {
        if (creatingDoc) {
            // Get text from text area
            String txt = textArea.getText();

            // Set xml text in doc
            doc.setXmlText(txt);

            // Set last edit to today's date
            doc.setLastEdit(Date.valueOf(LocalDate.now()));

            // Update the database with the changed doc
            EObjectDocDao dao = new EObjectDocDao();
            dao.addOrUpdateEObjectDoc(doc);
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
                    Optional<Results> result = td.showAndWait();
                    //Creating objects used for saving to DB
                    TextBlock finalTxtBlock = txtBlock;
                    ImageBlock finalImg = img;
                    //Checks whether there are any results
                    result.ifPresent((Results results) ->{
                        //If the user chose the "Text" option in the Combobox this code is executed
                        if(results.choice.equals("Text")) {
                            //The TextBlock Object gets filled with user data and gets saved in DB
                            finalTxtBlock.setName(results.text);
                            finalTxtBlock.setTxt(txt);
                            txtDao.addOrUpdateTxt(finalTxtBlock);
                        }
                        else{
                            //The ImageBlock gets filled with user data and gets saved in DB
                            finalImg.setName(results.choice);
                            finalImg.setImagePath(txt);
                            imgDao.addOrUpdateImg(finalImg);
                        }
                    });
                    //Setting the boolean used for checking if new content block back to false
                    contentsSubPageController.setNewCB(false);
                    contentsSubPageController.makeContentBlockList();
                    contentsSubPageController.populateBox();
                }
                //If the ContentBlock the user wanted to edit is a TextBlock this gets executed
                else if (obj instanceof TextBlock) {
                    //Getting the TextBlock from the user inputs and saving in DB
                    txtBlock = (TextBlock) contentsSubPageController.getSelectedCB();
                    txtBlock.setTxt(txt);
                    txtDao.addOrUpdateTxt(txtBlock);
                }
                else {
                    //Getting the ImageBlock from the user inputs and saving in DB
                    img = (ImageBlock) contentsSubPageController.getSelectedCB();
                    img.setImagePath(txt);
                    imgDao.addOrUpdateImg(img);
                }
        }
    }

    /**
     * Inner class used to get and store the user input from the dialog when user tries to save a new ContentBlock
     */
    public static class Results{

        final String text;
        final String choice;

        public Results(String text, String choice){
            this.text = text;
            this.choice = choice;
        }
    }

    /**
     * Button on the GUI that changes a variety of things in the GUI to reflect that the user
     * is now editing in their documentation again.
     */
    @FXML
    public void returnToDoc() {
        creatingDoc = true;
        returnButton.setVisible(false);
        mainPageController.changeEObject();
        placeholdersSubPageController.updateEObjectValues();
    }

    // sets the textArea to the text of the opened file
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
     * Method to enclose selected text in tags (<> </>)
     * @param tag String for the tag (eg. "h1")
     */
    public void tagSelectedText(String tag){
        // Get index range for selected text in text area
        IndexRange range = textArea.getSelection();

        // Insert closing tag after index range
        textArea.insertText(range.getEnd(),"</"+ tag + "><xsl:text>");

        // Insert opening tag before index range
        textArea.insertText(range.getStart(),"</xsl:text><" + tag + ">");
    }

    //TODO kommentarer
    @FXML
    public void createHeader(ActionEvent e) {
        String choice = ((CheckMenuItem) e.getSource()).getId();

        switch (choice) {
            case "h1":
                tagSelectedText("h1");
                break;
            case "h2":
                tagSelectedText("h2");
                break;
            default:
                textArea.setStyle("-fx-font-size: 22px");
        }
    }

    /**
     * Method that gets xml text from the loaded eObject
     * and inserts it into the textArea
     */
    public void insertXmlTextInTextArea() {

        textArea.clear();
        // Get loaded eObject from mainPageController
        eObject = mainPageController.geteObject();

        // Get doc from eObject
        doc = eObject.getDoc();

        // Ensure that eObject, documentation, and xml text are not null
        if (eObject != null && eObject.getDoc() != null && eObject.getDoc().getXmlText() != null) {
            //Insert the loaded xml text at index 0
            textArea.insertText(0, eObject.getDoc().getXmlText());
        }
    }

    /**
     * Method for inserting the date of the last time the document was saved.
     */
    public void insertLastEditUserInLabels(){
        //Variable that stores the last edit of the document.
        Date editDate = doc.getLastEdit();
        //If the variable is not null, its value is inserted into the label.
        if (editDate != null){
            lastEditLabel.setText("Last edit " + editDate);

            //This part is pretty dumb at the moment. It is not dynamic.
            //It gets the name of a user, and in this case the user with id = 1
            UserDao userDao = mainPageController.userDAO;
            User user = userDao.getById(1);
            lastUserLabel.setText(" performed by: " + user.getName());
        }
    }

    /**
     * Method that gets references to other controllers
     * to be able to pass data between them
     */
    public void setControllers(){
        this.contentsSubPageController = Main.getContentsSubPageController();
        this.mainPageController = Main.getMainPageController();
        this.overviewSubPageController = Main.getOverviewSubPageController();
        this.placeholdersSubPageController = Main.getPlaceholdersSubPageController();
        this.previewSubPageController = Main.getPreviewSubPageController();
        this.registerNewContentBlockController = Main.getRegisterNewContentBlockController();
        this.textEditorController = Main.getTextEditorController();
    }
}
