package prisc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

//TODO: Update exceptions treatment

/**
 * Configures Hibernate in the application, creating methods to get the Sessions Factory and to turn off
 * the register when the application is closed.
 */
public class HibernateUtil {

    /* Storing and managing the configurations needed to run hibernate. */
    private static StandardServiceRegistry registry;

    /*
    Created once during application startup and shared throughout the application's.
    Provides efficient interaction between Java objects and the database.
     */
    private static SessionFactory sessionFactory;

    /**
     * Gets an instance of SessionFactory, Hibernate's main interface for creating sessions with the database.
     * A session is a unit of work that encapsulates read and write operations on the database.
     * It implements the singleton pattern concept, checking if a session has already been created.
     */
    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                /*
                Standard Service Registry creation.
                The configure() method gets the Hibernate specif setting, such as the database URL, access credentials
                and other options from file hibernate.cfg.xml.
                 */
                registry = new StandardServiceRegistryBuilder().configure().build();

                /*
                Metadata Source creation
                It aggregates and manages different sources or metadata, which can include JPA (Java Persistence API)
                annotations, XML mapping files or Java classes annotated with mapping information.
                This is where you tell Hibernate about your entities, their mappings and other settings.
                 */
                MetadataSources sources = new MetadataSources(registry);


                /*
                 Metadata creation
                 The metadata represents the actual Hibernate metadata after it has been built from the sources.
                 In Hibernate context, metadata refers to the detailed  information about the mapping between Java
                  objects and database tables. Some of the mainly components of Hibernate metadata are: Class mapping,
                  Annotations or XML Mapping Files, Relationship between classes, Primary and secondary keys and
                  Cache configurations (if applicable).
                 */
                Metadata metadata = sources.getMetadataBuilder().build();

                /*
                 SessionFactory creation
                 Responsible for creating Session instances, which are objects that provide a unit of
                  work and act as a bridge between Java objects and database operations. Sessions encapsulates
                  database operations.
                 Furthermore, the Session Factory works transaction management (ensuring the atomicity and
                 consistency of database operations) and connection pooling (optimizing performance by reusing
                 database connections).
                 */
                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    /**
     Closes the Hibernate registry to release resources, ensure the proper completion of Hibernate operations, and
     prevent resource leaks.
     */
    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
