package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FacadeFactory {

    private final EntityManagerFactory EMF;
    private final EntityManager EM;
    
    private UserFacade userFacade;
    private PlaceFacade placeFacade;
    private RentableFacade rentableFacade;

    public FacadeFactory() {
        this.EMF = Persistence.createEntityManagerFactory("pu_development");
        this.EM = EMF.createEntityManager();
    }

    public UserFacade getUserFacade() {
        return userFacade;
    }

    public void setUserFacade() {
        this.userFacade = new UserFacade(EM);
    }
    
    public void close() {
        this.EM.close();
        this.EMF.close();
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

    public void setRentableFacade(RentableFacade rentableFacade) {
        this.rentableFacade = rentableFacade;
    }
    
    
}
