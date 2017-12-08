package facades;

import customExceptions.DBException;
import entity.Booking;
import entity.Place;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class BookingFacade {
    
    private final EntityManager EM;

    public BookingFacade(EntityManager EM) {
        this.EM = EM;
    }
    
    /*
        Creates new Booking in the Database
        Throws DBException if something is wrong with the Database
    */
    public void createNewBooking(Booking booking) throws DBException {
        try {
            EM.getTransaction().begin();
            EM.persist(booking);
            EM.getTransaction().commit();

        } catch (Exception e) {
            throw new DBException("facades.BookingFacade.createNewBooking");
        }
    }
    /*
        Gets all the bookings for a specific user
        Throws DBException if something is wrong with DB
    */
    public List<Booking> getBookingsByUser(String username) throws DBException {
        List<Booking> toReturn;
        try {
            EM.getEntityManagerFactory().getCache().evictAll();
            Query q = EM.createQuery("SELECT b FROM Booking b WHERE b.user.username = :username", Booking.class);
            q.setParameter("username", username);
            toReturn =  q.getResultList();
            
        } catch (Exception e) {
            throw new DBException("facades.BookingFacade.getBookingByUser");
        }
        
        return toReturn;
    }
}
