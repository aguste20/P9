package P9.model;

import javax.persistence.*;

/**
 * TextBlock implements a block of text which can be reused across different EObjects (engineering objects).
 * TextBlock is a concrete implementation of the superclass ContentBlock.
 *
 * The class is mapped with Hibernate JPA. See: https://www.baeldung.com/jpa-entities
 * JPA inheritance mapping: https://www.baeldung.com/hibernate-inheritance
 */

@Entity
@Table(name = "text")
// Maps inheritance between content block and text block,
// using the join column "text" in the content block table
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
