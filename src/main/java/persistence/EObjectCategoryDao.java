package persistence;

import model.EObjectCategory;
import org.hibernate.Session;

public class EObjectCategoryDao {

    public void addEobjectCat(EObjectCategory category){
        Session session = Setup.getSession();
        session.save(category);
        Setup.closeSession(session);
    }

    public void updateEObjectCat(EObjectCategory category){
        Session session = Setup.getSession();
        session.update(category);
        Setup.closeSession(session);
    }

    public void deleteEObjectCat(EObjectCategory category){
        Session session = Setup.getSession();
        session.delete(category);
        Setup.closeSession(session);
    }
}
