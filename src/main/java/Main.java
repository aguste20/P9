import controller.MainPageController;
import controller.TextEditorController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Main extends Application {

    //roots being declared, which will later hold .fxml files.
    private static Parent mainPageParent;



    // References to controllers
    private static TextEditorController textEditorController;
    private static MainPageController mainPageController;


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
        mainPageParent = FXMLLoader.load(getClass().getResource("view/mainPage.fxml"));
    }


    /**
     * Implements abstract method inherited from Application
     * Starts the application. Sets stage, scene and root to hold contents.
     * @param stage primary stage provided by this.launch()
     */
    @Override
    public void start(Stage stage) throws Exception {

        TestBjorn testBjorn = new TestBjorn();

        testBjorn.insertTextEditorMainPage();

        scene = new Scene(mainPageParent);
        stage.setTitle("Documentation Assist 😎");
        //Image icon = new Image("https://github.com/Sighlund/P8/blob/main/src/main/resources/img/Logo.PNG?raw=true");
        //stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();





        /*
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/mainPage.fxml"));
        Parent root = loader.load();
        //TextEditorController controller = loader.getController();
        //controller.init(stage);


        stage.setTitle("JavaFX Text Editor");
        stage.setScene(new Scene(root));
        stage.show();
        */

    }

    /**
     * Main method, launches the application
     * @param args command line arguments
     */
    public static void main(String[] args){
        launch(args);
    }

}
