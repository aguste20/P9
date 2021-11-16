package P9.controller;

import P9.Main;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;

public class MainPageController {

    //We annotate the containers of the mainPage.fxml
    @FXML
    private ScrollPane paneTextEditor;

    @FXML
    private ScrollPane paneOverviewSubPage;

    @FXML
    private ScrollPane paneContentsPlaceholders;



    public ScrollPane getPaneTextEditor() {
        return paneTextEditor;

    }

    public ScrollPane getPaneOverviewSubPage() { return paneOverviewSubPage; }

    public ScrollPane getPaneContentsPlaceholders() { return paneContentsPlaceholders; }

}
