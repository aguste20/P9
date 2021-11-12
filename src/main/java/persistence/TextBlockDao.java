package persistence;

import model.TextBlock;
import org.hibernate.Session;

public class TextBlockDao {

    public void addTextBlock(TextBlock txt){
        Session session = Setup.getSession();
        session.save(txt);
        Setup.closeSession(session);
    }

    public void updateTextBlock(TextBlock txt){
        Session session = Setup.getSession();
        session.update(txt);
        Setup.closeSession(session);
    }


    public void deleteTextBlock(TextBlock txt){
        Session session = Setup.getSession();
        session.delete(txt);
        Setup.closeSession(session);
    }
}
