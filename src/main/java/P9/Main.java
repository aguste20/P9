package P9;

import P9.controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    // Root elements holding loaded .fxml files.
    private static Parent mainPageParent;
    private static Parent textEditorParent;
    private static Parent contentsSubPageParent;
    private static Parent overviewSubPageParent;
    private static Parent placeholdersSubPageParent;
    private static Parent registerNewContentBlockPageParent;
    private static Parent previewSubPageParent;

    // Controllers
    private static MainPageController mainPageController;
    private static TextEditorController textEditorController;
    private static ContentsSubPageController contentsSubPageController;
    private static OverviewSubPageController overviewSubPageController;
    private static PlaceholdersSubPageController placeholdersSubPageController;
    private static RegisterNewContentBlockController registerNewContentBlockController;
    private static PreviewSubPageController previewSubPageController;

    // Currently active stage and scene
    private static Stage stage;
    private static Scene scene;

    // Webview and webview engine displaying the documentation preview
    private static WebView webview;
    private static WebEngine engine;


    // ---- Getters ----
    //Getters for root elements
    public static Parent getMainPageParent() { return mainPageParent; }
    public static Parent getTextEditorParent() { return textEditorParent; }
    public static Parent getContentsSubPageParent() { return contentsSubPageParent; }
    public static Parent getOverviewSubPageParent() { return overviewSubPageParent; }
    public static Parent getPlaceholdersSubPageParent() { return placeholdersSubPageParent; }
    public static Parent getRegisterNewContentBlockPageParent() { return registerNewContentBlockPageParent; }
    public static Parent getPreviewSubPageParent () {return previewSubPageParent;}

    //Getters for controllers
    public static MainPageController getMainPageController() {
        return mainPageController;
    }
    public static TextEditorController getTextEditorController() {
        return textEditorController;
    }
    public static ContentsSubPageController getContentsSubPageController() {
        return contentsSubPageController;
    }
    public static OverviewSubPageController getOverviewSubPageController() {
        return overviewSubPageController;
    }
    public static PlaceholdersSubPageController getPlaceholdersSubPageController() { return placeholdersSubPageController; }
    public static RegisterNewContentBlockController getRegisterNewContentBlockController() { return registerNewContentBlockController; }
    public static PreviewSubPageController getPreviewSubPageController() {return previewSubPageController;}

    // Webview and webengine
    public static WebView getWebview() {
        return webview;
    }
    public static WebEngine getEngine() {
        return engine;
    }


    /**
     * Overrides the init() method inherited from Application.
     * Loads all .fxml files into memory and stores static references to them
     * Calls this.start()
     * @throws IOException if loading fxml files fails
     */
    @Override
    public void init() throws IOException {
        // Load all fxml files from directory and load related controllers
        loadFxmlFilesFromDirectory();
    }

    /**
     * Implements abstract method inherited from Application
     * Starts the application. Sets stage, scene and root to hold contents.
     * @param stage primary stage provided by this.launch()
     */
    @Override
    public void start(Stage stage) {

        // Load webview to display preview
        loadWebViewInPreviewSubPage();

        // Set content roots in main page
        setContentInMainPage();

        // Create new scene from main page root, set scene, and show stage
        scene = new Scene(mainPageParent);
        stage.setTitle("Documentation Assist 😎");
        //TODO Anne: Skal der ske noget her?
        //Image icon = new Image("https://github.com/Sighlund/P8/blob/main/src/main/resources/img/Logo.PNG?raw=true");
        //stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method, launches the application
     * @param args command line arguments
     */
    public static void main(String[] args){ launch(args); }



    // ------ Private helper methods -----

    /**
     * Method that loads fxml files from directory to parent roots in Main
     * The method also gets references to the initialized controller objects
     * @throws IOException if loading fxml files fails
     */
    private void loadFxmlFilesFromDirectory() throws IOException {
        //We fill the roots with respective .fxml files from hard drive.

        // We create an FXMLLoader which fills the empty mainPageParent reference with its respective .fxml file
        // We also fill the empty mainPageController reference with the P9.controller of the .fxml file
        FXMLLoader loader1 = new FXMLLoader();
        mainPageParent = loader1.load(getClass().getResource("../view/mainPage.fxml").openStream());
        mainPageController = loader1.getController();

        //Same as above
        FXMLLoader loader2 = new FXMLLoader();
        textEditorParent = loader2.load(getClass().getResource("../view/texteditor.fxml").openStream());
        textEditorController = loader2.getController();

        //Same as above
        FXMLLoader loader3 = new FXMLLoader();
        contentsSubPageParent = loader3.load(getClass().getResource("../view/contentsSubPage.fxml").openStream());
        contentsSubPageController = loader3.getController();

        //Same as above
        FXMLLoader loader4 = new FXMLLoader();
        overviewSubPageParent = loader4.load(getClass().getResource("../view/overviewSubPage.fxml").openStream());
        overviewSubPageController = loader4.getController();

        //Same as above
        FXMLLoader loader5 = new FXMLLoader();
        placeholdersSubPageParent = loader5.load(getClass().getResource("../view/placeholdersSubPage.fxml").openStream());
        placeholdersSubPageController = loader5.getController();

        //Same as above
        FXMLLoader loader6 = new FXMLLoader();
        registerNewContentBlockPageParent = loader6.load(getClass().getResource("../view/registerNewContentBlockPage.fxml").openStream());
        registerNewContentBlockController = loader6.getController();

        //Same as above
        FXMLLoader loader7 = new FXMLLoader();
        previewSubPageParent = loader7.load(getClass().getResource("../view/previewSubPage.fxml").openStream());
        previewSubPageController = loader7.getController();
    }

    /**
     * Method that creates and loads a WebView element
     * to the preview sub page root using a WebEngine.
     */
    private void loadWebViewInPreviewSubPage(){
        // Create webview and engine.
        webview = new WebView();
        engine = webview.getEngine();

        //Sets load path to xml file
        File f = new File("src/main/resources/xml/eObject.xml");
        engine.load(f.toURI().toString());

        // Add webview to preview sub page
        previewSubPageController.getWebGridPane().add(webview, 0 , 0);
    }

    /**
     * Method that sets content in the main page.
     * Adds textEditor, overView, and contentsPlaceholders roots to main page
     */
    private void setContentInMainPage() {
        //Inserts the textEditor into the middle Pane of the main page.
        mainPageController.getPaneTextEditor().setContent(textEditorParent);

        //Inserts the overviewSubPage(Table of contents) into the left-side scrollPane of the main page.
        mainPageController.getPaneOverviewSubPage().setContent(overviewSubPageParent);

        //Inserts the contentsSubPage into the right-side scrollPane of the main page as the default option.
        mainPageController.getPaneContentsPlaceholders().setContent(contentsSubPageParent);
    }

}
