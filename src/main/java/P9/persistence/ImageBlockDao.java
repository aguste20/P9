package P9.persistence;

import P9.model.ImageBlock;
import org.hibernate.Session;

import java.util.List;

/**
 * The ImageBlockDao class enables data exchange between the application and the SQL database.
 * It has 3 different Hibernate operations that allows for data transfer between SQL elements and Java objects.
 */

public class ImageBlockDao {

    /**
     * Adds a ImageBlock object to the database
     * @param img The ImageBlock object that is to be added
     */
    public void addImageBlock(ImageBlock img){
        Session session = Setup.getSession();
        session.save(img);
        Setup.closeSession(session);
    }

    /**
     * Updating a ImageBlock object in the database
     * @param img The ImageBlock object that is to be added
     */
    public void updateImageBlock(ImageBlock img){
        Session session = Setup.getSession();
        session.update(img);
        Setup.closeSession(session);
    }

    /**
     * Method for deleting a ImageBlock object by ID
     * @param img The ID of the ImageBlock object that is being searched for
     */
    public void deleteImageBlock(ImageBlock img){
        Session session = Setup.getSession();
        session.delete(img);
        Setup.closeSession(session);
    }

    /**
     * Method for finding a ImageBlock by ID
     * @param id The ID of the ImageBlock that is being searched for
     * @return Returns the object that is found through the ID
     */
    public ImageBlock getById(Integer id){
        //Creating session
        Session session = Setup.getSession();
        //Searching the database for the object with the provided ID
        ImageBlock obj = session.find(ImageBlock.class, id);
        //Closing session
        Setup.closeSession(session);
        //Returning the found object
        return obj;
    }

    /**
     * Method to get all of the ImageBlock objects in the database
     * @return Returning a list of all the ImageBlock objects in the databse
     */
    public List<ImageBlock> listAll(){
        //Creating session
        Session session = Setup.getSession();
        //Querying database for all objects
        List<ImageBlock> objList = session.createQuery("SELECT a from ImageBlock a", ImageBlock.class).getResultList();
        //Closing session
        Setup.closeSession(session);
        //Returning list of objects retrieved from the database
        return objList;
    }

    public void addOrUpdateImg(ImageBlock img) {
        Session session = Setup.getSession();
        session.saveOrUpdate(img);
        Setup.closeSession(session);
    }
}
