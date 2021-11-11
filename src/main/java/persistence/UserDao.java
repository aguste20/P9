package persistence;

import model.User;
import org.hibernate.Session;

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
}
