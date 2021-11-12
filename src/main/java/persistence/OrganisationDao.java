package persistence;

import model.Organisation;
import org.hibernate.Session;

import java.util.Set;

public class OrganisationDao {

    public void addOrg(Organisation org){
        Session session = Setup.getSession();
        session.save(org);
        Setup.closeSession(session);
    }

    public void updateOrg(Organisation org){
        Session session = Setup.getSession();
        session.update(org);
        Setup.closeSession(session);
    }

    public void deleteOrg(Organisation org){
        Session session = Setup.getSession();
        session.delete(org);
        Setup.closeSession(session);

    }

}
