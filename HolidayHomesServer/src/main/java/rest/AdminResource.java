package rest;

import entity.User_Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import entity.User;
import facades.FacadeFactory;
import java.util.ArrayList;
import javax.annotation.security.RolesAllowed;

@Path("demoadmin")
//@RolesAllowed("Admin")
public class AdminResource {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final FacadeFactory FF;

    public AdminResource() {
        FF = new FacadeFactory();
        FF.setUserFacade();
    }

    @GET
    //@Produces(MediaType.APPLICATION_JSON)
    public String getAllUsers() {
        List<User> users = FF.getUserFacade().getAllUsers();
        List<User_Map> mappedUsers = new ArrayList<>();

        for (User user : users) {
            mappedUsers.add(new User_Map(user));
        }

        String result = gson.toJson(mappedUsers);
        return result;
    }

}
