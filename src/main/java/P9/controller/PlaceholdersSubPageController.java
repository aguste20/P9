package P9.controller;

import P9.Main;
import P9.model.EObject;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class PlaceholdersSubPageController implements Initializable {
    //TODO Lav en liste over placeholders

    EObject eObject = new EObject();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eObject = Main.getMainPageController().geteObject();
    }



}
