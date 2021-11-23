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
        //Might have to move some of the content that is outside this method, in to this method, in order to keep the interface updated. - Bjørn

        load();
        // Load engineering object from database with id = 1
        //TODO Anne - skal jo ikke være hardcoded i virkelig løsning.
        // Men giver ikke mening at gøre dynamisk lige nu

    // EObject created to set the attributes that the xml file is getting created from
        EObject eObject = new EObject();
        //TODO: Lav nedenstående dynamisk

        eObject.seteObjectId(999);
        eObject.setName("Volvo Penta Car Factory");
        eObject.setVersion(2.2);
        eObject.setLength(2.3);
        eObject.setHeight(2.4);
        eObject.setHeight(2.5);
        eObject.setWidth(2.6);
        eObject.setWeight(2.7);

        // Marshal eObject to XML file, which is saved in resources/xml
        javaObjectToXML(eObject);

        //Populate the Combobox with the eObjects' names
        List<EObject> eList;
        eList = eDao.listAll();
        ObservableList<EObject> oeList = FXCollections.observableArrayList(eList);
        eObjectChoice.setItems(oeList);
        eObjectChoice.setConverter(new StringConverter<>() {
            @Override
            public String toString(EObject eObject) {
                return eObject.getName();
            }

            @Override
            public EObject fromString(String s) {
                return eObjectChoice.getItems().stream().filter
                        (ap -> ap.getName().equals(s)).findFirst().orElse(null);
            }
        });


                /*
        List<String> eList;
        EObjectDao eObjectDao = new EObjectDao();
        eList = eObjectDao.listName();
        eObjectChoice.getItems().addAll(eList);
    */
    }

    public void load(){
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
     * Fetches the eObject in the DB, when the update button is pressed in the GUI,
     * thereby updating the eObject
     * @param e Refers to the button in the GUI
     */
    public void updateEObject(ActionEvent e) {

        eObject = eDao.getById(eObject.geteObjectId());
        eObjectLabel.setText(eObject.getName());
        Main.getPlaceholdersSubPageController().updateEObjectValues();
    }

    public void changeEObject(ActionEvent e) {
        eObject = eObjectChoice.getValue();
        load();
        Main.getPlaceholdersSubPageController().updateEObjectValues();
        Main.getTextEditorController().insertXmlTextInTextArea();
    }

    public void javaObjectToXML(EObject eObject){
        //Passes EObject attribute values to create XML file
        try
        {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(EObject.class);

            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //Formats file and bind it to xsl
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty("com.sun.xml.bind.xmlHeaders",
                    "<?xml-stylesheet type='text/xsl' href='style.xsl' ?>");

            //Store XML to File
            File file = new File("src/main/resources/xml/eObject.xml");

            //Writes XML file to file-system
            jaxbMarshaller.marshal(eObject, file);
        }
        catch (JAXBException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Methods for changing the contents of the middle pane of the mainPage.fxml.
     * When user presses one of the buttons, the interface shows the associated viewfile.
     * @param event action from button element
     */
    public void switchToPlaceholdersSubPage (ActionEvent event){
        paneContentsPlaceholders.setContent(Main.getPlaceholdersSubPageParent());
    }

    public void switchToPreviewSubPage (ActionEvent event){
        paneTextEditor.setContent(Main.getPreviewSubPageParent());
    }

    public void switchToTextEditorPage(){
        paneTextEditor.setContent(Main.getTextEditorParent());
    }

    public void switchToContentsSubPage(){
        paneContentsPlaceholders.setContent(Main.getContentsSubPageParent());
    }


}


