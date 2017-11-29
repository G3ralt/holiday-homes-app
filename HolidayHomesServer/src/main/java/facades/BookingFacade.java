package facades;

import customExceptions.DBException;
import entity.Booking;
import javax.persistence.EntityManager;

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
}
