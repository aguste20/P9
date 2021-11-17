package P9.controller;

import P9.Main;
import P9.model.*;
import P9.persistence.EObjectDao;
import P9.persistence.EObjectDocDao;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class TextEditorController implements Initializable {




    @FXML
    public TextArea textArea;

    private Stage stage;
    private final FileChooser fileChooser = new FileChooser();

    private EObject eObject;
    private EObjectDoc doc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser
                .getExtensionFilters()
                .addAll(
                        new FileChooser.ExtensionFilter("XML", "*.xml"),
                        new FileChooser.ExtensionFilter("All Files", "*.*"));

        // Load xml text from the eObject that the user is working on
        insertXmlTextInTextArea();
    }

    public void init(Stage myStage) {
        this.stage = myStage;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    @FXML
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

         */
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

    /**
     * Method that saves content from text area in the database
     */
    @FXML
    private void save() {

        // If eObject doesn't already have a persisted doc, create one
        if(eObject.getDoc() == null){
            eObject.createNewDoc();
        }

        // Get doc from eObject
        doc = eObject.getDoc();

        // Get text from text area
        String txt = textArea.getText();

        // Set xml text in doc
        doc.setXmlText(txt);

        // Set last edit to today's date
        doc.setLastEdit(Date.valueOf(LocalDate.now()));

        // Update the database with the changed doc
        EObjectDocDao dao = new EObjectDocDao();
        dao.addOrUpdateEObjectDoc(doc);


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
    public void openFile() {
        fileChooser.setTitle("Open File");
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            textArea.clear();
            readText(file);
        }
    }

    // sets the textArea to the text of the opened file
    private void readText(File file) {
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




    @FXML
    public void newFile() {
        textArea.clear();
    }

    @FXML
    public void about() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("About");
        alert.setHeaderText("Ayy");
        alert.setContentText("Lmao");
        alert.showAndWait();
    }

    // TODO Add a proper font-size menu && color selection

    @FXML
    public void fontSize(ActionEvent e) {
        String choice = ((CheckMenuItem) e.getSource()).getId();

        switch (choice) {
            case "small":
                textArea.setStyle("-fx-font-size: 14px");
                break;
            case "default":
                textArea.setStyle("-fx-font-size: 22px");
                break;
            case "large":
                textArea.setStyle("-fx-font-size: 30px");
                break;
            default:
                textArea.setStyle("-fx-font-size: 22px");
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
        textArea.insertText(range.getEnd(),"</"+ tag + ">");

        // Insert opening tag before index range
        textArea.insertText(range.getStart(),"<" + tag + ">");
    }

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
        // Get loaded eObject from mainPageController
        eObject = Main.getMainPageController().geteObject();

        // Ensure that eObject, documentation, and xml text are not null
        if (eObject != null && eObject.getDoc() != null && eObject.getDoc().getXmlText() != null) {
            //Insert the loaded xml text at index 0
            textArea.insertText(0, eObject.getDoc().getXmlText());
        }
    }

}
