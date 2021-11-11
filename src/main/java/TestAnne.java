import model.Organisation;
import persistence.OrganisationDao;

public class TestAnne {

    OrganisationDao orgDaO = new OrganisationDao();

    public void metode(){
        System.out.println("hej");
    }

    public void createOrg(){
        Organisation org = new Organisation("XD");
        org.setOrganisationId(2);
        orgDaO.deleteOrg(org);
    }

}
