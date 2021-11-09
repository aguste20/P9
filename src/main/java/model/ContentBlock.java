package model;

/**
 * ContentBlock is an abstract class that represents a block of content that is reusable across different
 * EObjects (engineering objects).
 *
 * The content be an image or text (from a single word to several paragraphs or pages)
 *
 * The classes TextBlock and ImageBlock implement ContentBlocks.
 */

public abstract class ContentBlock {

    // ----- Properties -----
    private Integer contentBlockID;
    private String name;

    // ----- Constructors -----

    /**
     * Empty constructor
     */
    public ContentBlock() {
    }


    // ----- Getters and setters -----

    public Integer getContentBlockID() {
        return contentBlockID;
    }

    public void setContentBlockID(Integer contentBlockID) {
        this.contentBlockID = contentBlockID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
