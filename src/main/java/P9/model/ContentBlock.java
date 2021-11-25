package P9.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ContentBlock is an abstract class that represents a block of content that is reusable across different
 * EObjects (engineering objects).
 *
 * The content be an image or text (from a single word to several paragraphs or pages)
 *
 * The classes TextBlock and ImageBlock are concrete subclasses of ContentBlocks.
 *
 * The class is mapped with Hibernate JPA. See: https://www.baeldung.com/jpa-entities
 * JPA many to many mapping: https://www.baeldung.com/hibernate-many-to-many
 */

@Entity
@Table(name = "content_block")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ContentBlock {

    // ----- Properties -----

    @Id
    @Column(name = "content_block_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generates an unique value for every identity
    private Integer contentBlockID;

    private String name;

    // Maps a many-to-many relation between contentBlock and category
    // The association is mapped by the field "contentBlockList" in EObjectCategory.java
    @ManyToMany(mappedBy = "contentBlockList")
    private List<EObjectCategory> categoryList = new ArrayList<>();

    // Maps a many-to-many relation between contentBlock and eObject doc
    // The association is mapped by the field "contentBlockList" in EObjectDoc.java
    @ManyToMany(mappedBy = "contentBlockList")
    private List<EObjectDoc> eObjectDocList = new ArrayList<>();

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

    public List<EObjectDoc> geteObjectDocList() {
        return eObjectDocList;
    }

    public void seteObjectDocList(List<EObjectDoc> eObjectDocList) {
        this.eObjectDocList = eObjectDocList;
    }

    public List<EObjectCategory> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<EObjectCategory> categoryList) {
        this.categoryList = categoryList;
    }

    public StringProperty nameProperty(){
        return new SimpleStringProperty(name);
    }

}
