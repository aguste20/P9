package P9;

import P9.model.EObject;
import P9.model.EObjectDoc;
import P9.model.Organisation;
import P9.persistence.EObjectDao;
import P9.persistence.EObjectDocDao;
import P9.persistence.OrganisationDao;

import java.sql.Date;
import java.time.LocalDate;

public class TestAnne {

    OrganisationDao orgDaO = new OrganisationDao();

    public void metode(){
        readCategoryFromEobject();
        readComponentsFromEobject();
        dateTest();
    }

    public void createOrg(){
        Organisation org = new Organisation("Lmao");
        org.setOrganisationId(4);
        orgDaO.updateOrg(org);
    }

    public void readComponentsFromEobject(){
        EObject motor = new EObjectDao().getById(1);

        if (!motor.getComponentList().isEmpty()) {
            for (EObject c : motor.getComponentList()) {
                System.out.println("Component: " + c.getName());
            }
        } else {
            System.out.println("No components dude");
        }
    }

    public void readCategoryFromEobject(){
        EObject motor = new EObjectDao().getById(1);

        System.out.println("Cateogory: " + motor.getCategory().getName());

    }

    public void dateTest(){

        EObject motor = new EObjectDao().getById(1);

        EObjectDoc doc = new EObjectDoc();

        Date date = Date.valueOf(LocalDate.now());

        doc.setLastEdit(date);

        doc.seteObject(motor);

        EObjectDocDao dao = new EObjectDocDao();
        dao.addEObjectDoc(doc);
    }

}
