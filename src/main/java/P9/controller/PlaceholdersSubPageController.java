package P9.controller;

import P9.Main;
import P9.model.EObject;
import P9.persistence.Setup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class PlaceholdersSubPageController implements Initializable {

    public Label objName;
    public Label objVersion;
    public Label objLength;
    public Label objHeight;
    public Label objWidth;
    public Label objWeight;


    //TODO Lav en liste over placeholders

    EObject eObject = new EObject();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eObject = Main.getMainPageController().geteObject();
        setLabels();
    }

    /**
     * Adds values from the selected eObject to the labels next to buttons
     */
    public void setLabels(){
        objName.setText(eObject.getName());
        objVersion.setText(eObject.getVersion().toString());
        objLength.setText(eObject.getLength().toString());
        objHeight.setText(eObject.getHeight().toString());
        objWidth.setText(eObject.getWidth().toString());
        objWeight.setText(eObject.getWeight().toString());
    }

    /**
     * Inserts placeholders that later will be converted to the actual value in the DB,
     * when the documentation is converted to
     * @param e ActionEvent is defined in the fxml. Method activates when button in GUI
     *          is pressed
     */
    public void insertPlaceholder(ActionEvent e) {
        String choice = ((Button) e.getSource()).getId();
        TextArea text = Main.getTextEditorController().getTextArea();
        int pos = Main.getTextEditorController().getTextArea().getCaretPosition();

        switch (choice){
            case "name":
                text.insertText(pos, "</xsl:text><xsl:value-of select=\"name\"/><xsl:text>");
        }
        switch (choice){
            case "version":
                text.insertText(pos, "</xsl:text><xsl:value-of select=\"version\"/><xsl:text>");
        }
        switch (choice){
            case "length":
                text.insertText(pos, "</xsl:text><xsl:value-of select=\"length\"/><xsl:text>");
        }
        switch (choice){
            case "height":
                text.insertText(pos, "</xsl:text><xsl:value-of select=\"height\"/><xsl:text>");
        }
        switch (choice){
            case "width":
                text.insertText(pos, "</xsl:text><xsl:value-of select=\"width\"/><xsl:text>");
        }
        switch (choice){
            case "weight":
                text.insertText(pos, "</xsl:text><xsl:value-of select=\"weight\"/><xsl:text>");
        }
    }

    /**
     * Updates the eObject labels in the GUI with the latest eObject values from DB
     */
    public void updateEObjectValues(){
        eObject = Main.getMainPageController().geteObject();
        setLabels();
    }

}
