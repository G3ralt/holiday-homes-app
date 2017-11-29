package facades;

import javax.persistence.*;

public class FacadeFactory {

    private final EntityManagerFactory EMF;
    private final EntityManager EM;
    
    private UserFacade userFacade;
    private PlaceFacade placeFacade;
    private RentableFacade rentableFacade;
    private BookingFacade bookingFacade;

    public FacadeFactory() {
        this.EMF = Persistence.createEntityManagerFactory("pu_development");
        this.EM = EMF.createEntityManager();
    }

    public BookingFacade getBookingFacade() {
        return bookingFacade;
    }

    public void setBookingFacade() {
        this.bookingFacade = new BookingFacade(EM);
    }

    public UserFacade getUserFacade() {
        return userFacade;
    }

    public void setUserFacade() {
        this.userFacade = new UserFacade(EM);
    }
    
    public PlaceFacade getPlaceFacade() {
        return placeFacade;
    }

    public void setPlaceFacade() {
        this.placeFacade = new PlaceFacade(EM);
    }

    public RentableFacade getRentableFacade() {
        return rentableFacade;
    }

    public void setRentableFacade() {
        this.rentableFacade = new RentableFacade(EM);
    }
    
     public void close() {
        this.EM.close();
        this.EMF.close();
    }
    
}
