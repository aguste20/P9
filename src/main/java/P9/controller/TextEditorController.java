package P9.controller;

import P9.Main;
import P9.model.*;
import P9.persistence.ContentBlockDao;
import P9.persistence.EObjectDocDao;
import P9.persistence.TextBlockDao;
import P9.persistence.UserDao;
import com.sun.jdi.InvocationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class TextEditorController implements Initializable {

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

        // Load xml text from the eObject that the user is working on
        insertXmlTextInTextArea();
        //Load the date and the responsible user of the last edit into labels.
        insertLastEditUserInLabels();

        textEditorActive = true;

    }

    public void init(Stage myStage) {
        this.stage = myStage;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    //TODO slettes?
    /*
    public void exit() {
        if (textArea.getText().isEmpty()) {
            return;
        }
        ButtonType button1 = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType button2 = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType button3 = new ButtonType("Don't save", ButtonBar.ButtonData.NO);

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Close without saving?",
                button1,
                button2,
                button3);
        /*
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Yes, do not save");
                ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("No, I want to save");
                ((Button) alert.getDialogPane().lookupButton(ButtonType.NO)).setText("Cancel");


        alert.setHeaderText("Are you sure you want to close without saving?");
        alert.showAndWait();

        if (alert.getResult() == button1) {
            alert.close();
        }
        if (alert.getResult() == button2) {
            save();
        }
        if (alert.getResult() == button3){
            textArea.clear();
        }
    }
*/
    /**
     * Method that saves content from text area in the database
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
            try {
                TextBlock txtCheck = (TextBlock) Main.getContentsSubPageController().cbEdit.getValue();
                String txt = textArea.getText();
                TextBlock txtBlock = new TextBlock();

                if (Objects.isNull(txtCheck)) {
                    TextInputDialog td = new TextInputDialog();

                    td.setTitle("Content Block name");
                    td.setHeaderText("What should the Content Block be called?");
                    Optional<String> result = td.showAndWait();

                    result.ifPresent(txtBlock::setName);
                } else {
                    txtBlock = (TextBlock) Main.getContentsSubPageController().cbEdit.getValue();
                }
                txtBlock.setTxt(txt);
                txtDao.addOrUpdateTxt(txtBlock);
                Main.getContentsSubPageController().cbEdit.setPromptText("Edit...");
                creatingDoc = true;
                returnButton.setVisible(false);
                Main.getMainPageController().changeEObject();
                Main.getPlaceholdersSubPageController().updateEObjectValues();
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        //TODO Anne - har bare udkommenteret for nu. Tænker det skal slettes?
        /*
         try {
            fileChooser.setTitle("Save As");
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                PrintWriter savedText = new PrintWriter(file);
                BufferedWriter out = new BufferedWriter(savedText);
                out.write(textArea.getText());
                out.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e);
        } catch (IOException e) {
            e.printStackTrace();
        }
         */
    }

    @FXML
    public void returnToDoc() {
        creatingDoc = true;
        returnButton.setVisible(false);
        Main.getMainPageController().changeEObject();
        Main.getPlaceholdersSubPageController().updateEObjectValues();
    }

    //TODO slettes?
    /*
    @FXML
    public void openFile() {
        fileChooser.setTitle("Open File");
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            textArea.clear();
            readText(file);
        }
    }
    */
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

    //TODO add confirmation window if text editor has text and wasn't saved

    //TODO slettes?
    /*
    @FXML
    public void newFile() {
        textArea.clear();
    }
    */

/*
    @FXML
    public void about() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("About");
        alert.setHeaderText("Ayy");
        alert.setContentText("Lmao");
        alert.showAndWait();
    }
*/
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
        eObject = Main.getMainPageController().geteObject();

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
            UserDao userDao = Main.getMainPageController().userDAO;
            User user = userDao.getById(1);
            lastUserLabel.setText(" performed by: " + user.getName());
        }
    }
}
