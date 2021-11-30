package P9.controller;

import P9.Main;
import P9.model.EObject;
import P9.persistence.Setup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

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

    public String phColor = "yellow";

    @FXML
    private TextField imageTextField;

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
                        text.insertText(pos, "</xsl:text><span id=\"name\" style=\"background-color: " + phColor + ";\"><xsl:value-of select=\"eObject/name\"/></span><xsl:text>");
                    }else{insertPlaceholderInHtml(eObject.getName(), "name",  phColor);}
            }
            switch (choice) {
                case "version":
                    if (textEditorActive) {
                        text.insertText(pos, "</xsl:text><span id=\"version\" style=\"background-color: " + phColor + ";\"><xsl:value-of select=\"eObject/version\"/></span><xsl:text>");
                    }else{insertPlaceholderInHtml(eObject.getVersion().toString(), "version", phColor);}
            }
            switch (choice) {
                case "length":
                    if (textEditorActive) {
                    text.insertText(pos, "</xsl:text><span id=\"length\" style=\"background-color: " + phColor + ";\"><xsl:value-of select=\"eObject/length\"/></span><xsl:text>");
                    }else{insertPlaceholderInHtml(eObject.getLength().toString(), "length", phColor);}
            }
            switch (choice) {
                case "height":
                    if (textEditorActive) {
                    text.insertText(pos, "</xsl:text><span id=\"height\" style=\"background-color: " + phColor + ";\"><xsl:value-of select=\"eObject/height\"/></span><xsl:text>");
                    }else{insertPlaceholderInHtml(eObject.getHeight().toString(), "height", phColor);}
            }
            switch (choice) {
                case "width":
                    if (textEditorActive) {
                    text.insertText(pos, "</xsl:text><span id=\"width\" style=\"background-color: " + phColor + ";\"><xsl:value-of select=\"eObject/width\"/></span><xsl:text>");
                    }else{insertPlaceholderInHtml(eObject.getWidth().toString(), "width", phColor);}
            }
            switch (choice) {
                case "weight":
                    if (textEditorActive) {
                    text.insertText(pos, "</xsl:text><span id=\"weight\" style=\"background-color: " + phColor + ";\"><xsl:value-of select=\"eObject/weight\"/></span><xsl:text>");
                    }else{insertPlaceholderInHtml(eObject.getWeight().toString(), "weight", phColor);}
            }
            switch (choice){
                case "image":
                    if (textEditorActive){
                        text.insertText(pos, "</xsl:text><p><img src=\"" + imageTextField.getText() + "\"/></p><xsl:text>");
                    }
                    else{insertImage(imageTextField.getText());}
                    //insertImage("https://tailandfur.com/wp-content/uploads/2014/03/Funny-pictures-of-animals-41.jpg");
            }

            }

    public void insertPlaceholderInHtml(String ph, String pn, String color){
        //runs script that inserts placeholder value inside span tags with id span
        //the placeholder gets inserted at caret position or start of selection
        //a background color is applied. the function is called when clicking at placeholder buttons
        Main.getEngine().executeScript("var range = window.getSelection().getRangeAt(0);\n" +
                "var selectionContents = range.extractContents();\n" +
                "var span = document.createElement(\"span\");\n" +
                "span.setAttribute(\"id\",\"" + pn + "\");\n" +
                "span.style.backgroundColor = \"" + color + "\";\n" +
                "span.textContent = \"" + ph + "\";\n" +
                "span.appendChild(selectionContents);\n" +
                "range.insertNode(span);");
    }

    public void insertImage(String src){
        //runs script to insert picture at caret position.
        //the picture is inserted inside p tags as a string containing the image source
        Main.getEngine().executeScript("var range = window.getSelection().getRangeAt(0);\n" +
                "var image = '<p><img src=" + src + " /></p>';\n" +
                "node = range.createContextualFragment(image);\n" +
                "range.insertNode(node);");
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
