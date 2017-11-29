package rest;

import entity.Booking;
import facades.FacadeFactory;
import javax.ws.rs.core.*;
import javax.ws.rs.*;
import static rest.JSONConverter.getJSONfromObject;
import static rest.JSONConverter.*;

@Path("booking")
public class BookingResource {

    private final FacadeFactory FF;
    
    @Context
    private UriInfo context;

    public BookingResource() {
        FF = new FacadeFactory();
        FF.setBookingFacade();
    }

    @Path("/create")
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

    
}
