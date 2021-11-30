package P9.controller;

import P9.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.web.WebView;
import javafx.util.StringConverter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.net.URL;
import java.util.*;

public class OverviewSubPageController implements Initializable {

    private List<Header> headerList = new ArrayList<>();
    private List<Header> allh2s = new ArrayList<>();
    private String text; // Text from the textarea

    @FXML
    private Button refreshTocButton;

    @FXML
    private TreeView<Header> tocView; // The tree view that holds all header elements

    /**
     * Method that initializes a contoller object after its root element has been loaded.
     * Inherited from Initializable.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Prepare table of contents tree view
        prepareToc();

        // Update table of contents
        updateToc();
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
        headerList.clear();
        allh2s.clear();

        // Clear previous table of content
        tocView.getRoot().getChildren().clear();

        // Get text from text area
        text = Main.getTextEditorController().getTextArea().getText();

        //Find all h1 - add to list
        headerList = findAllH(1);

        //For every h1 in list
        for (Header h : headerList){
            //Find all h2 for the parent header - add to list for the current h
            h.h2List.addAll(findAllH(2, h));

            // Add header's local h2 list to list with all h2's
            allh2s.addAll(h.h2List);

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

        System.out.println("All h2's:");
        for (Header h : allh2s){
            System.out.println(allh2s.indexOf(h));
            System.out.println(h.getHeaderText());

        }

        System.out.println("All h1's:");
        for (Header h : headerList){
            System.out.println(headerList.indexOf(h));
            System.out.println(h.getHeaderText());

        }
    }


    /**
     * Overloaded method that returns a list of headers for the type
     * Should only be used for H1
     * @param type Type of header to be found (1 or 2)
     * @return list of header objects
     */
    public List<Header> findAllH(Integer type){
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
    public List<Header> findAllH(Integer type, Header parentH){
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
    public void createTocTreeViewRoot(){
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
    public void setCellFactoryOnTocView(){
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
    public void setCursorToSelectedHeader(){
        //Get selected header
        Header h = tocView.getSelectionModel().getSelectedItem().getValue();

        System.out.println(h.getHeaderText());

        if(Main.getTextEditorController().isTextEditorActive()){ // If text editor window active
            //Move cursor to selected header in text area
            setCursorInTextAreaAtIndex(h.startIndex);
        }
        else{ // If preview window active
            // Move cursor to selected header in preview
            //TODO anne: bohemian er også empty !! skal være et andet kriterie, fx type
            if(h.type == 1){
                setCursorInPreviewAtIndex(1, headerList.indexOf(h));
            }else if (h.type == 2) {
                setCursorInPreviewAtIndex(2, allh2s.indexOf(h));
            }

        }
    }

    // TODO Anne: kommentarer
    private void setCursorInTextAreaAtIndex(int index){

        System.out.println(index);

        // Get textarea from text editor
        TextArea textArea = Main.getTextEditorController().getTextArea();

        // Request window focus
        textArea.requestFocus();

        // Move cursor position to start index for selected header
        textArea.positionCaret(index);
    }

    // TODO Anne: Kommentarer
    public void setCursorInPreviewAtIndex(int type, int index){

        // Get webview/webengine
        WebView webview = Main.getWebview();

        //Request window focus
        String focusScript = "document.getElementById(\"mySpan\").focus()";
        webview.getEngine().executeScript(focusScript);
        webview.requestFocus();

        //Move cursor to index in html

        /*
               webview.getEngine().executeScript(setCaretScript);

         */

        System.out.println("Index of header is: " + index);



        webview.getEngine().executeScript("function placeCaretAtEnd(el) {\n" +
                "    el.focus();\n" +
                "    if (typeof window.getSelection != \"undefined\"\n" +
                "            && typeof document.createRange != \"undefined\") {\n" +
                "        var range = document.createRange();\n" +
                "        range.selectNodeContents(el);\n" +
                //"        range.setStart(el.childNodes[4], 10)\n" +
                "        range.collapse(false);\n" +
                "        var sel = window.getSelection();\n" +
                "        sel.removeAllRanges();\n" +
                "        sel.addRange(range);\n" +
                "    } else if (typeof document.body.createTextRange != \"undefined\") {\n" +
                "        var textRange = document.body.createTextRange();\n" +
                "        textRange.moveToElementText(el);\n" +
                "        textRange.collapse(false);\n" +
                "        textRange.select();\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "placeCaretAtEnd( document.querySelectorAll('h"+ type +"').item("+ index +") );");

        /*
        webview.getEngine().executeScript("function placeCaretAtEnd(el) {\n" +
                "    el.focus();\n" +
                "    if (typeof window.getSelection != \"undefined\"\n" +
                "            && typeof document.createRange != \"undefined\") {\n" +
                "        var range = document.createRange();\n" +
               // "        range.selectNodeContents(el);\n" +
                "        range.setStart(el.childNodes[4], 10)\n" +
                "        range.collapse(false);\n" +
                "        var sel = window.getSelection();\n" +
                "        sel.removeAllRanges();\n" +
                "        sel.addRange(range);\n" +
                "    } else if (typeof document.body.createTextRange != \"undefined\") {\n" +
                "        var textRange = document.body.createTextRange();\n" +
                "        textRange.moveToElementText(el);\n" +
                "        textRange.collapse(false);\n" +
                "        textRange.select();\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "placeCaretAtEnd( document.getElementById(\"mySpan\") );");

         */



        /*
        webview.getEngine().executeScript(
                "var el = document.body;\n" +
                        "if (typeof window.getSelection != \"undefined\"\n" +
                        "            && typeof document.createRange != \"undefined\") {\n" +
                        "        var range = document.createRange();\n" +
                        "        range.selectNodeContents(el);\n" +
                        "        range.collapse(false);\n" +
                        "        var sel = window.getSelection();\n" +
                        "        sel.removeAllRanges();\n" +
                        "        sel.addRange(range);\n" +
                        "    } else if (typeof document.body.createTextRange != \"undefined\") {\n" +
                        "        var textRange = document.body.createTextRange();\n" +
                        "        textRange.moveToElementText(el);\n" +
                        "        textRange.collapse(false);\n" +
                        "        textRange.select();\n" +
                        "    }");

         */




        //TODO Anne: ikke her
        // -- refresh TOC when switch to preview sub page

            /*
            Main.getEngine().executeScript("function setCaret() {\n" +
                "    var el = document.getElementById(\"mySpan\")\n" +
                "    var range = document.createRange()\n" +
                "    var sel = window.getSelection()\n" +
                "    \n" +
                "    range.setStart(el.childNodes[2], 5)\n" +
                "    range.collapse(true)\n" +
                "    \n" +
                "    sel.removeAllRanges()\n" +
                "    sel.addRange(range)\n" +
                "}");


             */


    }



    /**
     * Inner class that represents a header
     * Used to store header index position and treeItem in treeView
     */
    private class Header {
        private Integer startIndex; //Index of the first character in "<h1>"
        private Integer endTagIndex; //Index of the first character in "</h1>"
        private Integer endIndex; // Index of the next occurrence of "<h1>". Is set to final index position of text, if no more h1's
        private String headerText;
        private int type;
        private List<Header> h2List; // List of the headers subheadings, if any
        private TreeItem<Header> treeItem; // The headers treeitem in the tree view

        /**
         * Constructor
         * @param startIndex
         * @param endTagIndex
         */
        public Header(Integer startIndex, Integer endTagIndex) {
            this(startIndex, endTagIndex, 0);
        }

        public Header(Integer startIndex, Integer endTagIndex, Integer endIndex) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.endTagIndex = endTagIndex;
            this.h2List = new ArrayList<>();
        }

        public void setHeaderText(String headerText) {
            this.headerText = headerText;
        }

        public void setType(int type) {
            this.type = type;
        }

        /**
         * Method that gets the actual text, representing the header
         * @return String with the header text
         */
        public String getHeaderText(){
            if(headerText == null){
                String htext = text.substring(startIndex, endTagIndex);

                String h = htext.replace("<h1>", "");
                h = h.replace("<h2>", "");

                return h;
            }
            else {
                return headerText;
            }
        }

        /**
         * Method that creates a new Tree item from the header
         * and stores a reference to it in the header
         */
        public void headerToTreeItem(){
            treeItem = new TreeItem<>(this);
        }
    }

}
