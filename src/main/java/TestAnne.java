import model.EObject;
import model.Organisation;
import persistence.EObjectDao;
import persistence.OrganisationDao;

public class TestAnne {

    OrganisationDao orgDaO = new OrganisationDao();

    public void metode(){
        readCategoryFromEobject();
        readComponentsFromEobject();
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

}
