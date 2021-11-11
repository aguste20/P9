import model.DaoModel;
import model.Organisation;

public class TestAnne {

    public void metode(){
        System.out.println("hej");
    }

    public void addOrg(){
        Organisation org = new Organisation("test");
        org.addModel(org);
    }

}
