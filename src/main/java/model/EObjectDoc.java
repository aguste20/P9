package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * EObjectDoc implements the documentation that is related to a given EObject (engineering object).
 * An EObjectDoc can only be related to one EObject and represents the most resent documentation.
 *
 * An EObjectDoc holds meta information about the documentation
 * and a path to an .XML file in which the documentation is stored.
 */

public class EObjectDoc {

    // ----- Properties -----
    private Integer eObjectDocId;
    private Boolean published;
    private String xmlPath;
    private Date lastEdit;
    private List<ContentBlock> contentBlockList = new ArrayList<>();

    // ----- Constructors -----

    /**
     * Empty constructor
     */
    public EObjectDoc() {
    }


    // ----- Getters and setters -----

    public Integer geteObjectDocId() {
        return eObjectDocId;
    }

    public void seteObjectDocId(Integer eObjectDocId) {
        this.eObjectDocId = eObjectDocId;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public String getXmlPath() {
        return xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public Date getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(Date lastEdit) {
        this.lastEdit = lastEdit;
    }
}
