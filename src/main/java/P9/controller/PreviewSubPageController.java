package P9.controller;

import P9.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class PreviewSubPageController implements Initializable {

    @FXML
    public GridPane webGridPane;

    //private WebEngine engine;

    public GridPane getWebGridPane() { return webGridPane; }

    /**
     * This method initializes a controller after its root element has already been processed.
     * I think this means that this method is needed to keep content in the view pages updated visually.
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
    }

    //TODO kommentarer
    public void createXslFromTextArea() {

        // Get textarea string
        String textAreaString = Main.getTextEditorController().getTextArea().getText();

        // Create string with xsl document start tag
        String xslStartString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<xsl:stylesheet version=\"1.0\"\n" +
                "                xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                "    <xsl:output method=\"html\"/>\n" +
                "\n" +
                "    <xsl:template match=\"/\">";

        // Create xml document end tag
        String xslEndString = "    </xsl:template>\n" +
                "\n" +
                "</xsl:stylesheet>";

        // Concatenate file string
        String fileString = (xslStartString + "<span contenteditable=\"true\"><xsl:text>" + textAreaString + "</xsl:text></span>" + xslEndString);

        // Write to file with string
        File file = new File("src/main/resources/xml/style.xsl");

        try
        {
            PrintWriter savedText = new PrintWriter(file);
            BufferedWriter out = new BufferedWriter(savedText);
            out.write(fileString);
            out.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Reload Webview to make sure that changes are displayed in preview
        Main.getEngine().reload();

    }

}
