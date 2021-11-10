package model;

import java.util.ArrayList;
import java.util.List;

/**
 * EObject implements an 'engineering object' - the name is derived from Bluestar PLM.
 * Engineering objects are the objects that engineers work with in the Bluestar PLM solution.
 *
 * A single EObject can either represent a product that the organisation sells
 * or a component that is part of another engineering object.
 *
 * An EObject holds information we assume is available in the Bluestar PLM solution.
 */

//TODO: Annotate with Hibernate JPA
public class EObject {

    // ----- Properties -----
    private Integer eObjectId;
    private String name;
    private Double version;
    private Double length;
    private Double height;
    private Double width;
    private Double weight;
    private String imagePath;
    private List<EObject> componentList = new ArrayList<>();
    private EObjectCategory category;
    private EObjectDoc doc;

    // ----- Constructors -----

    /**
     * Empty constructor
     */
    public EObject() {
    }


    // ----- Getters and setters -----

    public Integer geteObjectId() {
        return eObjectId;
    }

    public void seteObjectId(Integer eObjectId) {
        this.eObjectId = eObjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<EObject> getComponentList() {
        return componentList;
    }

    public void setComponentList(List<EObject> componentList) {
        this.componentList = componentList;
    }

    public EObjectCategory getCategory() {
        return category;
    }

    public void setCategory(EObjectCategory category) {
        this.category = category;
    }

    public EObjectDoc getDoc() {
        return doc;
    }

    public void setDoc(EObjectDoc doc) {
        this.doc = doc;
    }
}
