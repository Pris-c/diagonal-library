package prisc.book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import prisc.util.HibernateUtil;
import prisc.util.LibraryPrinter;

import java.util.List;

public class BookRepository {

    private static final Logger logger = LogManager.getLogger(BookRepository.class);

    /**
     * Retrieves all Book objects from the database.
     * This operation involves:
     *   1. Opening a Hibernate session using HibernateUtil.getSessionFactory(),
     *   2. Creating an HQL (Hibernate Query Language) query to select all records from the Book entity,
     *   3. Returning a list of Book objects.
     *
     * If an exception occurs during the process:
     *  - The method logs the error message using the logger,
     *  - Prints a user-friendly error message using the LibraryPrinter class,
     *  - Returns null.
     *
     * @return List<Book> A list containing all Book objects retrieved from the databas

     */
    public List<Book> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Book", Book.class).list();
        } catch (Exception e) {
            logger.error("Error while retrieving Book objects from the database: " + e.getMessage());
            LibraryPrinter.printHibernateException();
            return null;
        }
    }


    /**
     * Saves a Book object to the database.
     * This operation involves:
     *   1. Opening a Hibernate session using HibernateUtil.getSessionFactory(),
     *   2. Starting a Hibernate transaction,
     *   3. Persisting the Book using the `persist` method,
     *   4. Committing the transaction, applying the database changes.
     *
     * If an exception occurs during the process:
     *   - The method logs the error message using the logger,
     *   - Prints a user-friendly error message using the LibraryPrinter class,
     *   - Rolls back the transaction to maintain data consistency.
     *
     * @param book The Book object to be saved.
     */
    public void save(Book book) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while saving Book to the database: " + e.getMessage());
            LibraryPrinter.printHibernateException();
        }
    }


    /**
     * Retrieves a list of Book objects containing the specified title substring.
     *
     * This operation involves:
     *   1. Opening a Hibernate session using HibernateUtil.getSessionFactory(),
     *   2. Creating an HQL (Hibernate Query Language) query to select Book objects
     *      where the title contains the specified substring (case-insensitive),
     *   3. Setting a parameter in the query for the title substring,
     *   4. Returning a list of Book objects matching the criteria.
     *
     * If an exception occurs during the process:
     *  - The method logs the error message using the logger,
     *  - Prints a user-friendly error message using the LibraryPrinter class,
     *  - Returns null.
     *
     * @param title The substring to search for in the titles of books.
     * @return List<Book> A list containing Book objects with titles matching the specified substring.
     */
    public List<Book> findByTitle(String title) {
        String hql = "FROM Book b WHERE lower(b.title) like lower(:titleSubstring)";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("titleSubstring", "%" + title + "%");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error while searching for books with title containing '" + title + "': " + e.getMessage());
            LibraryPrinter.printHibernateException();
        return null;
        }
    }


    /**
     * Retrieves a list of Book objects containing the specified author substring.
     *
     * This operation involves:
     *   1. Opening a Hibernate session using HibernateUtil.getSessionFactory(),
     *   2. Creating an HQL (Hibernate Query Language) query to select Book objects
     *      where the author contains the specified substring (case-insensitive),
     *   3. Setting a parameter in the query for the author substring,
     *   4. Returning a list of Book objects matching the criteria.
     *
     * If an exception occurs during the process:
     *  - The method logs the error message using the logger,
     *  - Prints a user-friendly error message using the LibraryPrinter class,
     *  - Returns null.
     *
     * @param author The substring to search for in the titles of books.
     * @return List<Book> A list containing Book objects with authors matching the specified substring.
     */
    public List<Book> findByAuthor(String author){
        String hql = "FROM Book b WHERE lower(b.author) like lower(:authorSubstring)";

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("authorSubstring", "%" + author + "%");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error while searching for books with author containing '" + author + "': " +e.getMessage());
            LibraryPrinter.printHibernateException();
            return null;
        }
    }


    /**
     * Retrieves a list of Book objects published in the specified year.
     *
     * This operation involves:
     *   1. Opening a Hibernate session using HibernateUtil.getSessionFactory(),
     *   2. Creating an HQL (Hibernate Query Language) query to select Book objects
     *      where the publication year matches the specified year,
     *   3. Setting a parameter in the query for the year,
     *   4. Returning a list of Book objects matching the criteria.
     *
     * If an exception occurs during the process:
     *   - The method logs the error message using the logger,
     *   - Prints a user-friendly error message using the LibraryPrinter class,
     *   - Returns null.
     *
     * @param year The publication year to search for in the books.
     * @return List<Book> A list containing Book objects published in the specified year.
     */
    public List<Book> findByYear(int year){
        String hql = "FROM Book b WHERE b.year = :year";

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("year", year);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error while searching for books from " + year + ": " + e.getMessage());
            LibraryPrinter.printHibernateException();
            return null;
        }
    }


    /**
     * Retrieves a Book object that corresponds to the specified ID.
     *
     * This operation involves:
     *   1. Opening a Hibernate session using HibernateUtil.getSessionFactory(),
     *   2. Utilizing the built-in Hibernate method to directly retrieve a Book object by its unique identifier,
     *   3. Returning the Book object that matches the criteria.
     *
     * If an exception occurs during the process:
     *   - The method logs the error message using the logger,
     *   - Prints a user-friendly error message using the LibraryPrinter class,
     *   - Returns null.
     *
     * @param bookId The unique identifier of the book.
     * @return Book A Book object that corresponds to the specified ID.
     */
    public Book findById(int bookId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Book.class, bookId);
        } catch (Exception e) {
            logger.error("Error while searching for book id = " + bookId + ": " + e.getMessage());
            LibraryPrinter.printHibernateException();
            return null;
        }
    }


    /**
     * Updates a Book object in the database.
     *
     * This operation involves:
     *   1. Opening a Hibernate session using HibernateUtil.getSessionFactory(),
     *   2. Beginning a transaction,
     *   3. Merging the state of the provided Book object with the persistent state in the database,
     *   4. Committing the transaction.
     *
     * If an exception occurs during the process:
     *   - The method logs the error message using the logger,
     *   - Prints a user-friendly error message using the LibraryPrinter class,
     *   - Rolls back the transaction to maintain data consistency.
     *
     * @param book The Book object to be updated.
     */
    public void update(Book book){
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(book);
            transaction.commit();
        } catch (Exception e){
            if (transaction!= null){
                transaction.rollback();
            }
            logger.error("Error while updating Book to the database: " + e.getMessage());
            LibraryPrinter.printHibernateException();
        }
    }


    /**
     * Deletes a Book object from the database based on its unique identifier.
     *
     * This operation involves:
     *   1. Opening a Hibernate session using HibernateUtil.getSessionFactory(),
     *   2. Beginning a transaction,
     *   3. Retrieving the Book object by its unique identifier (ID),
     *   4. Removing the specified Book object from the database,
     *   5. Committing the transaction if the Book object is found.
     *
     * If an exception occurs during the process:
     *   - The method logs the error message using the logger,
     *   - Prints a user-friendly error message using the LibraryPrinter class,
     *   - Rolls back the transaction to maintain data consistency.
     *
     * @param bookId The unique identifier of the Book object to be deleted.
     */
    public void delete(int bookId){
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Book book = session.get(Book.class, bookId);

            if (book != null){
                session.remove(book);
                transaction.commit();
            }
        } catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while deleting Book to the database: " + e.getMessage());
            LibraryPrinter.printHibernateException();
        }

    }


}