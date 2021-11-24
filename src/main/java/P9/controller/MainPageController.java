package P9.controller;

import P9.Main;
import P9.model.EObject;
import P9.model.TextBlock;
import P9.persistence.ContentBlockDao;
import P9.persistence.EObjectDao;
import P9.persistence.TextBlockDao;
import P9.persistence.UserDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;

import javax.persistence.Column;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainPageController implements Initializable{

    //We annotate the containers of the mainPage.fxml
    @FXML private ScrollPane paneTextEditor;
    @FXML private ScrollPane paneOverviewSubPage;
    @FXML private ScrollPane paneContentsPlaceholders;
    //Annotating label and button
    @FXML public Label eObjectLabel;
    @FXML public Button eOjbectUpdate;
    @FXML public ComboBox<EObject> eObjectChoice;

    // Reference to the engineering object that the user is working on
    EObject eObject;
    // Reference to DAO objects.
    UserDao userDAO = new UserDao();
    TextBlockDao txtDao = new TextBlockDao();
    EObjectDao eDao = new EObjectDao();

    // ---- Getters ----
    // Returns the containers of the mainPage.fxml
    public ScrollPane getPaneTextEditor() { return paneTextEditor; }
    public ScrollPane getPaneOverviewSubPage() { return paneOverviewSubPage; }
    public ScrollPane getPaneContentsPlaceholders() { return paneContentsPlaceholders; }

    public EObject geteObject() {
        return eObject;
    }
    public UserDao getUserDAO() { return userDAO; }

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

    }

    /**
     * Method used to change eObject when user changes in the GUI
     */
    public void loadEObject(){
        if (eObject == null){
            eObject = eDao.getById(1);
        }

        eObjectLabel.setText(eObject.getName());

        // If eObject has no doc, create one for it, and set it to the template in the DB
        if (eObject.getDoc() == null){
            eObject.createNewDoc();
        }
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
     * Fetches the eObject in the DB, when the update button is pressed in the GUI,
     * thereby updating the eObject
     */
    public void updateEObject() {

        eObject = eDao.getById(eObject.geteObjectId());
        eObjectLabel.setText(eObject.getName());
        Main.getPlaceholdersSubPageController().updateEObjectValues();
    }

    /**
     * Method called when user changes eObject in the GUI ComboBox.
     * It changes the eObject values to the currently selected by changing the
     * label, the placeholder values, the TextEditor, and the preview page.
     */
    public void changeEObject() {
        eObject = eObjectChoice.getValue();
        loadEObject();
        Main.getPlaceholdersSubPageController().updateEObjectValues();
        Main.getTextEditorController().insertXmlTextInTextArea();
        Main.getPreviewSubPageController().createXslFromTextArea();
    }

    /**
     * Methods for changing the contents of the middle pane of the mainPage.fxml.
     * When user presses one of the buttons, the interface shows the associated viewfile.
     */
    public void switchToPlaceholdersSubPage (){
        paneContentsPlaceholders.setContent(Main.getPlaceholdersSubPageParent());
    }

    public void switchToPreviewSubPage (ActionEvent event){
        Main.getPreviewSubPageController().createXslFromTextArea();
        paneTextEditor.setContent(Main.getPreviewSubPageParent());
    }

    public void switchToTextEditorPage(){
        //loads webview, if it contains any content
        if (Main.getEngine()!=null){
            Main.getPreviewSubPageController().createTXTFromWebView();
        }

        paneTextEditor.setContent(Main.getTextEditorParent());
    }

    public void switchToContentsSubPage(){
        paneContentsPlaceholders.setContent(Main.getContentsSubPageParent());
    }

}


