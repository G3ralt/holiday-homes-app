package rest;

import com.google.gson.*;
import customExceptions.DBException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import entity.Place;
import facades.FacadeFactory;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import static rest.JSONConverter.*;

@Path("places")
public class PlaceResource {

    private final FacadeFactory FF;

    public PlaceResource() {
        FF = new FacadeFactory();
        FF.setPlaceFacade();
    }

    @Path("/all")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaces(String jsonString) {
        try {
            JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
            String userName = json.get("username").getAsString();

            List<Place> places = FF.getPlaceFacade().getAllPlaces(userName); //Get the places from Database.

            return Response.status(200).entity(getJSONfromObject(places)).build(); //Return the places as JSON

        } catch (Exception e) {
            return Response.status(503).entity(getJSONfromObject(e.getMessage())).build(); //Service unavailable if something is wrong

        } finally {
            FF.close();
        }
    }

    @Path("/all/{username}")
    @RolesAllowed("User")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlacesForUser(@PathParam("username") String username) {
        try {
            List<Place> places = FF.getPlaceFacade().getAllPlacesForUser(username); //Get the place from Database.

            return Response.status(200).entity(getJSONfromObject(places)).build(); //Return the places as JSON

        } catch (Exception e) {
            return Response.status(503).entity(getJSONfromObject(e.getMessage())).build(); //Service unavailable if something is wrong

        } finally {
            FF.close();
        }
    }

    @Path("/create")
    @RolesAllowed("User")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewPlace(String jsonString) {
        try {
            Place place = getPlaceFromJSON(jsonString);

            FF.getPlaceFacade().createNewPlace(place);

            return Response.status(201).entity(getJSONfromObject("Place created!")).build();

        } catch (DBException e) {
            //When the Place name is already in use
            return Response.status(406).entity(getJSONfromObject(e.getMessage())).build();

        } catch (Exception e) {
            return Response.status(503).entity(getJSONfromObject(e.getMessage())).build();

        } finally {
            FF.close();
        }
    }

    @Path("/checkName/{placeName}")
    @RolesAllowed("User")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkPlaceName(@PathParam("placeName") String placeName) {
        try {
            boolean existing = FF.getPlaceFacade().checkForPlaceName(placeName);

            return existing ? Response.status(409).build() : Response.status(202).build(); //If placeName is used - 409, otherwise 202

        } catch (Exception e) {
            return Response.status(503).entity(getJSONfromObject(e.getMessage())).build(); //Service unavailable if something is wrong

        } finally {
            FF.close();
        }
    }

    @Path("/addRating")
    @RolesAllowed("User")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRatingForPlace(String jsonString) {
        try {
            //Get the information from the request
            JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
            String placeName = json.get("placeName").getAsString();
            String username = json.get("username").getAsString();
            int rating = json.get("rating").getAsInt();

            int userRating = FF.getPlaceFacade().getUserRating(username, placeName);

            if (userRating == 0) { //If user hasn`t rated the place yet
                FF.getPlaceFacade().addRatingForPlace(placeName, rating, username);
            } else { //if user has rated update the rating
                FF.getPlaceFacade().updateRatingForPlace(placeName, rating, username);
            }

            return Response.status(201).entity(getJSONfromObject("Rating added!")).build();

        } catch (Exception e) {
            return Response.status(503).entity(getJSONfromObject(e.getMessage())).build();
        }
    }
}
