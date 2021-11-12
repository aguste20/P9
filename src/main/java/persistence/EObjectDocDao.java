package persistence;

import model.EObjectDoc;
import org.hibernate.Session;

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

}
