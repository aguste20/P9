package P9.controller;

import P9.Main;
import P9.model.EObjectDoc;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.*;

public class OverviewSubPageController implements Initializable {

    private EObjectDoc doc;
    private List<Header> headerList = new ArrayList<>();
    private String theText;

    @FXML
    private TreeView<String> tocView;

    //TODO kommentarer
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get reference to displayed doc
        doc = Main.getMainPageController().geteObject().getDoc();
        theText = doc.getXmlText();

        createDummyRoot();

        // Create table of contents
        updateToc();
    }


    //TODO kommentarer
    public void updateToc() {
        // Get text from doc from eObject
        String txt = doc.getXmlText();

        // Find indexes of all h1s
        findIndexesOfAllh1(txt);
    }



    public void findIndexesOfAllh1(String text){
        String hTag = "<h1>";
        String hClosingTag = "</h1>";

        // Find indexes of first occurrence
        int index = text.indexOf(hTag);
        int endIndex = text.indexOf(hClosingTag);

        // Store index and keep looking for occurrences
        while (index >= 0) {  // indexOf returns -1 if no match found

            // Store index in header object
            Header h = new Header(index, endIndex);
            headerList.add(h);

            // Update indexes to match indexes for next occurrence
            index = text.indexOf(hTag, index + hTag.length());
            endIndex = text.indexOf(hClosingTag, index);

            // Store end index for the range that current h1 covers
            if(endIndex > h.startIndex){
                h.endIndex = index;
            }
            else{
                h.endIndex = text.length();
            }

            System.out.println("Header at range: " + h.startIndex + " - " + h.endTagIndex + ". Headertext: " + h.getHeaderText(text) + " ends at index: " + h.endIndex);

            addHeaderToTreeView(h, text);

            h.findAllH2(text);

        }

    }

    public void addHeaderToTreeView(Header h, String text){
        h.setTreeItem(new TreeItem<>(h.getHeaderText(text)));

        tocView.getRoot().getChildren().add(h.treeItem);
    }




    //TODO kommentarer
    private class Header {
        String txt;
        Integer startIndex; //Index of the first character in "<h1>"
        Integer endTagIndex; //Index of the first character in "</h1>"
        Integer endIndex; // Index of the next occurrence of "<h1>". Is set to final index position of text, if no more h1's
        Integer type; // Type of header h1 or h2
        TreeItem<String> treeItem;


        public Header(Integer startIndex, Integer endTagIndex) {
            this.startIndex = startIndex;
            this.endTagIndex = endTagIndex;
        }

        public Header(String txt, Integer startIndex, Integer endTagIndex, Integer type) {
            this.txt = txt;
            this.startIndex = startIndex;
            this.endTagIndex = endTagIndex;
            this.type = type;
        }

        public void setEndIndex(Integer endIndex) {
            this.endIndex = endIndex;
        }

        public void setTreeItem(TreeItem<String> treeItem) {
            this.treeItem = treeItem;
        }

        public String getHeaderText(String text){
            String htext = text.substring(startIndex, endTagIndex);

            String h = htext.replace("<h1>", "");
            h = h.replace("<h2>", "");

            return h;
        }

        public void findAllH2(String text){
            String substring = text.substring(startIndex, this.endIndex);

            String hTag = "<h2>";
            String hClosingTag = "</h2>";

            int index = text.indexOf(hTag, startIndex);
            int endIndex = text.indexOf(hClosingTag, startIndex);
            while (index >= 0 && index < this.endIndex) {  // indexOf returns -1 if no match found
                Header h2 = new Header(index, endIndex);

                index = text.indexOf(hTag, index + hTag.length());
                endIndex = text.indexOf(hClosingTag, index);

                System.out.println("Header at range: " + h2.startIndex + " - " + h2.endTagIndex + ". Headertext: " + h2.getHeaderText(text) + " ends at index: " + h2.endIndex);

                //Ad to treeview
                this.addH2ToTreeView(h2);

            }
        }

        public void addH2ToTreeView(Header h2){
            TreeItem<String> item = new TreeItem<>(h2.getHeaderText(theText));

            treeItem.getChildren().add(item);
        }
    }

    public void createDummyRoot(){
        TreeItem<String> contents = new TreeItem<>("Contents");
        tocView.setRoot(contents);
        contents.setExpanded(true);
    }

















    // ------------------- skrald -----------------

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

            System.out.println("H1: " + h.txt + " at index: " + h.startIndex + " and end index: " + h.endTagIndex);

            lookFrom = index + s.length();
        }






        return headerList;

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
