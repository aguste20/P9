package P9.controller;

import P9.Main;
import P9.model.EObjectDoc;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Region;

import java.net.URL;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;

public class OverviewSubPageController implements Initializable {

    private EObjectDoc doc;

    @FXML
    private Accordion tocAccordion;

    //TODO kommentarer
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get reference to displayed doc
        doc = Main.getMainPageController().geteObject().getDoc();

        // Create table of contents
        updateToc();
    }

    //TODO kommentarer
    private class Header {
        String txt;
        Integer startIndex;

        public Header(String txt, Integer startIndex) {
            this.txt = txt;
            this.startIndex = startIndex;
        }
    }

    Header h = new Header("Hej", 1);

    //TODO kommentarer
    public void updateToc() {
        // Get text from doc from eObject
        String txt = doc.getXmlText();

        // Find all h1 headers
        List<String> headerList = findHeaders(txt, 1);

        for(String headerString : headerList){
            addHeaderToAccordion(headerString);

            //TODO Anne: Find h2 for denne h1
            //TODO Anne: Tilføj accordion til h1-tp + tilføj h2-tp til accordion
            /*
            TitledPane tp2 = new TitledPane();
            tp2.setText("h2");

            Accordion h1Acc = new Accordion();
            h1Acc.getPanes().add(tp2);

            tp.setContent(h1Acc);
             */



        }

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

    //TODO kommentarer
    public void addHeaderToAccordion(String header){
        // Create titled pane from found heading string
        TitledPane tp = new TitledPane();
        tp.setText(header);

        // Append new titled pane to accordion in scene
        tocAccordion.getPanes().add(tp);

    }

}
