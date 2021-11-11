package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Organisation implements an organisation/company that uses Bluestar PLM for their engineering processes.
 * Its properties hold basic information about the organisation.
 */

@Entity
@Table(name = "organisation")
public class Organisation {

    // ----- Properties -----

    @Id
    @Column(name = "organisation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Generate unique value for every identity
    private Integer organisationId;

    private String name;
    private String address;
    private String phone;
    private String email;

    @OneToMany(cascade = { CascadeType.ALL })
    @JoinColumn(name = "organisation_id", referencedColumnName = "organisation_id")
    private List<User> userList = new ArrayList<>();


    // ----- Constructors -----

    /**
     * Empty constructor
     */
    public Organisation() {
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

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
