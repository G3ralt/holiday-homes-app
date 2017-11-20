/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Role;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import facades.FacadeFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import security.PasswordStorage;

/**
 * REST Web Service
 *
 * @author Kasper
 */
@Path("register")
public class Register {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
    public String registerUser(String user) {
        entity.User us = null;
        try {
            JsonObject json = new JsonParser().parse(user).getAsJsonObject();
            String username = json.get("username").getAsString();
            String password = json.get("password").getAsString();
            entity.User newUser = new entity.User(username, password);
            Role userRole = new Role("User");
            newUser.addRole(userRole);
            us = FF.getUserFacade().registerUser(newUser);
        } catch (PasswordStorage.CannotPerformOperationException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gson.toJson(us);
    }
    
}
