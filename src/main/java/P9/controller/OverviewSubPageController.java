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

public class OverviewSubPageController implements Initializable{

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

    private class Header{
        String txt;
        Integer startIndex;

        public Header(String txt, Integer startIndex) {
            this.txt = txt;
            this.startIndex = startIndex;
        }
    }

    Header h = new Header("Hej", 1);

    public void updateToc(){
        //Get text from doc from eobject
        String txt = doc.getXmlText();

        System.out.println(txt);

        // Parse string for substrings
        if(txt.contains("<h1>")){

            int i = 0;

            //Iterate over txt string
            int j = txt.indexOf("<h2>");
            System.out.println(j);
            int h = txt.indexOf("</h2>");
            System.out.println(h);

            String headerString = txt.substring(j+4, h);
            System.out.println(headerString);

            TitledPane tp = new TitledPane();
            tp.setText(headerString);

            tocAccordion.getPanes().add(tp);
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

        //create titledPane for each heading in list
    }





}
