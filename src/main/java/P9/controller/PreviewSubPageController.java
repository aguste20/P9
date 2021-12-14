package P9.controller;

import P9.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

//TODO Anne/cleanup: Mangler dokumentation

public class PreviewSubPageController implements Initializable {
    // ----- Properties -----
    // References to other controllers
    private PlaceholdersSubPageController placeholdersSubPageController;
    private TextEditorController textEditorController;

    // FXML Element
    @FXML private GridPane webGridPane;

    // ----- Getter -----
    public GridPane getWebGridPane() {
        return webGridPane;
    }

    /**
     * Method that sets references to other controllers
     * to be able to pass data between them
     */
    public void setControllers(){
        this.placeholdersSubPageController = Main.getPlaceholdersSubPageController();
        this.textEditorController = Main.getTextEditorController();
    }

    /**
     * This method initializes a controller after its root element has already been processed.
     * I think this means that this method is needed to keep content in the view pages updated visually.
     *
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }

    // ----- Instance methods -----
    /**
     * function used to create txt from textarea content
     * default start text is called through the function getXslText()
     * the existing xsl file is overwritten. The output form is sat to html by default
     * we load the converted xsl/xml content to the webview engine
     */
    public void createXslFromTextArea() {

        // Get textarea string
        String textAreaString = textEditorController.getTextArea().getText();

        // Create string with xsl document start tag
        String xslStartString = getXslText();

        // Create xml document end tag
        String xslEndString = " </xsl:text></span>    </xsl:template>\n" +
                "\n" +
                "</xsl:stylesheet>";

    // Concatenate file string
        String fileString = (xslStartString + textAreaString + xslEndString);
        String path = "src/main/resources/xml/style.xsl";

        overwriteFile(fileString, path);

        // Reload Webview to make sure that changes are displayed in preview
        Main.getEngine().reload();

    }


    //TODO Anne/cleanup: Hvorfor ligger den her her?
    int i = 0;

    /**
     * Converts html text to a txt that is ready to be saved as xsl
     * adds xsl:text start and end tags to the default html tags
     * changes placeholder value back to the selected value from the xml file
     * clears txt and inserts the new text
     */
    public void createTXTFromWebView() {
        //creates string from webview content
        String html = (String) Main.getEngine().executeScript("document.getElementById(\"mySpan\").innerHTML");

        String name = placeHolderReplacement(html, "<span id=\"name\"", "</span>");
        String version = placeHolderReplacement(html, "<span id=\"version\"", "</span>");
        String length = placeHolderReplacement(html, "<span id=\"length\"", "</span>");
        String height = placeHolderReplacement(html, "<span id=\"height\"", "</span>");
        String width = placeHolderReplacement(html, "<span id=\"width\"", "</span>");
        String weight = placeHolderReplacement(html, "<span id=\"weight\"", "</span>");

        //Modifies String html with correct header and placeholder values according to xsl
        String modifiedHTML = html
                .replaceAll("<h1>", "</xsl:text><h1>")
                .replaceAll("</h1>", "</h1><xsl:text>")
                .replaceAll("<h2>", "</xsl:text><h2>")
                .replaceAll("</h2>", "</h2><xsl:text>")
                .replaceAll("<i>", "</xsl:text><i>")
                .replaceAll("</i>", "</i><xsl:text>")
                .replaceAll("<b>", "</xsl:text><b>")
                .replaceAll("</b>", "</b><xsl:text>")
                .replaceAll("<u>", "</xsl:text><u>")
                .replaceAll("</u>", "</u><xsl:text>")
                .replaceAll("<p>", "</xsl:text><p>")
                .replaceAll("></p>", "/></p><xsl:text>")
                .replaceAll("<br>", "</xsl:text><br/><xsl:text>")
                .replaceAll(name, "</xsl:text><span id=\"name\" style=\"background-color: " + placeholdersSubPageController.getPhColor() + ";\"><xsl:value-of select=\"eObject/name\"/>")
                .replaceAll(version, "</xsl:text><span id=\"version\" style=\"background-color: "+ placeholdersSubPageController.getPhColor() +";\"><xsl:value-of select=\"eObject/version\"/>")
                .replaceAll(length, "</xsl:text><span id=\"length\" style=\"background-color: "+ placeholdersSubPageController.getPhColor() +";\"><xsl:value-of select=\"eObject/length\"/>")
                .replaceAll(height, "</xsl:text><span id=\"height\" style=\"background-color: "+ placeholdersSubPageController.getPhColor() +";\"><xsl:value-of select=\"eObject/height\"/>")
                .replaceAll(width, "</xsl:text><span id=\"width\" style=\"background-color: "+ placeholdersSubPageController.getPhColor() +";\"><xsl:value-of select=\"eObject/width\"/>")
                .replaceAll(weight, "</xsl:text><span id=\"weight\" style=\"background-color: "+ placeholdersSubPageController.getPhColor() +";\"><xsl:value-of select=\"eObject/weight\"/>")
                .replaceAll("<span style", "</xsl:text><span style")
                .replaceAll("</span>", "</span><xsl:text>");

        // Write to file with string
        String path = "src/main/resources/xml/webTxt.txt";
        overwriteFile(modifiedHTML, path);

        File file = new File(path);

        textEditorController.getTextArea().clear();
        textEditorController.readText(file);

    }


    // ----- Private instance methods -----
    /**
     * function called when converting html to txt/xsl/xml when we want to replace text inside tags
     * we find the start tag and the id from which we determine the replacement value
     * @param html string that contains the whole html content
     * @param startTag string that contains the start tag
     * @param closingTag string that contains the end tag
     * @return
     */
    private String placeHolderReplacement(String html, String startTag, String closingTag){
        // String to hold value of substring to be replaced.
        // Initialized to something that would never be found
        String change = "///%%%&&&";
        
        // Find index of matching occurrence, starting at index 0
        int index = html.indexOf(startTag);
        
        // If match is found (method returns -1 if no match)
        if (index >= 0){
            //Find end index of match
            int endIndex = html.indexOf(closingTag, index);

            // Store total substring that needs to be replaced
            change = html.substring(index, endIndex);
        }

        return change;
    }

    /**
     * contains default start text for the xsl file
     * output method is declared as html
     * two javascript functions is called. One, that temporary saves changes in engine. One that adds headers and formatting to buttons
     * @return
     */
    private String getXslText(){
        String xslText = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<xsl:stylesheet version=\"1.0\"\n" +
                "                xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                "    <xsl:output method=\"html\"/>\n" +
                "\n" +
                //the match attribute is an XPath expression "/" that matches the whole XML document
                "    <xsl:template match=\"/\">\n" +
                "        <script language=\"javascript\">\n" +
                //The saveChanges function temporary saves changes in the html file, while the application is open
                "            function saveChanges(){\n" +
                "            var xr = new XMLHttpRequest();\n" +
                //calls the php script saveNewText.php
                "            var url = \"saveNewText.php\";\n" +
                "            var text = document.getElementById(\"mySpan\").innerHTML;\n" +
                "            var vars = \"newText\"+text;\n" +
                "\n" +
                "            xr.open(\"POST\", url, true);\n" +
                //sets the request to the header and body of the html cotent
                "            xr.setRequestHeader(\"Content-type\", \"application/x-www-form-urlencoded\");\n" +
                //sends the body content of the http as a query string
                "            xr.send(vars)\n" +
                "            }\n" +
                "\n" +
                "        </script>\n" + "<script language=\"javascript\">\n" +
                "    borto = {\n" +
                //creates div element
                "    tmpEl: document.createElement('div'),\n" +
                //converts html buttons to dom elements to return child node
                "    htmlToDom: function(htmlEl){\n" +
                "    borto.tmpEl.innerHTML = htmlEl;\n" +
                "    return borto.tmpEl.children[0]\n" +
                "    },\n" +
                //wrap selection and extract contents
                "    wrapSelection: function(htmlEl){\n" +
                "    var sel = window.getSelection();\n" +
                "    for(var i = sel.rangeCount;i--;){\n" +
                "    var wrapper = borto.htmlToDom(htmlEl)\n" +
                "    var range = sel.getRangeAt(i);\n" +
                "    wrapper.appendChild(range.extractContents());\n" +
                "    range.insertNode(wrapper);\n" +
                "    }\n" +
                "    },\n" +
                //takes arguments that determines outcome
                "    command: (name,argument)=>{\n" +
                "    switch(name){\n" +
                "    case \"createLink\":\n" +
                "    break;case 'heading' :\n" +
                "    borto.wrapSelection('&lt;'+argument+'/>')\n" +
                "    return;\n" +
                "    }\n" +
                "    if(typeof argument === 'undefined') {\n" +
                "    argument = '';\n" +
                "    }\n" +
                "    document.execCommand(name, false, argument);\n" +
                "    }\n" +
                "    }\n" +
                "</script>\n" +
                "\n" +
                //Buttons are created used for formatting and applied javascript function that allows formatting
                "<button onclick=\"borto.command('bold')\">Bold</button>\n" +
                "<button onclick=\"borto.command('italic')\"><i>Italic</i></button>\n" +
                "<select onchange=\"borto.command('heading', this.value); this.selectedIndex = 0;\">\n" +
                "<option value=\"\">Typography</option>\n" +
                "<option value=\"h1\">Heading 1</option>\n" +
                "<option value=\"h2\">Heading 2</option>\n" +
                "</select>\n" +
                "\n" +       "<span id=\"mySpan\" onLoad='document.getElementById(\"mySpan\").focus()' contenteditable=\"true\" coloronblur=\"saveChanges()\"><?php include(\"myText.txt\"); ?> <xsl:text>";

        return  xslText;
    }

    //TODO Anne/cleanup: Mangler dokumentation
    private void overwriteFile(String fileString, String path){

        // Write to file with string
        File file = new File(path);

        try {
            PrintWriter savedText = new PrintWriter(file);
            BufferedWriter out = new BufferedWriter(savedText);
            out.write(fileString);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
