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

//TODO Anne/cleanup: Mangler dokumentation

public class PlaceholdersSubPageController implements Initializable {
    // ----- Properties -----
    // References to other controllers
    private ContentsSubPageController contentsSubPageController;
    private MainPageController mainPageController;
    private OverviewSubPageController overviewSubPageController;
    private PlaceholdersSubPageController placeholdersSubPageController;
    private PreviewSubPageController previewSubPageController;
    private RegisterNewContentBlockController registerNewContentBlockController;
    private TextEditorController textEditorController;

    // FXML elements
    @FXML private Label objName;
    @FXML private Label objVersion;
    @FXML private Label objLength;
    @FXML private Label objHeight;
    @FXML private Label objWidth;
    @FXML private Label objWeight;
    @FXML private Label objComponents;
    @FXML private TextField imageTextField;

    private String phColor; // Background color for placeholders in preview
    private EObject eObject; // EObject the user is currently working on


    // ----- Getters and setters -----
    public void seteObject(EObject eObject) {
        this.eObject = eObject;
    }
    public String getPhColor() {
        return phColor;
    }

    /**
     * Method that sets references to other controllers
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        phColor = "yellow";
    }

    // ----- Instance methods -----
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
        objComponents.setText(eObject.componentNames().replaceAll(" ", "\n"));
    }

    /**
     * Method used to call setLabelsVisible with the correct arguments
     */
    public void callLabelsVisible(){
        setLabelsVisible(objName, objVersion, objLength, objHeight, objWidth, objWeight);
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

    // ----- Private instance methods -----
    /**
     * Changes the visibility of the labels displaying the eObject's values, depending on the
     * creating doc variable from TextEditorController
     * @param nodes The method can take an unlimited number of arguments of type node
     */
    private void setLabelsVisible(Node... nodes){
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
    @FXML
    private void insertPlaceholder(ActionEvent e) {
        String choice = ((Button) e.getSource()).getId();

        TextArea text = textEditorController.getTextArea();
        int pos = text.getCaretPosition();

        boolean sourceTextActive = textEditorController.isSourceTextActive();

        switch (choice) {
            case "name":
                if (sourceTextActive) {
                    text.insertText(pos, "</xsl:text><span id=\"name\" style=\"background-color: " + phColor + ";\"><xsl:value-of select=\"eObject/name\"/></span><xsl:text>");
                } else {
                    insertPlaceholderInHtml(eObject.getName(), "name", phColor);
                }
                break;
            case "version":
                if (sourceTextActive) {
                    text.insertText(pos, "</xsl:text><span id=\"version\" style=\"background-color: " + phColor + ";\"><xsl:value-of select=\"eObject/version\"/></span><xsl:text>");
                } else {
                    insertPlaceholderInHtml(eObject.getVersion().toString(), "version", phColor);
                }
                break;
            case "length":
                if (sourceTextActive) {
                    text.insertText(pos, "</xsl:text><span id=\"length\" style=\"background-color: " + phColor + ";\"><xsl:value-of select=\"eObject/length\"/></span><xsl:text>");
                } else {
                    insertPlaceholderInHtml(eObject.getLength().toString(), "length", phColor);
                }
                break;
            case "height":
                if (sourceTextActive) {
                    text.insertText(pos, "</xsl:text><span id=\"height\" style=\"background-color: " + phColor + ";\"><xsl:value-of select=\"eObject/height\"/></span><xsl:text>");
                } else {
                    insertPlaceholderInHtml(eObject.getHeight().toString(), "height", phColor);
                }
                break;
            case "width":
                if (sourceTextActive) {
                    text.insertText(pos, "</xsl:text><span id=\"width\" style=\"background-color: " + phColor + ";\"><xsl:value-of select=\"eObject/width\"/></span><xsl:text>");
                } else {
                    insertPlaceholderInHtml(eObject.getWidth().toString(), "width", phColor);
                }
                break;
            case "weight":
                if (sourceTextActive) {
                    text.insertText(pos, "</xsl:text><span id=\"weight\" style=\"background-color: " + phColor + ";\"><xsl:value-of select=\"eObject/weight\"/></span><xsl:text>");
                } else {
                    insertPlaceholderInHtml(eObject.getWeight().toString(), "weight", phColor);
                }
                break;
            case "image":
                if (sourceTextActive) {
                    text.insertText(pos, "</xsl:text><p><img src=\"" + imageTextField.getText() + "\" width=\"500\"/></p><xsl:text>");
                } else {
                    insertImage(imageTextField.getText());
                }
                break;
            //insertImage("https://tailandfur.com/wp-content/uploads/2014/03/Funny-pictures-of-animals-41.jpg")
            case "components":
                if (sourceTextActive) {
                    // TODO next iteration: Insert in text area
                } else {
                    // TODO next iteration: Insert in html
                }
                break;
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
    private void insertPlaceholderInHtml(String ph, String pn, String color){
        Main.getEngine().executeScript("var range = window.getSelection().getRangeAt(0);" +
                "var selectionContents = range.extractContents();" +
                "var span = document.createElement(\"span\");" +
                "span.setAttribute(\"id\",\"" + pn + "\");" +
                "span.style.backgroundColor = \"" + color + "\";" +
                "span.textContent = \"" + ph + "\";" +
                "span.appendChild(selectionContents);" +
                "range.insertNode(span);");
    }
}
