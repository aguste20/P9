package persistence;

import model.EObjectDoc;
import org.hibernate.Session;

import java.util.List;

public class EObjectDocDao {

    public void addEObjectDoc(EObjectDoc doc){
        Session session = Setup.getSession();
        session.save(doc);
        Setup.closeSession(session);
    }

    public void updateEObjectDoc(EObjectDoc doc){
        Session session = Setup.getSession();
        session.update(doc);
        Setup.closeSession(session);
    }

    public void deleteEObjectDoc(EObjectDoc doc){
        Session session = Setup.getSession();
        session.delete(doc);
        Setup.closeSession(session);
    }

    /**
     * Method for finding a EObjectDoc by ID
     * @param id The ID of the EObjectDoc that is being searched for
     * @return Returns the object that is found through the ID
     */
    public EObjectDoc getById(Integer id){
        //Creating session
        Session session = Setup.getSession();
        //Searching the database for the object with the provided ID
        EObjectDoc obj = session.find(EObjectDoc.class, id);
        //Closing session
        Setup.closeSession(session);
        //Returning the found object
        return obj;
    }

    /**
     * Method to get all of the EObjectDoc objects in the database
     * @return Returning a list of all the EObjectDoc objects in the databse
     */
    public List<EObjectDoc> listAll(){
        //Creating session
        Session session = Setup.getSession();
        //Querying database for all objects
        List<EObjectDoc> objList = session.createQuery("SELECT a from EObjectDoc a", EObjectDoc.class).getResultList();
        //Closing session
        Setup.closeSession(session);
        //Returning list of objects retrieved from the database
        return objList;
    }
}
