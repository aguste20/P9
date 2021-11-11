package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User implements a user in the system (eg. engineer, technical writer, sales person).
 * A user has access to a number of engineering objects.
 *
 * The class is mapped with Hibernate JPA. See: https://www.baeldung.com/jpa-entities
 */

@Entity
@Table(name = "user")
public class User {

    // ----- Properties -----

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Generate unique value for every identity
    private Integer userId;


    private String name;

    // Maps a many to many relationship between user and eObjects, cascading all actions
    @ManyToMany(cascade = { CascadeType.ALL })
    // The association uses the join/link table "user_has_object"
    @JoinTable(name = "user_has_e_object",
            // The two columns are foreign keys to id columns in the user table and the eObject table
            // User is the "owning" part of the association. eObject is the inverse part.
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns =  { @JoinColumn(name = "e_object_id") }
    )
    private List<EObject> eObjectList = new ArrayList<>();


    // ----- Constructors -----

    /**
     * Empty constructor
     */
    public User() {
    }


    // ----- Getters and setters -----

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
}
