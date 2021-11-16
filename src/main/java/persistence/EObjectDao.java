package persistence;

import model.EObject;
import org.hibernate.Session;

import java.util.List;

/**
 * The EObjectDao class enables data exchange between the application and the SQL database.
 * It has 3 different Hibernate operations that allows for data transfer between SQL elements and Java objects.
 */

public class EObjectDao {

    /**
     * Adds a EObject object to the database
     * @param eObject The EObject object that is to be added
     */
    public void addEObject(EObject eObject){
        Session session = Setup.getSession();
        session.save(eObject);
        Setup.closeSession(session);
    }

    /**
     * Updating a EObject object in the database
     * @param eObject The EObject object that is to be added
     */
    public void updateEobject(EObject eObject){
        Session session = Setup.getSession();
        session.update(eObject);
        Setup.closeSession(session);
    }

    /**
     * Method for deleting a EObject object by ID
     * @param eObject The ID of the EObject object that is being searched for
     */
    public void deleteEobject(EObject eObject){
        Session session = Setup.getSession();
        session.delete(eObject);
        Setup.closeSession(session);
    }

    /**
     * Method for finding a EObject by ID
     * @param id The ID of the EObject that is being searched for
     * @return Returns the object that is found through the ID
     */
    public EObject getById(Integer id){
        //Creating session
        Session session = Setup.getSession();
        //Searching the database for the object with the provided ID
        EObject obj = session.find(EObject.class, id);
        //Closing session
        Setup.closeSession(session);
        //Returning the found object
        return obj;
    }

    /**
     * Method to get all of the EObject objects in the database
     * @return Returning a list of all the EObject objects in the databse
     */
    public List<EObject> listAll(){
        //Creating session
        Session session = Setup.getSession();
        //Querying database for all objects
        List<EObject> objList = session.createQuery("SELECT a from EObject a", EObject.class).getResultList();
        //Closing session
        Setup.closeSession(session);
        //Returning list of objects retrieved from the database
        return objList;
    }
}
