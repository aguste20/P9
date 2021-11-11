package model;

import org.hibernate.Session;
import persistence.Setup;

public abstract class DaoModel {

    public void addModel (DaoModel model){
        Session session = Setup.getSession();
        session.save(model);
        Setup.closeSession(session);
    }

}
