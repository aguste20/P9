import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * Main method, launches the application
     * @param args command line arguments
     */
    public static void main(String[] args){
        TestAnne test = new TestAnne();
        test.metode();

        launch();
    }

    /**
     * Implements abstract method inherited from Application
     * Starts the application. Sets stage, scene and root to hold contents.
     * @param stage primary stage provided by this.launch()
     */
    @Override
    public void start(Stage stage) throws Exception {

    }
}
