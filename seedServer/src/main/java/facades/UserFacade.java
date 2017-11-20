package facades;

import security.IUserFacade;
import entity.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import security.IUser;
import security.PasswordStorage;

public class UserFacade implements IUserFacade {

    private final EntityManager EM;

    public UserFacade(EntityManager EM) {
        this.EM = EM;
    }
    
    @Override
    public IUser getUserByUserId(String id) {
        try {
            return EM.find(User.class, id);
        } finally {
            EM.close();
        }
    }

    public List<User> getAllUsers() {
        try {
            EM.getTransaction().begin();
            Query q = EM.createQuery("Select u From SEED_USER u");
            
            //Map
            
            EM.getTransaction().commit();
            return q.getResultList();
        } finally {
            EM.close();
        }
    }

    /*
  Return the Roles if users could be authenticated, otherwise null
     */
    @Override
    public List<String> authenticateUser(String userName, String password) {
        try {
            System.out.println("User Before:" + userName + ", " + password);
            IUser user = getUserByUserId(userName);
            System.out.println("User After:" + user.getUserName() + ", " + user.getPasswordHash());
            return user != null && PasswordStorage.verifyPassword(password, user.getPasswordHash()) ? user.getRolesAsStrings() : null;
        } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException ex) {
            throw new NotAuthorizedException("Invalid username or password", Response.Status.FORBIDDEN);
        }
    }

    public User registerUser(User user) {
        try {
            EM.getTransaction().begin();
            EM.persist(user);
            EM.getTransaction().commit();
        } finally {
            EM.close();
        }
        return user;
    }
}
