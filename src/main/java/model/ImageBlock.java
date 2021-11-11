package model;

import javax.persistence.*;

/**
 * TextBlock implements an image which can be reused across different EObjects (engineering objects).
 * ImageBlock is a concrete implementation of the superclass ContentBlock.
 *
 * The class is mapped with Hibernate JPA. See: https://www.baeldung.com/jpa-entities
 * JPA inheritance mapping: https://www.baeldung.com/hibernate-inheritance
 */

//TODO: Annotate with Hibernate JPA

@Entity
@Table(name = "image")
@PrimaryKeyJoinColumn(name = "image_id")
public class ImageBlock extends ContentBlock{

    // ----- Properties -----
    private String imagePath;


    // ----- Constructors -----

    /**
     * Empty constructor
     */
    public ImageBlock() {
    }


    // ----- Getters and setters -----

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
