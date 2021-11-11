import controller.TextEditorController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * Main method, launches the application
     * @param args command line arguments
     */
    public static void main(String[] args){
        TestAnne test = new TestAnne();
        test.metode();
        test.createOrg();

        launch(args);
    }

    /**
     * Implements abstract method inherited from Application
     * Starts the application. Sets stage, scene and root to hold contents.
     * @param stage primary stage provided by this.launch()
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/textEditor.fxml"));

        Parent root = loader.load();
        TextEditorController controller = loader.getController();
        controller.init(stage);

        stage.setTitle("JavaFX Text Editor");
        stage.setScene(new Scene(root, 700, 500));
        stage.getScene().getStylesheets().add("css/textEditStyle.css");
        stage.show();
    }
}
