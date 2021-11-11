package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * EObjectCategory implements categories that apply to the engineering objects in the organisation (eg. boat, car).
 *
 * The categories are used to differentiate engineering objects
 * and display relevant reusable content for these - in the form of ContentBlocks.
 *
 * The class is mapped with Hibernate JPA. See: https://www.baeldung.com/jpa-entities
 * JPA many to many mapping: https://www.baeldung.com/hibernate-many-to-many
 */

@Entity
@Table(name = "e_object_category")
public class EObjectCategory {

    // ----- Properties -----

    @Id
    @Column(name = "e_object_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Generate unique value for every identity
    private Integer eObjectCatId;

    private String name;

    // Maps a one to many relation between category and eObject
    // The association is mapped by the field "category" in EObject.java
    @OneToMany(mappedBy = "category")
    private List<EObject> eObjectList = new ArrayList<>();

    // Maps a many to many relation between category and content blocks, cascading all actions
    @ManyToMany(cascade = { CascadeType.ALL})
    // The association uses the join/link table "e_object_category_has_content_block"
    @JoinTable(name = "e_object_category_has_content_block",
            // The two columns are foreign keys to id columns in the category table and the content block table
            // The category is the "owning" part of the association. The content block is the inverse part.
            joinColumns = { @JoinColumn(name = "e_object_category_id")},
            inverseJoinColumns = { @JoinColumn(name = "content_block_id")}
    )
    private List<ContentBlock> contentBlockList = new ArrayList<>();


    // ----- Constructors -----

    /**
     * Empty constructor
     */
    public EObjectCategory() {
    }


    // ----- Getters and setters -----

    public Integer geteObjectCatId() {
        return eObjectCatId;
    }

    public void seteObjectCatId(Integer eObjectCatId) {
        this.eObjectCatId = eObjectCatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EObject> geteObjectList() {
        return eObjectList;
    }

    public void seteObjectList(List<EObject> eObjectList) {
        this.eObjectList = eObjectList;
    }

    public List<ContentBlock> getContentBlockList() {
        return contentBlockList;
    }

    public void setContentBlockList(List<ContentBlock> contentBlockList) {
        this.contentBlockList = contentBlockList;
    }
}
