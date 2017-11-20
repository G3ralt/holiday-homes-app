package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import facades.PlaceFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import entity.Place;

@Path("places")
public class PlaceRest {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu_development");
    private PlaceFacade pf = new PlaceFacade(emf);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() {
        return gson.toJson(pf.getAllPlaces());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String registerPlace(String place) {
        entity.Place pl = null;
        try {
            JsonObject json = new JsonParser().parse(place).getAsJsonObject();

            String city = json.get("city").getAsString();
            int zip = Integer.parseInt(json.get("zip").getAsString());
            String street = json.get("street").getAsString();
            String gpsLocation = json.get("gpsLocation").getAsString();
            String description = json.get("description").getAsString();
            int rating = Integer.parseInt(json.get("rating").getAsString());
            String imgUri = json.get("imgUri").getAsString();
            Place newPlace = new Place(city, zip, street, gpsLocation, description, rating, imgUri);

            pl = pf.registerPlace(newPlace);
        } catch (JsonSyntaxException | NumberFormatException ex) {
            Logger.getLogger(PlaceRest.class.getName()).log(Level.SEVERE, null, ex);
        }   
        return gson.toJson(pl);
    }

}
