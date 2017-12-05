package rest;

import entity.Booking;
import facades.FacadeFactory;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.*;
import javax.ws.rs.*;
import static rest.JSONConverter.getJSONfromObject;
import static rest.JSONConverter.*;

@Path("booking")
@RolesAllowed("User")
public class BookingResource {

    private final FacadeFactory FF;

    @Context
    private UriInfo context;

    public BookingResource() {
        FF = new FacadeFactory();
        FF.setBookingFacade();
    }

    @Path("/create")
    @RolesAllowed("User")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBooking(String jsonString) {
        try {
            Booking booking = getBookingFromJSON(jsonString);

            FF.getBookingFacade().createNewBooking(booking);

            return Response.status(201).entity(getJSONfromObject("Booking created!")).build();

        } catch (Exception e) {
            return Response.status(503).entity(getJSONfromObject(e.getMessage())).build();

        } finally {
            FF.close();
        }
    }

    @Path("/allForUser/{username}")
    @RolesAllowed("User")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBookingsForUser(@PathParam("username") String username) {
        try {
            List<Booking> list = FF.getBookingFacade().getBookingsByUser(username);

            return Response.status(200).entity(getJSONfromObject(list)).build();
            
        } catch (Exception e) {
            return Response.status(503).entity(getJSONfromObject(e.getMessage())).build(); //Service unavailable if something is wrong
        }
    }

}
