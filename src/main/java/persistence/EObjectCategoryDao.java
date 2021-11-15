package persistence;

import model.EObjectCategory;
import org.hibernate.Session;

import java.util.List;

/**
 * The EObjectCategoryDao class enables data exchange between the application and the SQL database.
 * It has 3 different Hibernate operations that allows for data transfer between SQL elements and Java objects.
 */

public class EObjectCategoryDao {

    /**
     * Adds a EobjectCat object to the database
     * @param category The EobjectCat object that is to be added
     */
    public void addEobjectCat(EObjectCategory category){
        Session session = Setup.getSession();
        session.save(category);
        Setup.closeSession(session);
    }

    /**
     * Updating a ContentBlock object in the database
     * @param category The ContentBlock object that is to be added
     */
    public void updateEObjectCat(EObjectCategory category){
        Session session = Setup.getSession();
        session.update(category);
        Setup.closeSession(session);
    }

    /**
     * Method for deleting a ContentBlock object by ID
     * @param category The ID of the ContentBlock object that is being searched for
     */
    public void deleteEObjectCat(EObjectCategory category){
        Session session = Setup.getSession();
        session.delete(category);
        Setup.closeSession(session);
    }

    /**
     * Method for finding a EObjectCategory by ID
     * @param id The ID of the EObjectCategory that is being searched for
     * @return Returns the object that is found through the ID
     */
    public EObjectCategory getById(Integer id){
        //Creating session
        Session session = Setup.getSession();
        //Searching the database for the object with the provided ID
        EObjectCategory obj = session.find(EObjectCategory.class, id);
        //Closing session
        Setup.closeSession(session);
        //Returning the found object
        return obj;
    }

    /**
     * Method to get all of the EObjectCategory objects in the database
     * @return Returning a list of all the EOjbectCategory objects in the databse
     */
    public List<EObjectCategory> listAll(){
        //Creating session
        Session session = Setup.getSession();
        //Querying database for all objects
        List<EObjectCategory> objList = session.createQuery("SELECT a from EObjectCategory a", EObjectCategory.class).getResultList();
        //Closing session
        Setup.closeSession(session);
        //Returning list of objects retrieved from the database
        return objList;
    }

}
