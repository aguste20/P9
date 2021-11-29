package P9.controller;

import P9.Main;
import P9.model.EObject;
import P9.persistence.Setup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public void callLabelsNull(){
        setLabelsVisible(objName, objVersion, objLength, objHeight, objWidth, objWeight);
    }

    public void setLabelsVisible(Node... nodes){
        for (Node node : nodes){
            if (!Main.getTextEditorController().getCreatingDoc()){
                node.setVisible(false);
            }
            else {
                node.setVisible(true);
            }
        }
        System.out.println("Hej med dig");
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

            boolean textEditorActive = Main.getTextEditorController().isTextEditorActive();
            System.out.println(textEditorActive);


            switch (choice) {
                case "name":
                    if (textEditorActive) {
                        text.insertText(pos, "</xsl:text><span id=\"name\" style=\"color: yellow;\"><xsl:value-of select=\"eObject/name\"/></span><xsl:text>");
                    }else{insertPlaceholderInHtml(eObject.getName(), "name");}
            }
            switch (choice) {
                case "version":
                    if (textEditorActive) {
                        text.insertText(pos, "</xsl:text><span id=\"version\" style=\"color: yellow;\"><xsl:value-of select=\"eObject/version\"/></span><xsl:text>");
                    }else{insertPlaceholderInHtml(eObject.getVersion().toString(), "version");}
            }
            switch (choice) {
                case "length":
                    if (textEditorActive) {
                    text.insertText(pos, "</xsl:text><span id=\"length\" style=\"color: yellow;\"><xsl:value-of select=\"eObject/length\"/></span><xsl:text>");
                    }else{insertPlaceholderInHtml(eObject.getLength().toString(), "length");}
            }
            switch (choice) {
                case "height":
                    if (textEditorActive) {
                    text.insertText(pos, "</xsl:text><span id=\"height\" style=\"color: yellow;\"><xsl:value-of select=\"eObject/height\"/></span><xsl:text>");
                    }else{insertPlaceholderInHtml(eObject.getHeight().toString(), "height");}
            }
            switch (choice) {
                case "width":
                    if (textEditorActive) {
                    text.insertText(pos, "</xsl:text><span id=\"width\" style=\"color: yellow;\"><xsl:value-of select=\"eObject/width\"/></span><xsl:text>");
                    }else{insertPlaceholderInHtml(eObject.getWidth().toString(), "width");}
            }
            switch (choice) {
                case "weight":
                    if (textEditorActive) {
                    text.insertText(pos, "</xsl:text><span id=\"weight\" style=\"color: yellow;\"><xsl:value-of select=\"eObject/weight\"/></span><xsl:text>");
                    }else{insertPlaceholderInHtml(eObject.getWeight().toString(), "weight");}
            }

            }

    public void insertPlaceholderInHtml(String ph, String pn){

        Main.getEngine().executeScript("var range = window.getSelection().getRangeAt(0);\n" +
                "var selectionContents = range.extractContents();\n" +
                "var span = document.createElement(\"span\");\n" +
                "span.setAttribute(\"id\",\"" + pn + "\");\n" +
                "span.style.color = \"yellow\";\n" +
                "span.textContent = \"" + ph + "\";\n" +
                "span.appendChild(selectionContents);\n" +
                "range.insertNode(span);");
    }

    /**
     * Updates the eObject labels in the GUI with the latest eObject values from DB
     */
    public void updateEObjectValues(){
        eObject = Main.getMainPageController().geteObject();
        callLabelsNull();
        setLabels();
    }

}
