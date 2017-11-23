package rest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import customExceptions.DBException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import entity.Rentable;
import facades.FacadeFactory;
import java.util.List;
import javax.ws.rs.core.Response;
import static rest.JSONConverter.*;


//ANTON IS WORKING ON THIS. NOT DONE.
@Path("rentables")
public class RentableResource {

    private final FacadeFactory FF;

    public RentableResource() {
        FF = new FacadeFactory();
        FF.setRentableFacade();
    }

    @Path("/all")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRentables(String jsonString) {
        try {
            JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
            String userName = json.get("username").getAsString();

            List<Rentable> rentables = FF.getRentableFacade().getAllRentables(userName); //Get the locations from Database.

            return Response.status(200).entity(getJSONfromObject(rentables)).build(); //Return the locations as JSON

        } catch (Exception e) {
            return Response.status(503).entity(getJSONfromObject(getJSONfromObject(e.getMessage()))).build(); //Service unavailable if something is wrong

        } finally {
            FF.close();
        }

    }

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewRentable(String jsonString) {
        try {
            Rentable rentable = getRentableFromJSON(jsonString);

            FF.getRentableFacade().createNewRentable(rentable);

            return Response.status(201).entity(getJSONfromObject("Rentable created!")).build();

        } catch (DBException e) {
            //When the Place name is already in use
            return Response.status(406).entity(getJSONfromObject(e.getMessage())).build();

        } catch (Exception e) {
            return Response.status(503).entity(getJSONfromObject(e.getMessage())).build();

        } finally {
            FF.close();
        }
    }

    @Path("/addRating")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewRatingForRentable(String jsonString) {
        try {
            //Get the information from the request
            JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
            String placeName = json.get("placeName").getAsString();
            String userName = json.get("username").getAsString();
            int rating = json.get("rating").getAsInt();

            FF.getPlaceFacade().addRatingForPlace(placeName, rating, userName);

            return Response.status(201).entity(getJSONfromObject("Rating added!")).build();

        } catch (Exception e) {
            return Response.status(503).entity(getJSONfromObject(e.getMessage())).build();
        }
    }

}
