package model;

/**
 * TextBlock implements an image which can be reused across different EObjects (engineering objects).
 * ImageBlock is a concrete implementation of ContentBlock.
 */

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
