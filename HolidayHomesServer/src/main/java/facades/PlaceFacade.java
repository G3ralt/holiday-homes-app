package facades;

import customExceptions.DBException;
import entity.Place;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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

    //Creates new location in the database, returns null if failed
    public void createNewPlace(Place location) throws DBException {
        try {
            EM.getTransaction().begin();
            EM.persist(location);
            EM.getTransaction().commit();
            
        } catch (Exception e) {
            throw new DBException("facades.PlaceFacade.createNewPlace");
        }
    }

    

    /*
        This method is used for adding ratings for locations given the user and the location name.
        Throws DBException if the Database refuses the creation.
     */
    public void addRatingForPlace(String placeName, int rating, String userName) throws DBException {
        try {
            EM.getTransaction().begin();
            Query q = EM.createNativeQuery("INSERT INTO place_rating (place_name, rating, user_name) VALUES (?, ?, ?);");
            q.setParameter(1, placeName);
            q.setParameter(2, rating);
            q.setParameter(3, userName);
            q.executeUpdate();
            EM.getTransaction().commit();

        } catch (Exception e) {
            throw new DBException("facades.PlaceFacade.addRatingForPlace");
        }

    }
    
    /*
        This method is used to retrieve the rating for a specific location given its name.
        The method is used by getAllLocations and getLocation.
        Throws DBExceptions if there is something wrong with the Database.
     */
    private double getRatingForLocation(String locationName) throws DBException {
        double rating = 0;

        try {
            DecimalFormat df = new DecimalFormat(".#");
            Query q = EM.createNativeQuery("SELECT AVG(rating) FROM ratings WHERE location_name = '" + locationName + "';");
            BigDecimal result = (BigDecimal) q.getSingleResult(); //get the result from DB
            if (result != null) {
                rating = Double.parseDouble(df.format(result)); //format the result and parse it to double
            }

        } catch (Exception e) {
            throw new DBException("facades.LocationFacade.getRatingForLocation");
        }
        return rating;
    }

    /*
        This method is used to check if the user has already voted for a specific location.
        Return true 
     */
    private boolean hasUserVoted(String userName, String locationName) throws DBException {
        try {
            Query q = EM.createNativeQuery("SELECT count(rating) FROM ratings WHERE location_name = ? AND user_name = ?;");
            q.setParameter(1, locationName);
            q.setParameter(2, userName);
            long count = (long) q.getSingleResult();
            return (count > 0); //Has voted if count > 0
        } catch (Exception e) {
            throw new DBException("facades.LocationFacade.hasUserVoted");
        }
    }
}
