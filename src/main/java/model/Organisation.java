package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Organisation implements an organisation/company that uses Bluestar PLM for their engineering processes.
 * Its properties hold basic information about the organisation.
 */

//TODO: Annotate with Hibernate JPA
@Entity
@Table(name = "organisation")
public class Organisation {

    // ----- Properties -----

    @Id
    @Column(name = "organisation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer organisationId;
    private String name;
    private String address;
    private String phone;
    private String email;
    //private List<User> userList = new ArrayList<>();



    // ----- Constructors -----

    /**
     * Empty constructor
     */
    public Organisation() {
    }

    public Organisation(String name){
        this.name = name;
    }


    // ----- Getters and setters -----

    public Integer getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Integer organisationId) {
        this.organisationId = organisationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

     */
}
