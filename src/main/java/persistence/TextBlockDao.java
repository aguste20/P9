package persistence;

import model.TextBlock;
import org.hibernate.Session;

import java.util.List;

public class TextBlockDao {

    public void addTextBlock(TextBlock txt){
        Session session = Setup.getSession();
        session.save(txt);
        Setup.closeSession(session);
    }

    public void updateTextBlock(TextBlock txt){
        Session session = Setup.getSession();
        session.update(txt);
        Setup.closeSession(session);
    }


    public void deleteTextBlock(TextBlock txt){
        Session session = Setup.getSession();
        session.delete(txt);
        Setup.closeSession(session);
    }

    /**
     * Method for finding a TextBlock by ID
     * @param id The ID of the TextBlock that is being searched for
     * @return Returns the object that is found through the ID
     */
    public TextBlock getById(Integer id){
        //Creating session
        Session session = Setup.getSession();
        //Searching the database for the object with the provided ID
        TextBlock obj = session.find(TextBlock.class, id);
        //Closing session
        Setup.closeSession(session);
        //Returning the found object
        return obj;
    }

    /**
     * Method to get all of the TextBlock objects in the database
     * @return Returning a list of all the TextBlock objects in the databse
     */
    public List<TextBlock> listAll(){
        //Creating session
        Session session = Setup.getSession();
        //Querying database for all objects
        List<TextBlock> objList = session.createQuery("SELECT a from TextBlock a", TextBlock.class).getResultList();
        //Closing session
        Setup.closeSession(session);
        //Returning list of objects retrieved from the database
        return objList;
    }
}
