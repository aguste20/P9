package P9.controller;

import P9.Main;
import P9.model.EObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class PlaceholdersSubPageController implements Initializable {

    // References to other controllers
    private ContentsSubPageController contentsSubPageController;
    private MainPageController mainPageController;
    private OverviewSubPageController overviewSubPageController;
    private PlaceholdersSubPageController placeholdersSubPageController;
    private PreviewSubPageController previewSubPageController;
    private RegisterNewContentBlockController registerNewContentBlockController;
    private TextEditorController textEditorController;

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

    private EObject eObject;

    public void seteObject(EObject eObject) {
        this.eObject = eObject;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Adds values from the selected eObject to the labels next to buttons
     */
    public void setLabels(){
        eObject = mainPageController.geteObject();

        objName.setText(eObject.getName());
        objVersion.setText(eObject.getVersion().toString());
        objLength.setText(eObject.getLength().toString());
        objHeight.setText(eObject.getHeight().toString());
        objWidth.setText(eObject.getWidth().toString());
        objWeight.setText(eObject.getWeight().toString());
    }

    /**
     * Method used to call setLabelsVisible with the correct arguments
     */
    public void callLabelsVisible(){
        setLabelsVisible(objName, objVersion, objLength, objHeight, objWidth, objWeight);
    }

    /**
     * Changes the visibility of the labels displaying the eObject's values, depending on the
     * creating doc variable from TextEditorController
     * @param nodes The method can take an unlimited number of arguments of type node
     */
    public void setLabelsVisible(Node... nodes){
        for (Node node : nodes){
            if (!textEditorController.getCreatingDoc()){
                node.setVisible(false);
            }
            else {
                node.setVisible(true);
            }
        }
    }


    /**
     * Inserts placeholders that later will be converted to the actual value in the DB,
     * when the documentation is converted to either xsl or html determined by value of boolean textEditorActive
     * @param e ActionEvent is defined in the fxml. Method activates when button in GUI
     *          is pressed
     */
    public void insertPlaceholder(ActionEvent e) {
        String choice = ((Button) e.getSource()).getId();

            TextArea text = textEditorController.getTextArea();
            int pos = textEditorController.getTextArea().getCaretPosition();

            boolean textEditorActive = textEditorController.isTextEditorActive();
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
                        text.insertText(pos, "</xsl:text><p><img src=\"" + imageTextField.getText() + "\" width=\"500\"/></p><xsl:text>");
                    }
                    else{insertImage(imageTextField.getText());}
                    //insertImage("https://tailandfur.com/wp-content/uploads/2014/03/Funny-pictures-of-animals-41.jpg");
            }

            }

    /**
     * runs script that inserts placeholder value inside span tags with id span
     * the placeholder gets inserted at caret position or start of selection
     * a background color is applied. the function is called when clicking at placeholder buttons
     * @param ph placeholder value
     * @param pn placeholder name, used as id
     * @param color background color for placeholder in html. Making it recognisable
     */
    public void insertPlaceholderInHtml(String ph, String pn, String color){
        Main.getEngine().executeScript("var range = window.getSelection().getRangeAt(0);\n" +
                "var selectionContents = range.extractContents();\n" +
                "var span = document.createElement(\"span\");\n" +
                "span.setAttribute(\"id\",\"" + pn + "\");\n" +
                "span.style.backgroundColor = \"" + color + "\";\n" +
                "span.textContent = \"" + ph + "\";\n" +
                "span.appendChild(selectionContents);\n" +
                "range.insertNode(span);");
    }

    /**
     * runs script to insert picture at caret position.
     * the picture is inserted inside p tags as a string containing the image source
     * @param src image source
     */
    public void insertImage(String src){

        Main.getEngine().executeScript("var range = window.getSelection().getRangeAt(0);\n" +
                "var image = '<p><img src=" + src + " width=\"500\"/></p>';\n" +
                "node = range.createContextualFragment(image);\n" +
                "range.insertNode(node);");
    }

    /**
     * Updates the eObject labels in the GUI with the latest eObject values from DB
     */
    public void updateEObjectValues(){
        eObject = mainPageController.geteObject();
        callLabelsVisible();
        setLabels();
    }

    /**
     * Method that gets references to other controllers
     * to be able to pass data between them
     */
    public void setControllers(){
        this.contentsSubPageController = Main.getContentsSubPageController();
        this.mainPageController = Main.getMainPageController();
        this.overviewSubPageController = Main.getOverviewSubPageController();
        this.placeholdersSubPageController = Main.getPlaceholdersSubPageController();
        this.previewSubPageController = Main.getPreviewSubPageController();
        this.registerNewContentBlockController = Main.getRegisterNewContentBlockController();
        this.textEditorController = Main.getTextEditorController();
    }

}
