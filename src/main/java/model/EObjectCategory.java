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
 */

//TODO: Annotate with Hibernate JPA

@Entity
@Table(name = "e_object_category")
public class EObjectCategory {

    // ----- Properties -----

    @Id
    @Column(name = "e_object_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Generate unique value for every identity
    private Integer eObjectCatId;
    private String name;
    private List<EObject> eObjectList = new ArrayList<>();

    @ManyToMany(cascade = { CascadeType.ALL})
    @JoinTable(
            name = "e_object_category_has_content_block",
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
