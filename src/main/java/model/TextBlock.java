package model;

/**
 * TextBlock implements a block of text which can be reused across different EObjects (engineering objects).
 * It is a concrete implementation of ContentBlock.
 */

public class TextBlock extends ContentBlock{

    // ----- Properties -----
    private String txt;


    // ----- Constructors -----

    /**
     * Empty constructor
     */
    public TextBlock() {
    }


    // ----- Getters and setters -----

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
