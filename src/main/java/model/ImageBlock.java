package model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TextBlock implements an image which can be reused across different EObjects (engineering objects).
 * ImageBlock is a concrete implementation of the superclass ContentBlock.
 */

//TODO: Annotate with Hibernate JPA

@Entity
@Table(name = "image")
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
