package persistence;

import model.ImageBlock;
import org.hibernate.Session;

public class ImageBlockDao {

    public void addImageBlock(ImageBlock img){
        Session session = Setup.getSession();
        session.save(img);
        Setup.closeSession(session);
    }

    public void updateImageBlock(ImageBlock img){
        Session session = Setup.getSession();
        session.update(img);
        Setup.closeSession(session);
    }

    public void deleteImageBlock(ImageBlock img){
        Session session = Setup.getSession();
        session.delete(img);
        Setup.closeSession(session);
    }
}
