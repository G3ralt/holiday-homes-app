package facades;

import entity.Place;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

public class PlaceFacade {

    EntityManagerFactory emf;

    public PlaceFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Place> getAllPlaces() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createQuery("Select p from SEED_PLACE p");
            em.getTransaction().commit();
            return q.getResultList();
        } catch (Exception e) {
        }
        return null;
    }
    
        public Place registerPlace(Place place) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(place);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return place;
    }
    
}
