package persistence;

import model.EObject;
import org.hibernate.Session;

public class EObjectDao {

    public void addEObject(EObject eObject){
        Session session = Setup.getSession();
        session.save(eObject);
        Setup.closeSession(session);
    }

    public void updateEobject(EObject eObject){
        Session session = Setup.getSession();
        session.update(eObject);
        Setup.closeSession(session);
    }

    public void deleteEobject(EObject eObject){
        Session session = Setup.getSession();
        session.delete(eObject);
        Setup.closeSession(session);
    }
}
