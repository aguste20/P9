package model;

/**
 * TextBlock implements a block of text which can be reused across different EObjects (engineering objects).
 * TextBlock is a concrete implementation of the superclass ContentBlock.
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
