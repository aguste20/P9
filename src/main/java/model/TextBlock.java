package model;

import javax.persistence.*;

/**
 * TextBlock implements a block of text which can be reused across different EObjects (engineering objects).
 * TextBlock is a concrete implementation of the superclass ContentBlock.
 */

//TODO: Annotate with Hibernate JPA

@Entity
@Table(name = "text")
@PrimaryKeyJoinColumn(name = "text_id")
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
