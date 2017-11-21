package rest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import entity.Place;
import facades.FacadeFactory;
import javax.ws.rs.core.Response;
import static rest.JSONConverter.*;

@Path("places")
public class PlaceResource {

    private final FacadeFactory FF;

    public PlaceResource() {
        FF = new FacadeFactory();
        FF.setPlaceFacade();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() {
        return getJSONfromObject(FF.getPlaceFacade().getAllPlaces());
    }

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewPlace(String jsonString) {
        try {
            Place place = getPlaceFromJSON(jsonString);
            FF.getPlaceFacade().createNewPlace(place);
            return Response.status(201).entity(getJSONfromObject("Location created!")).build();

        } catch (Exception e) {
            return Response.status(503).entity(e.getMessage()).build();

        } finally {
            FF.close();
        }
    }
    
    @Path("/addRating")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewRatingForPlace(String jsonString) {
        try {
            //Get the information from the request
            JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
            String locationName = json.get("locationName").getAsString();
            String userName = json.get("username").getAsString();
            int rating = json.get("rating").getAsInt();
            
            FF.getPlaceFacade().addRatingForLocation(locationName, rating, userName);
            
            return Response.status(201).entity(getJSONfromObject("Rating added!")).build();
        } catch (Exception e) {
            return Response.status(503).entity(e.getMessage()).build();
        }
    }

}
