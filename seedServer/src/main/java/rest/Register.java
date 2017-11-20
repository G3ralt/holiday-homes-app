package rest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import customExceptions.CreateNewUserException;
import entity.*;
import javax.ws.rs.core.*;
import javax.ws.rs.*;
import facades.FacadeFactory;
import static rest.JSONConverter.*;

@Path("register")
public class Register {

    private final FacadeFactory FF;

    public Register() {
        FF = new FacadeFactory();
        FF.setUserFacade();
    }

    @Context
    private UriInfo context;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(String user) {
        try {
            entity.User us = null;
            JsonObject json = new JsonParser().parse(user).getAsJsonObject();
            String username = json.get("username").getAsString();
            String password = json.get("password").getAsString();
            
            entity.User newUser = new entity.User(username, password);
            
            Role userRole = new Role("User");
            newUser.addRole(userRole);
            us = FF.getUserFacade().registerUser(newUser);
            
            return Response.status(201).entity(getJSONfromObject(us)).build();
        } catch (CreateNewUserException ex) {
            //When username is already in the database
            return Response.status(406).entity(getJSONfromObject(ex.getMessage())).build();

        } catch (Exception ex) {
            //When password hash or token creation fails
            return Response.status(503).entity(getJSONfromObject(ex.getMessage())).build();
            
        } finally {
            FF.close();
        }
    }

}
