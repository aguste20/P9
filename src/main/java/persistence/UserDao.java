package persistence;

import model.User;
import org.hibernate.Session;

import java.util.List;

public class UserDao {

    public void addUser(User user){
        Session session = Setup.getSession();
        session.save(user);
        Setup.closeSession(session);
    }

    public void updateUser(User user){
        Session session = Setup.getSession();
        session.update(user);
        Setup.closeSession(session);
    }


    public void deleteUser(User user){
        Session session = Setup.getSession();
        session.delete(user);
        Setup.closeSession(session);
    }

    /**
     * Method for finding a User by ID
     * @param id The ID of the User that is being searched for
     * @return Returns the object that is found through the ID
     */
    public User getById(Integer id){
        //Creating session
        Session session = Setup.getSession();
        //Searching the database for the object with the provided ID
        User obj = session.find(User.class, id);
        //Closing session
        Setup.closeSession(session);
        //Returning the found object
        return obj;
    }

    /**
     * Method to get all of the User objects in the database
     * @return Returning a list of all the User objects in the databse
     */
    public List<User> listAll(){
        //Creating session
        Session session = Setup.getSession();
        //Querying database for all objects
        List<User> objList = session.createQuery("SELECT a from User a", User.class).getResultList();
        //Closing session
        Setup.closeSession(session);
        //Returning list of objects retrieved from the database
        return objList;
    }
}
