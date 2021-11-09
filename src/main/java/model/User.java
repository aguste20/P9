package model;

import java.util.ArrayList;
import java.util.List;

/**
 * User implements a user in the system (eg. engineer, technical writer, sales person).
 * A user has access to a number of engineering objects.
 */

public class User {

    // ----- Properties -----
    private Integer userId;
    private String name;
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
}
