package facades;

import entity.Place;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PlaceFacade {
    
    private final EntityManager EM;

    public PlaceFacade(EntityManager EM) {
        this.EM = EM;
    }

    public List<Place> getAllPlaces() {
        try {
            EM.getTransaction().begin();
            Query q = EM.createQuery("Select p from SEED_PLACE p");
            EM.getTransaction().commit();
            return q.getResultList();
        } catch (Exception e) {
        }
        return null;
    }
    
        public Place registerPlace(Place place) {
        try {
            EM.getTransaction().begin();
            EM.persist(place);
            EM.getTransaction().commit();
        } finally {
            EM.close();
        }
        return place;
    }
    
}
