package persistence;

import model.Organisation;
import org.hibernate.Session;

import java.util.List;

public class OrganisationDao {

    public void addOrg(Organisation org){
        Session session = Setup.getSession();
        session.save(org);
        Setup.closeSession(session);
    }

    public void updateOrg(Organisation org){
        Session session = Setup.getSession();
        session.update(org);
        Setup.closeSession(session);
    }

    public void deleteOrg(Organisation org){
        Session session = Setup.getSession();
        session.delete(org);
        Setup.closeSession(session);

    }

    /**
     * Method for finding a Organisation by ID
     * @param id The ID of the Organisation that is being searched for
     * @return Returns the object that is found through the ID
     */
    public Organisation getById(Integer id){
        //Creating session
        Session session = Setup.getSession();
        //Searching the database for the object with the provided ID
        Organisation obj = session.find(Organisation.class, id);
        //Closing session
        Setup.closeSession(session);
        //Returning the found object
        return obj;
    }

    /**
     * Method to get all of the Organisation objects in the database
     * @return Returning a list of all the Organisation objects in the databse
     */
    public List<Organisation> listAll(){
        //Creating session
        Session session = Setup.getSession();
        //Querying database for all objects
        List<Organisation> objList = session.createQuery("SELECT a from Organisation a", Organisation.class).getResultList();
        //Closing session
        Setup.closeSession(session);
        //Returning list of objects retrieved from the database
        return objList;
    }
}
