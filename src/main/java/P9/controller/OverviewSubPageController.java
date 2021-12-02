package P9.controller;

import P9.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.web.WebView;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.*;

public class OverviewSubPageController implements Initializable {

    // References to other controllers
    private ContentsSubPageController contentsSubPageController;
    private OverviewSubPageController overviewSubPageController;
    private PlaceholdersSubPageController placeholdersSubPageController;
    private PreviewSubPageController previewSubPageController;
    private RegisterNewContentBlockController registerNewContentBlockController;
    private TextEditorController textEditorController;

    private List<Header> allH1s = new ArrayList<>(); // All h1 headers in the text
    private List<Header> allH2s = new ArrayList<>(); // All h2 headers in the text
    private String text; // Text from the textarea

    @FXML
    private Button refreshTocButton; // Button to refresh the table of contents

    @FXML
    private TreeView<Header> tocView; // The tree view that holds all header elements

    /**
     * Method that initializes a controller object after its root element has been loaded.
     * Inherited from Initializable.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Prepare table of contents tree view
        prepareToc();
    }

    /**
     * Helper method that prepares the toc tree view
     * by creating a root node (mandatory)
     * and sets a cell factory for the tree view cells
     */
    private void prepareToc() {
        // Create root for toc tree view
        createTocTreeViewRoot();

        // Set cell factory on tree view, so it displays header strings in its cells
        setCellFactoryOnTocView();
    }


    /**
     * Method that updates the table of contents
     */
    public void updateToc() {
        allH1s.clear();
        allH2s.clear();

        // Clear previous table of content
        tocView.getRoot().getChildren().clear();

        // Get text from text area
        text = textEditorController.getTextArea().getText();

        //Find all h1 - add to list
        allH1s = findAllH(1);

        //For every h1 in list
        for (Header h : allH1s){
            //Find all h2 for the parent header - add to list for the current h
            h.h2List.addAll(findAllH(2, h));

            // Add header's local h2 list to list with all h2's
            allH2s.addAll(h.h2List);

            // Create tree item for h1 and add to tree view
            h.headerToTreeItem();
            tocView.getRoot().getChildren().add(h.treeItem);
            h.treeItem.setExpanded(true);
            h.treeItem.getChildren().clear();

            // For every h2 in h1's list
            for (Header h2 : h.h2List){
                // Create tree item for h2 and add to h1 tree item
                h2.headerToTreeItem();
                h.treeItem.getChildren().add(h2.treeItem);
            }
        }
    }


    /**
     * Overloaded method that returns a list of headers for the type
     * Should only be used for H1
     * @param type Type of header to be found (1 or 2)
     * @return list of header objects
     */
    private List<Header> findAllH(Integer type){
        // Dummy parent header
        Header dummyHeader = new Header(0,0, text.length());

        // Call overloaded method
        return findAllH(type, dummyHeader);
    }

    /**
     * Overloaded method that returns a list of Header objects for the given type
     * @param type Type of header to be found (1 or 2)
     * @param parentH Parent header
     * @return
     */
    private List<Header> findAllH(Integer type, Header parentH){
        // List to return
        List<Header> h1List = new ArrayList<>();

        // Tags to look for
        String hTag = "<h" + type + ">";
        String hClosingTag = "</h" + type + ">";

        // Find indexes of first occurrence in text
        int index = text.indexOf(hTag, parentH.startIndex);
        int endIndex = text.indexOf(hClosingTag, parentH.startIndex);

        // Store index and keep looking for occurrences, while any text left
        while (index >= 0 && index < parentH.endIndex) {  // indexOf returns -1 if no match found

            // Store index in new header object
            Header h = new Header(index, endIndex);
            h.setType(type);
            h1List.add(h);

            // Update indexes to match indexes for next occurrence
            index = text.indexOf(hTag, index + hTag.length());
            endIndex = text.indexOf(hClosingTag, index);

            // Store end index for the range that current header covers
            if (endIndex > h.startIndex) {
                h.endIndex = index;
            } else {
                h.endIndex = parentH.endIndex;
            }

        }

        return h1List;
    }

    /**
     * Method that creates a root for the toc tree view
     */
    private void createTocTreeViewRoot(){
        Header h = new Header(0,0);
        h.setHeaderText("Contents");

        TreeItem<Header> contents = new TreeItem<>(h);

        tocView.setRoot(contents);
        contents.setExpanded(true);
    }

    /**
     * Method that sets a cell factory the toc tree view.
     * Enables tree view to hold header objects, and display the header text
     * as the visible tree item
     */
    private void setCellFactoryOnTocView(){
        tocView.setCellFactory(param -> new TextFieldTreeCell<>(new StringConverter<>() {

                    // Displays header text as tree item string
                    @Override
                    public String toString(Header header) {
                        return header.getHeaderText();
                    }

                    // Not needed, so no change in implemented method
                    @Override
                    public Header fromString(String s) {
                        return null;
                    }
                })
        );
    }


    /**
     * Event handler for toc tree view.
     * Is called when user clicks item in tree view.
     * Moves cursor to selected header in active window
     */
    @FXML
    private void setCursorToSelectedHeader(){
        //Get selected header
        Header h = tocView.getSelectionModel().getSelectedItem().getValue();

        // Move cursor to either text area window or preview window
        if(textEditorController.isTextEditorActive()){ // If text editor window active
            //Move cursor to selected header in text area
            setCursorInTextAreaAtIndex(h.startIndex);
        }
        else{ // If preview window active

            int type = h.type;
            // Move cursor to selected header in preview
            if(type == 1){
                setCursorInPreviewAtIndex(type, allH1s.indexOf(h));
            }else if (type == 2) {
                setCursorInPreviewAtIndex(type, allH2s.indexOf(h));
            }
        }
    }

    /**
     * Helper method that sets cursor in text area based on passed index
     * @param index Index to move cursor to
     */
    private void setCursorInTextAreaAtIndex(int index){
        // Get textarea from text editor
        TextArea textArea = textEditorController.getTextArea();

        // Request window focus
        textArea.requestFocus();

        // Move cursor position to start index for selected header
        textArea.positionCaret(index);
    }

    /**
     * Helper method that sets cursor in preview window based on passed type of header and index of header
     * @param type Type of header to look for
     * @param index Index of the header in the total list of headers (either allH1s or allH2s)
     */
    private void setCursorInPreviewAtIndex(int type, int index){
        // Get webview/webengine
        WebView webview = Main.getWebview();

        //Request window focus
        String focusScript = "document.getElementById(\"mySpan\").focus()";
        webview.getEngine().executeScript(focusScript);
        webview.requestFocus();

        //Move cursor to index in html using beautiful javascript
        webview.getEngine().executeScript("function placeCaretAtEnd(el) {" +
                "    if (typeof window.getSelection != \"undefined\"" +
                "            && typeof document.createRange != \"undefined\") {" +
                "        var range = document.createRange();" +
                "        range.selectNodeContents(el);" +
                "        range.collapse(false);" +
                "        var sel = window.getSelection();" +
                "        sel.removeAllRanges();" +
                "        sel.addRange(range);" +
                "    } else if (typeof document.body.createTextRange != \"undefined\") {" +
                "        var textRange = document.body.createTextRange();" +
                "        textRange.moveToElementText(el);" +
                "        textRange.collapse(false);" +
                "        textRange.select();" +
                "    }" +
                "}" +
                "" +
                "placeCaretAtEnd( document.querySelectorAll('h"+ type +"').item("+ index +") );");
    }


    /**
     * Inner class that represents a header
     * Used to store header index position and treeItem in treeView
     */
    private class Header {
        private Integer startIndex; //Index of the first character in tag (eg. "<h1>")
        private Integer endTagIndex; //Index of the first character in closing tag (eg. "</h1>")
        private Integer endIndex; // Index of the next occurrence of header of same type. Is set to final index position of text, if no more h1's
        private int type; // Type of header (1 or 2)
        private List<Header> h2List; // List of the headers subheadings, if any
        private TreeItem<Header> treeItem; // The headers treeitem in the tree view
        private String treeItemText; // Text to be displayed in toc treeview if header doesn't have text (for dummy root item only)

        /**
         * Constructs a header object from the passed start index and end tag index
         * @param startIndex Index of the first character in the headers tag
         * @param endTagIndex Index of the first character in the headers closing tag
         */
        public Header(Integer startIndex, Integer endTagIndex) {
            this(startIndex, endTagIndex, 0);
        }

        /**
         * Overloaded constructor, constructs a Header object from the given start index, end tag index and end index
         * @param startIndex Index of the first character in the headers tag
         * @param endTagIndex Index of the first character in the headers closing tag
         * @param endIndex Index of the first character of the next header of same type
         */
        public Header(Integer startIndex, Integer endTagIndex, Integer endIndex) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.endTagIndex = endTagIndex;
            this.h2List = new ArrayList<>();
        }

        // ----- Setters -----
        public void setHeaderText(String headerText) {
            this.treeItemText = headerText;
        }
        public void setType(int type) {
            this.type = type;
        }

        /**
         * Method that gets the actual text, representing the header
         * @return String with the header text
         */
        private String getHeaderText(){
            if(treeItemText == null){
                String htext = text.substring(startIndex, endTagIndex);

                String h = htext.replace("<h1>", "");
                h = h.replace("<h2>", "");

                return h;
            }
            else {
                return treeItemText;
            }
        }

        /**
         * Method that creates a new Tree item from the header
         * and stores a reference to it in the header
         */
        private void headerToTreeItem(){
            treeItem = new TreeItem<>(this);
        }
    }

    /**
     * Method that gets references to other controllers
     * to be able to pass data between them
     */
    public void setControllers(){
        this.contentsSubPageController = Main.getContentsSubPageController();
        this.overviewSubPageController = Main.getOverviewSubPageController();
        this.placeholdersSubPageController = Main.getPlaceholdersSubPageController();
        this.previewSubPageController = Main.getPreviewSubPageController();
        this.registerNewContentBlockController = Main.getRegisterNewContentBlockController();
        this.textEditorController = Main.getTextEditorController();
    }

}
