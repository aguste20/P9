package P9.controller;

import P9.Main;
import P9.model.EObjectDoc;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

import java.net.URL;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ResourceBundle;

public class OverviewSubPageController implements Initializable {

    private EObjectDoc doc;

    @FXML
    private Accordion tocAccordion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get reference to displayed doc
        doc = Main.getMainPageController().geteObject().getDoc();

        // Create table of contents
        updateToc();
    }

    private class Header {
        String txt;
        Integer startIndex;

        public Header(String txt, Integer startIndex) {
            this.txt = txt;
            this.startIndex = startIndex;
        }
    }

    Header h = new Header("Hej", 1);

    public void updateToc() {


        String headerString = getHeader("1");



            /*
                //Parse text for headings with iterator, save in list
            CharacterIterator it = new StringCharacterIterator(txt);

            while (it.current() != CharacterIterator.DONE)
            {
                System.out.print(it.current());


                it.next();
            }
             */

    }


    public String getHeader(String type) {
        // String to be returned by the method
        String headerString = "";

        // Get text from doc from eObject
        String txt = doc.getXmlText();

        // Create tags strings to look for
        String hTag = ("<h" + type + ">");
        String hClosingTag = ("</h" + type + ">");

        // Parse string for substrings
        if (txt.contains(hTag)) {

            int j = 0;

            int firstIndex;
            int lastIndex;

            // Iterate over text, looking for headers
            for (int i = 0; i < txt.length(); i += j) {
                // Find index of the header string, looking from index j
                firstIndex = txt.indexOf(hTag, j);
                lastIndex = txt.indexOf(hClosingTag, j);

                // If no header found, break fo loop
                if(firstIndex == -1){
                    break;
                }

                // Get substring at specified index
                headerString = txt.substring(firstIndex + 4, lastIndex);

                // Create titled pane from found heading string
                TitledPane tp = new TitledPane();
                tp.setText(headerString);

                // Append new titled pane to accordion in scene
                tocAccordion.getPanes().add(tp);

                // Increment j (index position to look from)
                j = lastIndex + 5;
            }

        }

        return headerString;

    }

}
