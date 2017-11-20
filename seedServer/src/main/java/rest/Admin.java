package rest;

import entity.User_Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.UserFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import entity.User;
import java.util.ArrayList;

@Path("demoadmin")
//@RolesAllowed("Admin")
public class Admin {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu_development");
    private UserFacade uf = new UserFacade(emf);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllUsers() {
        List<User> users = uf.getAllUsers();
        List<User_Map> mappedUsers = new ArrayList<>();

        for (User user : users) {
            mappedUsers.add(new User_Map(user));
        }

        String result = gson.toJson(mappedUsers);
        return result;
    }

}
