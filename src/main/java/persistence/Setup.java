package persistence;

import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * This class is responsible for creating the connection to the database by creating a sessionfactory
 * and allowing the creation of sessions.
 */
public class Setup {

    //Creating a Sessionfactory object, which is defined in the setSessionFactory method
    private static final SessionFactory sf = setSessionFactory();

    /**
     * Creates a session factory
     * @return Returns a sessionfactory that has been configured using the cfg file in resources
     */
    private static SessionFactory setSessionFactory() {
        //Using try because it can throw errors
        try {
            //Creating a config for the sessionfactory
            Configuration configuration = new Configuration();
            //Specifying what the config file is called
            configuration.configure("hibernate.cfg.xml");
            //Specifying which classes should be used in the sessionfactory
            configuration.addAnnotatedClass(ContentBlock.class)
                    .addAnnotatedClass(EObject.class)
                    .addAnnotatedClass(EObjectCategory.class)
                    .addAnnotatedClass(EObjectDoc.class)
                    .addAnnotatedClass(ImageBlock.class)
                    .addAnnotatedClass(Organisation.class)
                    .addAnnotatedClass(TextBlock.class)
                    .addAnnotatedClass(User.class);

            //Creating a Sessionfactory through the StandardRegistryBuilder with the config we created above
            StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            //Returning a sessionfactory that is used on the object defined in the beginning of the class
            return configuration.buildSessionFactory(ssrb.build());
            //Error handling
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Method to get the sessionfactory
     * @return Returns the sessionfactory that is built in the class.
     */
    private static SessionFactory getSf() {
        return sf;
    }

    /**
     * Method to get a session from the sessionfactory
     * @return Returns a Session where a transaction has already begun.
     */
    public static Session getSession(){
        Session session = getSf().openSession();
        session.beginTransaction();
        return session;
    }

    /**
     * Method to close a session
     * @param session Takes the session that is being worked on and commits the hibernate query's and closses the session.
     */
    public static void closeSession(Session session){
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Method to close the Sessionfactory
     */
    public static void exit(){
        getSf().close();
    }
}

