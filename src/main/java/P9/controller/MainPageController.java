package P9.controller;

import P9.Main;
import P9.model.EObject;
import P9.persistence.EObjectDao;
import P9.persistence.UserDao;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.persistence.Column;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable{

    //We annotate the containers of the mainPage.fxml
    @FXML private ScrollPane paneTextEditor;
    @FXML private ScrollPane paneOverviewSubPage;
    @FXML private ScrollPane paneContentsPlaceholders;

    // Reference to the engineering object that the user is working on
    EObject eObject;
    // Reference to the DAO for our user.
    UserDao userDAO = new UserDao();

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

        // Load engineering object from database with id = 1
        EObjectDao dao = new EObjectDao();
        //TODO Anne - skal jo ikke være hardcoded i virkelig løsning.
        // Men giver ikke mening at gøre dynamisk lige nu
        eObject = dao.getById(1);

        // If eObject has no doc, create one for it
        if (eObject.getDoc() == null){
            eObject.createNewDoc();
        }

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
        eObject.setImagePath("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.shutterstock.com%2Fda%2Ffile-converter&psig=AOvVaw1xd-Y-5Pvm8AYGrA2kEA-g&ust=1637661113319000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCMi735nZq_QCFQAAAAAdAAAAABAD");

        javaObjectToXML(eObject);
    }

    public void javaObjectToXML(EObject eObject){
        try
        {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(EObject.class);

            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty("com.sun.xml.bind.xmlHeaders",
                    "<?xml-stylesheet type='text/xsl' href='style.xsl' ?>");

            //Store XML to File
            File file = new File("src\\main\\resources\\xml\\eObject.xml");

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

    public void switchToPreviewSubPage(){
        Main.getPreviewSubPageController().getWebGridPane().add(Main.getWebview(), 0 , 0);
        paneTextEditor.setContent(Main.getPreviewSubPageParent());
    }

    public void switchToTextEditorPage(){
        paneTextEditor.setContent(Main.getTextEditorParent());
    }

    public void switchToContentsSubPage(){
        paneContentsPlaceholders.setContent(Main.getContentsSubPageParent());
    }



}


