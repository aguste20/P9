package persistence;

import org.hibernate.Session;

public class ContentBlockDao {

    public void addBlock(ContentBlockDao block){
        Session session = Setup.getSession();
        session.save(block);
        Setup.closeSession(session);
    }

    public void updateBlock(ContentBlockDao block){
        Session session = Setup.getSession();
        session.update(block);
        Setup.closeSession(session);
    }

    public void deleteBlock(ContentBlockDao block){
        Session session = Setup.getSession();
        session.delete(block);
        Setup.closeSession(session);
    }
}
