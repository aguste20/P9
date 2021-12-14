package P9.controller;

import P9.Main;
import P9.model.EObject;
import P9.model.EObjectDoc;
import P9.model.User;
import P9.persistence.EObjectDao;
import P9.persistence.TextBlockDao;
import P9.persistence.UserDao;
import com.lowagie.text.DocumentException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.util.StringConverter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

//TODO Anne/cleanup: Mangler dokumentation

public class MainPageController implements Initializable{

    // ----- Properties -----
    // References to other controllers
    private OverviewSubPageController overviewSubPageController;
    private PlaceholdersSubPageController placeholdersSubPageController;
    private PreviewSubPageController previewSubPageController;
    private TextEditorController textEditorController;

    // FXML elements
    @FXML private ScrollPane paneTextEditor;
    @FXML private ScrollPane paneOverviewSubPage;
    @FXML private ScrollPane paneContentsPlaceholders;
    @FXML private ComboBox<EObject> eObjectChoice;
    @FXML private Button exportPDFButton;
    @FXML private Label eObjectLabel;
    @FXML private Label lastEditLabel;
    @FXML private Label lastUserLabel;
    @FXML private Label savedAlert;

    // Local DAO instances
    private final UserDao userDAO = new UserDao();
    private final TextBlockDao txtDao = new TextBlockDao();
    private final EObjectDao eDao = new EObjectDao();

    private EObject eObject; // Reference to the engineering object that the user is working on
    private String PDF_output = "src/main/resources/html2pdf.pdf"; // Path to pdf

    // ---- Getters ----
    public ScrollPane getPaneTextEditor() { return paneTextEditor; }
    public ScrollPane getPaneOverviewSubPage() { return paneOverviewSubPage; }
    public ScrollPane getPaneContentsPlaceholders() { return paneContentsPlaceholders; }
    public EObject geteObject() {
        return eObject;
    }
    public ComboBox<EObject> geteObjectChoice() {
        return eObjectChoice;
    }
    public Label geteObjectLabel() {
        return eObjectLabel;
    }

    /**
     * Method that sets references to other controllers
     * to be able to pass data between them
     */
    public void setControllers(){
        this.overviewSubPageController = Main.getOverviewSubPageController();
        this.placeholdersSubPageController = Main.getPlaceholdersSubPageController();
        this.previewSubPageController = Main.getPreviewSubPageController();
        this.textEditorController = Main.getTextEditorController();
    }


    /**
     * This method initializes a controller after its root element has already been processed.
     * I think this means that this method is needed to keep content in the view pages updated visually.
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        //Loads eObject selected by user in GUI
        loadEObject();

        //Populate the Combobox with the eObjects' names
        populateBox();

        // Marshal eObject to XML file, which is saved in resources/xml
        eObject.eObjectToXML();

        insertLastEditUserInLabels();
    }


    // ----- Instance methods -----
    // TODO Anne/cleanup: Mangler dokumentation
    public void setSavedAlertText(String text) {
        this.savedAlert.setText(text);
        savedAlert.setVisible(true);
    }

    // TODO Anne/cleanup: Mangler dokumentation
    public void removeSavedAlert(){
        savedAlert.setVisible(false);
    }

    /**
     * Populates the ComboBox with all eObjects in the database
     */
    public void populateBox() {
        //Create list with eObject and fetch all in DB. Convert to ObservableList
        List<EObject> eList;
        eList = eDao.listAll();
        ObservableList<EObject> oeList = FXCollections.observableArrayList(eList);
        //Put ObservableList of eObjects into ComboBox
        eObjectChoice.setItems(oeList);
        //Creating a stringConverter which allows us to store eObjects in ComboBox, but display the name
        eObjectChoice.setConverter(new StringConverter<>() {
            @Override
            //The thing that will be displayed in the GUI, and return it.
            public String toString(EObject eObject) {
                return eObject.getName();
            }

            //Takes the currently selected eObject name, and finds the correlating eObject,
            //by searching the Comboxes' list of eObjects.
            @Override
            public EObject fromString(String s) {
                return eObjectChoice.getItems().stream().filter
                        (eOb -> eOb.getName().equals(s)).findFirst().orElse(null);
            }
        });
    }


    /**
     * Method called when user changes eObject in the GUI ComboBox.
     * It changes the eObject values to the currently selected by changing the
     * label, the placeholder values, the TextEditor, and the preview page.
     */
    @FXML
    public void changeEObject() {
        eObject = eObjectChoice.getValue();
        loadEObject();
        placeholdersSubPageController.updateEObjectValues();
        textEditorController.insertTextInTextArea();
        previewSubPageController.createXslFromTextArea();
        overviewSubPageController.updateToc();
    }

    /**
     * Fetches the eObject in the DB, when the update button is pressed in the GUI,
     * thereby updating the eObject
     */
    @FXML public void updateEObject() {
        eObject = eDao.getById(eObject.geteObjectId());
        eObjectLabel.setText(eObject.getName());
        placeholdersSubPageController.updateEObjectValues();
    }

    // ----- Private instance methods -----
    /**
     * Method used to change eObject when user changes in the GUI
     */
    private void loadEObject(){
        if (eObject == null){
            eObject = eDao.getById(1);
        }

        // Set name on label and combo box
        String name = eObject.getName();
        eObjectLabel.setText(name);
        eObjectChoice.setPromptText(name);

        // If eObject has no doc, create one for it, and set it to the template in the DB
        if (eObject.getDoc() == null){
            eObject.createNewDoc();
        }
    }

    /**
     * Converts the HTML in the fancy editor to a PDF document
     */
    @FXML
    private void exportPDF() throws IOException {
        //Storing HTML from fancy editor in a String
        String inputHTML = (String) Main.getEngine().executeScript("document.getElementById(\"mySpan\").innerHTML");

        //Modify background colors to white
        String PDF = inputHTML.replaceAll("yellow", "transparent");

        //Parsing HTML to a document Object
        Document document = Jsoup.parse(PDF, "UTF-8");
        //Setting syntax
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        //Defining output
        File outputPDF = new File(PDF_output);

        //Rendering the document with HTML to PDF
        try (OutputStream outputStream = new FileOutputStream(outputPDF)) {
            ITextRenderer renderer = new ITextRenderer();
            SharedContext sharedContext = renderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setInteractive(false);
            renderer.setDocumentFromString(document.html());
            renderer.layout();
            renderer.createPDF(outputStream);
            File file = new File(PDF_output);
            Desktop.getDesktop().open(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method for inserting the date of the last time the document was saved.
     */
    private void insertLastEditUserInLabels(){
        // Get doc from eObject
        EObjectDoc doc = eObject.getDoc();
        //Variable that stores the last edit of the document.
        Date editDate = doc.getLastEdit();
        //If the variable is not null, its value is inserted into the label.
        if (editDate != null){
            lastEditLabel.setText("Last edit " + editDate);

            //This part is pretty dumb at the moment. It is not dynamic.
            //It gets the name of a user, and in this case the user with id = 1
            User user = userDAO.getById(1);
            lastUserLabel.setText(" performed by: " + user.getName());
        }
    }

    /**
     * Method for saving the changes made to either contentBlocks,
     * the Source Text editor, or the Preview editor
     */
    @FXML
    private void saveChanges(){
        textEditorController.save();
    }

    /**
     * Methods for changing the contents of the middle AnchorPane of the mainPage.fxml.
     * When user presses one of the buttons, the interface shows the associated viewfile.
     */

    @FXML
    public void switchToPlaceholdersSubPage(){
        paneContentsPlaceholders.setContent(Main.getPlaceholdersSubPageParent());
    }

    //TODO Anne/cleanup: Mangler dokumentation
    @FXML
    public void switchToPreviewSubPage(){
        if(!textEditorController.isSourceTextActive()){ // Preview is already the active window
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Illegal action");
            alert.setHeaderText("You are already viewing Preview");
            alert.showAndWait();
        }
        else { // Text editor is active window, switch scene
            // Update table of contents
            overviewSubPageController.updateToc();

            previewSubPageController.createXslFromTextArea();

            paneTextEditor.setContent(Main.getPreviewSubPageParent());

            //Main.getPlaceholdersSubPageController().setTextEditor(false);
            textEditorController.setSourceTextActive(false);
        }
    }

    // TODO Anne/cleanup: Mangler dokumentation
    @FXML
    public void switchToTextEditorPage() {
        if(textEditorController.isSourceTextActive()){ // Text editor is already active window
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Illegal action");
            alert.setHeaderText("You are already viewing Source Text");
            alert.showAndWait();
        }
        else { // Preview is active window, switch scene
            if (textEditorController.getCreatingDoc()) {
                //loads webview, if it contains any content
                if (Main.getEngine() != null) {
                    previewSubPageController.createTXTFromWebView();
                }
                paneTextEditor.setContent(Main.getTextEditorParent());
                textEditorController.setSourceTextActive(true);
            }
        }
    }

    //TODO Anne/Cleanup: Mangler dokumentation
    @FXML
    public void switchToContentsSubPage(){
        paneContentsPlaceholders.setContent(Main.getContentsSubPageParent());
    }


}


