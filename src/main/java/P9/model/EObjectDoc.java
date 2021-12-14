package P9.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * EObjectDoc implements the documentation that is related to a given EObject (engineering object).
 * An EObjectDoc can only be related to one EObject and represents the most resent documentation.
 *
 * An EObjectDoc holds meta information about the documentation
 * and a path to an .XML file in which the documentation is stored.
 *
 * The class is mapped with Hibernate JPA. See: https://www.baeldung.com/jpa-entities
 * JPA many to many mapping: https://www.baeldung.com/hibernate-many-to-many
 */

@Entity
@Table(name = "e_object_doc")
public class EObjectDoc {

    // ----- Properties -----
    @Id
    @Column(name = "e_object_doc_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generates an unique value for every identity
    private Integer eObjectDocId;

    private Boolean published;

    @Column(name = "text")
    private String text;

    @Column(name = "last_edit")
    private Date lastEdit;

    // Maps a many-to-many relation between eObject doc and content blocks, cascading all actions
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL})
    // The association uses the join/link table "e_object_doc_has_content_block"
    @JoinTable(name = "e_object_doc_has_content_block",
            // The two columns are foreign keys to id columns in the eObject doc table and the content block table
            // The eObject doc is the "owning" part of the association. The content block is the inverse part.
            joinColumns = { @JoinColumn(name = "e_object_doc_id")},
            inverseJoinColumns = { @JoinColumn(name = "content_block_id")}
    )
    private List<ContentBlock> contentBlockList = new ArrayList<>();

    // Maps a one-to-one relation between eObject doc and eObject
    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL})
    // The association uses the join column "e_object_id" in the e_object_doc table
    // which references the id column in the eObject table
    @JoinColumn(name = "e_object_id", referencedColumnName = "e_object_id")
    private EObject eObject;

    // ----- Constructor -----
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

    public String getText() {
        return text;
    }

    public void setText(String xmlPath) {
        this.text = xmlPath;
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
