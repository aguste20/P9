package P9.controller;

import P9.Main;
import P9.model.EObjectDoc;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.*;

public class OverviewSubPageController implements Initializable {

    private List<Header> headerList = new ArrayList<>();
    private String text; // Text from the textarea

    @FXML
    private Button refreshTocButton;

    @FXML
    private TreeView<String> tocView;


    /**
     * Method that initializes a contoller object after its root element has been loaded.
     * Inherited from Initializable.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get reference to displayed doc
        createTocTreeViewRoot();

        // Create table of contents
        updateToc();
    }


    /**
     * Method that updates the table of contents
     */
    public void updateToc() {
        // Clear previous table of content
        tocView.getRoot().getChildren().clear();

        // Get from text area
        text = Main.getTextEditorController().getTextArea().getText();

        // Tags to look for
        String hTag = "<h1>";
        String hClosingTag = "</h1>";

        // Find indexes of first occurrence in text
        int index = text.indexOf(hTag);
        int endIndex = text.indexOf(hClosingTag);

        // Store index and keep looking for occurrences, while any text left
        while (index >= 0) {  // indexOf returns -1 if no match found

            // Store index in new header object
            Header h = new Header(index, endIndex);
            headerList.add(h); //TODO anne: gør ikke noget lige nu

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

            System.out.println("Header at range: " + h.startIndex + " - " + h.endTagIndex + ". Headertext: " + h.getHeaderText() + " ends at index: " + h.endIndex);

            addHeaderToTreeView(h);

            h.findAllH2(text);
        }
    }

    /**
     * Method that adds a header to the toc tree view element
     * @param h header to be added
     */
    public void addHeaderToTreeView(Header h){
        h.setTreeItem(new TreeItem<>(h.getHeaderText()));
        tocView.getRoot().getChildren().add(h.treeItem);
    }

    /**
     * Method that creates a root for the toc tree view
     */
    public void createTocTreeViewRoot(){
        TreeItem<String> contents = new TreeItem<>("Contents");
        tocView.setRoot(contents);
        contents.setExpanded(true);
    }


    /**
     * Inner class that represents a header
     * Used to store header index position and treeItem in treeView
     */
    private class Header {
        Integer startIndex; //Index of the first character in "<h1>"
        Integer endTagIndex; //Index of the first character in "</h1>"
        Integer endIndex; // Index of the next occurrence of "<h1>". Is set to final index position of text, if no more h1's
        TreeItem<String> treeItem; // The headers treeitem in the tree view

        public Header(Integer startIndex, Integer endTagIndex) {
            this.startIndex = startIndex;
            this.endTagIndex = endTagIndex;
        }

        public void setEndIndex(Integer endIndex) {
            this.endIndex = endIndex;
        }

        public void setTreeItem(TreeItem<String> treeItem) {
            this.treeItem = treeItem;
        }

        public String getHeaderText(){
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

                System.out.println("Header at range: " + h2.startIndex + " - " + h2.endTagIndex + ". Headertext: " + h2.getHeaderText() + " ends at index: " + h2.endIndex);

                //Ad to treeview
                this.addH2ToTreeView(h2);

            }
        }

        public void addH2ToTreeView(Header h2){
            TreeItem<String> item = new TreeItem<>(h2.getHeaderText());
            treeItem.getChildren().add(item);
        }
    }



}
