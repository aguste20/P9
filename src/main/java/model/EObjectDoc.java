package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * EObjectDoc implements the documentation that is related to a given EObject (engineering object).
 * An EObjectDoc can only be related to one EObject and represents the most resent documentation.
 *
 * An EObjectDoc holds meta information about the documentation
 * and a path to an .XML file in which the documentation is stored.
 *
 * The class is mapped with Hibernate JPA. See: https://www.baeldung.com/jpa-entities
 */

@Entity
@Table(name = "e_object_doc")
public class EObjectDoc {

    // ----- Properties -----

    @Id
    @Column(name = "e_object_doc_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Generate unique value for every identity
    private Integer eObjectDocId;

    private Boolean published;
    private String xmlPath;
    private Date lastEdit;

    @ManyToMany(cascade = { CascadeType.ALL})
    @JoinTable(
            name = "e_object_doc_has_content_block",
            joinColumns = { @JoinColumn(name = "e_object_doc_id")},
            inverseJoinColumns = { @JoinColumn(name = "content_block_id")}
    )
    private List<ContentBlock> contentBlockList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "e_object_id", referencedColumnName = "e_object_id")
    private EObject eObject;


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

    public List<ContentBlock> getContentBlockList() {
        return contentBlockList;
    }

    public void setContentBlockList(List<ContentBlock> contentBlockList) {
        this.contentBlockList = contentBlockList;
    }

    public EObject geteObject() {
        return eObject;
    }

    public void seteObject(EObject eObject) {
        this.eObject = eObject;
    }
}
