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
import java.util.Scanner;

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
                "    <xsl:template match=\"/\">\n" +
                "        <script language=\"javascript\">\n" +
                "            function saveChanges(){\n" +
                "            var xr = new XMLHttpRequest();\n" +
                "            var url = \"saveNewText.php\";\n" +
                "            var text = document.getElementById(\"mySpan\").innerHTML;\n" +
                "            var vars = \"newText\"+text;\n" +
                "\n" +
                "            xr.open(\"POST\", url, true);\n" +
                "            xr.setRequestHeader(\"Content-type\", \"application/x-www-form-urlencoded\");\n" +
                "            xr.send(vars)\n" +
                "            }\n" +
                "\n" +
                "        </script>\n" +
                "        <span id=\"mySpan\" contenteditable=\"true\" onblur=\"saveChanges()\"><?php include(\"myText.txt\"); ?><xsl:text> ";

        // Create xml document end tag
        String xslEndString = " </xsl:text></span>    </xsl:template>\n" +
                "\n" +
                "</xsl:stylesheet>";

        // Concatenate file string
        String fileString = (xslStartString + textAreaString + xslEndString);

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

    public void createTXTFromWebView(){

        //creates string from webview content
        String html = (String) Main.getEngine().executeScript("document.getElementById(\"mySpan\").innerHTML");

        //Modiefies String html
        String modifiedHTML = html.replaceAll("<h1>", "</xsl:text><h1>").replaceAll("</h1>", "</h1><xsl:text>").replaceAll("<h2>", "</xsl:text><h2>").replaceAll("</h2>", "</h2><xsl:text>");

        // Write to file with string
        File file = new File("src/main/resources/xml/webTxt.txt");

        try
        {
            PrintWriter savedText = new PrintWriter(file);
            BufferedWriter out = new BufferedWriter(savedText);
            out.write(modifiedHTML);
            out.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Main.getTextEditorController().getTextArea().clear();
        Main.getTextEditorController().readText(file);

    }

}
