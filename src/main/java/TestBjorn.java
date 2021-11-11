import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class TestBjorn {

    @FXML
    private BorderPane paneTextEditor;

    public void insertTextEditorMainPage() throws IOException {
        BorderPane pane = FXMLLoader.load(getClass().getResource("view/textEditor.fxml"));
        paneTextEditor.getChildren().setAll(pane);
    }


}
