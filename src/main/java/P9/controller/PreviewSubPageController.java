package P9.controller;

import P9.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class PreviewSubPageController implements Initializable {

    @FXML
    public GridPane webGridPane;

    //private WebEngine engine;

    public GridPane getWebGridPane() {
        return webGridPane;
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

    public void overwriteFile(String fileString, String path){

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

    //TODO kommentarer
    public void createXslFromTextArea() {

        // Get textarea string
        String textAreaString = Main.getTextEditorController().getTextArea().getText();

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

    public String placeHolderReplacement(String html, String startTag, String closingTag){
        int index = 0;
        int endIndex = 0;
        String change = "&&&&////%%%¤¤";

        // Store index and keep looking for occurrences, while any text left
        while (index >= 0) {  // indexOf returns -1 if no match found

            // Update indexes to match indexes for next occurrence
            index = html.indexOf(startTag, index + startTag.length());
            endIndex = html.indexOf(closingTag, index);
            if (index>0) {
                change = html.substring(index, endIndex);
            }
        }
        return change;
    }

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
                .replaceAll("<br>", " ")
                .replaceAll(name, "</xsl:text><span id=\"name\"><xsl:value-of select=\"eObject/name\"/>")
                .replaceAll(version, "</xsl:text><span id=\"version\"><xsl:value-of select=\"eObject/version\"/>")
                .replaceAll(length, "</xsl:text><span id=\"length\"><xsl:value-of select=\"eObject/length\"/>")
                .replaceAll(height, "</xsl:text><span id=\"height\"><xsl:value-of select=\"eObject/height\"/>")
                .replaceAll(width, "</xsl:text><span id=\"width\"><xsl:value-of select=\"eObject/width\"/>")
                .replaceAll(weight, "</xsl:text><span id=\"weight\"><xsl:value-of select=\"eObject/weight\"/>")
                .replaceAll("</span>", "</span><xsl:text>");

        // Write to file with string
        String path = "src/main/resources/xml/webTxt.txt";
        overwriteFile(modifiedHTML, path);

        File file = new File(path);

        Main.getTextEditorController().getTextArea().clear();
        Main.getTextEditorController().readText(file);

    }

    public String getXslText(){
        String xslText = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
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
                "        </script>\n" + "<script language=\"javascript\">\n" +
                "    borto = {\n" +
                "    tmpEl: document.createElement('div'),\n" +
                "    /* create element like in jquery with string &lt;tag attribute1 attribute2 /> or &lt;tag attribute1>&lt;/tag> */\n" +
                "    htmlToDom: function(htmlEl){\n" +
                "    borto.tmpEl.innerHTML = htmlEl;\n" +
                "    return borto.tmpEl.children[0]\n" +
                "    },\n" +
                "    wrapSelection: function(htmlEl){\n" +
                "    var sel = window.getSelection();\n" +
                "    // In firefox we can select multiple area, so they are multiple range\n" +
                "    for(var i = sel.rangeCount;i--;){\n" +
                "    var wrapper = borto.htmlToDom(htmlEl)\n" +
                "    var range = sel.getRangeAt(i);\n" +
                "    wrapper.appendChild(range.extractContents());\n" +
                "    range.insertNode(wrapper);\n" +
                "    }\n" +
                "    },\n" +
                "    command: (name,argument)=>{\n" +
                "    switch(name){\n" +
                "    case \"createLink\":\n" +
                "    argument = prompt(\"Quelle est l'adresse du lien ?\");\n" +
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
                "<button onclick=\"borto.command('bold')\">Bold</button>\n" +
                "<button onclick=\"borto.command('italic')\"><i>I</i></button>\n" +
                "<button onclick=\"borto.command('underline')\"><u>U</u></button>\n" +
                "<button onclick=\"borto.command('createLink')\"><u>createLink</u></button>\n" +
                "<select onchange=\"borto.command('heading', this.value); this.selectedIndex = 0;\">\n" +
                "<option value=\"\">Title</option>\n" +
                "<option value=\"h1\">Title 1</option>\n" +
                "<option value=\"h2\">Title 2</option>\n" +
                "<option value=\"h3\">Title 3</option>\n" +
                "<option value=\"h4\">Title 4</option>\n" +
                "<option value=\"h5\">Title 5</option>\n" +
                "<option value=\"h6\">Title 6</option>\n" +
                "</select>\n" +
                "\n" +       "<span id=\"mySpan\" contenteditable=\"true\" onblur=\"saveChanges()\"><?php include(\"myText.txt\"); ?><xsl:text> ";

        return  xslText;
    }
}
