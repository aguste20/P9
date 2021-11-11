import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    //roots being declared, which will later hold .fxml files.
    private static Parent mainPageParent;
    private static Parent textEditorParent;
    private static Parent contentsSubPageParent;
    private static Parent overviewSubPageParent;
    private static Parent placeholdersSubPageParent;


    // References to controllers.
    private static MainPageController mainPageController;
    private static TextEditorController textEditorController;
    private static ContentsSubPageController contentsSubPageController;
    private static OverviewSubPageController overviewSubPageController;
    private static PlaceholdersSubPageController placeholdersSubPageController;


    // ---- Getters ----
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
    public static PlaceholdersSubPageController getPlaceholdersSubPageController() {
        return placeholdersSubPageController;
    }


    //TODO: are these needed? - Bjørn
    //References to the current active stage and scene are held here.
    private static Stage stage;
    private static Scene scene;


    /**
     * Overrides the init() method inherited from Application.
     * Loads all .fxml files into memory and stores static references to them
     * Calls this.start()
     * @throws IOException if FXLLoader fails to load the resource exception is thrown and application shuts down
     */
    @Override
    public void init() throws IOException {
        //We fill the roots with respective .fxml files from harddrive.


        // We create an FXMLLoader which fills the empty mainPageParent reference with its respective .fxml file
        // We also fill the empty mainPageController reference with the controller of the .fxml file
        FXMLLoader loader1 = new FXMLLoader();
        mainPageParent = loader1.load(getClass().getResource("view/mainPage.fxml").openStream());
        mainPageController = loader1.getController();

        //Same as above
        FXMLLoader loader2 = new FXMLLoader();
        textEditorParent = loader2.load(getClass().getResource("view/textEditor.fxml").openStream());
        textEditorController = loader2.getController();

        //Same as above
        FXMLLoader loader3 = new FXMLLoader();
        contentsSubPageParent = loader3.load(getClass().getResource("view/contentsSubPage.fxml").openStream());
        contentsSubPageController = loader3.getController();

        //Same as above
        FXMLLoader loader4 = new FXMLLoader();
        overviewSubPageParent = loader4.load(getClass().getResource("view/overviewSubPage.fxml").openStream());
        overviewSubPageController = loader4.getController();

        //Same as above
        FXMLLoader loader5 = new FXMLLoader();
        placeholdersSubPageParent = loader5.load(getClass().getResource("view/placeholdersSubPage.fxml").openStream());
        placeholdersSubPageController = loader5.getController();



        /*
        //Relates to Old textEditor
        FXMLLoader loader2 = new FXMLLoader();
        textEditorParent = loader2.load(getClass().getResource("view/textEditorOLD.fxml").openStream());
        textEditorController = loader2.getController();
         */

    }


    /**
     * Implements abstract method inherited from Application
     * Starts the application. Sets stage, scene and root to hold contents.
     * @param stage primary stage provided by this.launch()
     */
    @Override
    public void start(Stage stage) throws Exception {

        scene = new Scene(mainPageParent);
        stage.setTitle("Documentation Assist 😎");
        //Image icon = new Image("https://github.com/Sighlund/P8/blob/main/src/main/resources/img/Logo.PNG?raw=true");
        //stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();



        //Inserts the textEditor into the right-side scrollPane of the main page.
        mainPageController.getPaneTextEditor().setContent(textEditorParent);

    }

    /**
     * Main method, launches the application
     * @param args command line arguments
     */
    public static void main(String[] args){
        launch(args);
    }

}
