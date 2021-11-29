package P9.controller;

import P9.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.*;

public class OverviewSubPageController implements Initializable {

    private List<Header> headerList = new ArrayList<>();
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
        // Get reference to displayed doc
        createTocTreeViewRoot();

        // Create table of contents
        updateToc();

        setCellFactoryOnTocView();
    }


    /**
     * Method that updates the table of contents
     */
    public void updateToc() {
        // Clear previous table of content
        tocView.getRoot().getChildren().clear();

        // Get text from text area
        text = Main.getTextEditorController().getTextArea().getText();

        //Find all h1 - add to list
        headerList = findAllH(1);

        //For every h1 in list
        for (Header h : headerList){
            //Find all h2 for the parent header - add to list
            h.h2List.addAll(findAllH(2, h));

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

    // TODO Anne - gør sådan at den finder header position i preview
    /**
     * Event handler for toc tree view.
     * Is called when user clicks item in tree view.
     * Sets text editor scene in focus and moves cursor to selected header
     */
    @FXML
    public void moveToSelectedHeaderInTextArea(){
        // Get textarea from text editor
        TextArea textArea = Main.getTextEditorController().getTextArea();

        // Request window focus
        textArea.requestFocus();

        // Get selected header in toc
        Header h = tocView.getSelectionModel().getSelectedItem().getValue();

        // Move cursor position to start index for selected heade
        textArea.positionCaret(h.startIndex);
    }


    /**
     * Inner class that represents a header
     * Used to store header index position and treeItem in treeView
     */
    private class Header {
        Integer startIndex; //Index of the first character in "<h1>"
        Integer endTagIndex; //Index of the first character in "</h1>"
        Integer endIndex; // Index of the next occurrence of "<h1>". Is set to final index position of text, if no more h1's
        String headerText;
        List<Header> h2List; // List of the headers subheadings, if any
        TreeItem<Header> treeItem; // The headers treeitem in the tree view

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
