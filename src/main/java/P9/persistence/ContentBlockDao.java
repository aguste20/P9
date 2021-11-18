package P9.persistence;

import P9.model.ContentBlock;
import org.hibernate.Session;

import java.util.List;

/**
 * The ContentBlockDao class enables data exchange between the application and the SQL database.
 * It has 3 different Hibernate operations that allows for data transfer between SQL elements and Java objects.
 */

public class ContentBlockDao {

    /**
     * Adds a ContentBlock object to the database
     * @param block The ContentBlock object that is to be added
     */
    public void addBlock(ContentBlock block){
        Session session = Setup.getSession();
        session.save(block);
        Setup.closeSession(session);
    }

    /**
     * Updating a ContentBlock object in the database
     * @param block The ContentBlock object that is to be added
     */
    public void updateBlock(ContentBlock block){
        Session session = Setup.getSession();
        session.update(block);
        Setup.closeSession(session);
    }

    /**
     * Method for deleting a ContentBlock object by ID
     * @param block The ID of the ContentBlock object that is being searched for
     */
    public void deleteBlock(ContentBlock block){
        Session session = Setup.getSession();
        session.delete(block);
        Setup.closeSession(session);
    }

    /**
     * Method for finding a ContentBlock by ID
     * @param id The ID of the ContentBlock that is being searched for
     * @return Returns the object that is found through the ID
     */
    public ContentBlock getById(Integer id){
        //Creating session
        Session session = Setup.getSession();
        //Searching the database for the object with the provided ID
        ContentBlock block = session.find(ContentBlock.class, id);
        //Closing session
        Setup.closeSession(session);
        //Returning the found object
        return block;
    }

    /**
     * Method to get all of the ContentBlock objects in the database
     * @return Returning a list of all the ContentBlock objects in the databse
     */
    public List<ContentBlock> listAll(){
        //Creating session
        Session session = Setup.getSession();
        //Querying database for all objects
        List<ContentBlock> list = session.createQuery("SELECT a from ContentBlock a", ContentBlock.class).getResultList();
        //Closing session
        Setup.closeSession(session);
        //Returning list of objects retrieved from the database
        return list;
    }

}
