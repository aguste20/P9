package model;

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
public class EObjectCategory {

    // ----- Properties -----
    private Integer eObjectCatId;
    private String name;
    private List<EObject> eObjectList = new ArrayList<>();
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
