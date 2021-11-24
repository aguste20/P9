package P9.controller;

import P9.Main;
import P9.model.EObjectDoc;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Region;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;

public class OverviewSubPageController implements Initializable {

    private EObjectDoc doc;
    private List<Header> headerList = new ArrayList<>();

    @FXML
    private TreeView tocView;

    //TODO kommentarer
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get reference to displayed doc
        doc = Main.getMainPageController().geteObject().getDoc();

        // Create table of contents
        updateToc();
    }


    //TODO kommentarer
    public void updateToc() {
        // Get text from doc from eObject
        String txt = doc.getXmlText();


        // Find indexes of all h1s
        findIndexesOfAllh1(txt);

        // Find all h1 headers
        //findAllH1(txt);

        /*
        List<String> headerList = findHeaders(txt, 1);

        for(String headerString : headerList){
            //addHeaderToAccordion(headerString);

            //TODO Anne: Find h2 for denne h1
            //TODO Anne: Tilføj accordion til h1-tp + tilføj h2-tp til accordion

            TitledPane tp2 = new TitledPane();
            tp2.setText("h2");

            Accordion h1Acc = new Accordion();
            h1Acc.getPanes().add(tp2);

            tp.setContent(h1Acc);
            */


    }



            /*
                //Parse text for headings with iterator, save in list
            CharacterIterator it = new StringCharacterIterator(txt);

            while (it.current() != CharacterIterator.DONE)
            {
                System.out.print(it.current());


                it.next();
            }
             */

    public List<Header> findAllH1(String txt){

        // List to return
        ArrayList<Header> headerList = new ArrayList<>();

        // Tags to look for
        String hTag = "<h1>";
        String hClosingTag = "</h1>";

        // Look for all h1's and save in list
        String[] h1s = StringUtils.substringsBetween(txt, hTag, hClosingTag);

        // Iterate over all h1 strings and save as header object,
        // starting from index 0 and incrementing as a new header is found
        int lookFrom = 0;
        for (String s : h1s){
            String match = "<h1>" + s + "</h1>";

            int matchIndex = txt.indexOf(s) - 4;

            match.equals(s);

            int index = txt.indexOf(s, lookFrom);

            Header h = new Header(s, index, (index + s.length()), 1);

            headerList.add(h);

            System.out.println("H1: " + h.txt + " at index: " + h.startIndex + " and end index: " + h.endIndex);

            lookFrom = index + s.length();
        }






        return headerList;


    }

    public void findIndexesOfAllh1(String text){
        ArrayList<Integer> list = new ArrayList<>();

        String hTag = "<h1>";
        String hClosingTag = "</h1>";

        int index = text.indexOf(hTag);
        int endIndex = text.indexOf(hClosingTag);
        while (index >= 0) {  // indexOf returns -1 if no match found
            Header h = new Header(index, endIndex);
            headerList.add(h);
            System.out.println("Header at range: " + h.startIndex + " - " + h.endIndex + ". Headertext: " + h.getHeaderText(text));
            index = text.indexOf(hTag, index + hTag.length());
            endIndex = text.indexOf(hClosingTag, index);
        }

    }





    //TODO kommentarer
    public List<String> findHeaders(String txt, int type) {

        ArrayList<String> headerList = new ArrayList<>();

        // Create tag strings to look for
        String hTag = ("<h" + type + ">");
        String hClosingTag = ("</h" + type + ">");

        // Parse string for substrings
        if (txt.contains(hTag)) {

            // String to be returned by the method
            String headerString = "";

            int j = 0;

            int firstIndex;
            int lastIndex;

            // Iterate over text, looking for headers
            for (int i = 0; i < txt.length(); i += j) {
                // Find index of the header string, looking from index j
                firstIndex = txt.indexOf(hTag, j);
                lastIndex = txt.indexOf(hClosingTag, j);

                // If no header found, break for loop
                if (firstIndex == -1) {
                    break;
                }

                // Get substring at specified index
                headerString = txt.substring(firstIndex + 4, lastIndex);

                headerList.add(headerString);

                // Increment j (index position to look from)
                j = lastIndex + 5;
            }

        }
        return headerList;
    }

    /*
    //TODO kommentarer
    public void addHeaderToAccordion(String header){
        // Create titled pane from found heading string
        TitledPane tp = new TitledPane();
        tp.setText(header);

        // Append new titled pane to accordion in scene
        tocAccordion.getPanes().add(tp);

    }

     */



    //TODO kommentarer
    private class Header {
        String txt;
        Integer startIndex;
        Integer endIndex;
        Integer type;

        public Header(Integer startIndex, Integer endIndex) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        public Header(String txt, Integer startIndex, Integer endIndex, Integer type) {
            this.txt = txt;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.type = type;
        }

        public String getHeaderText(String text){
            String htext = text.substring(startIndex, endIndex);

            String h = htext.replace("<h1>", "");
            h.replace("</h1>", "");

            return h;
        }
    }

}
