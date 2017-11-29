package facades;

import customExceptions.CreateNewUserException;
import security.IUserFacade;
import entity.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
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
        
            return EM.find(User.class, id);
         
    }

    public List<User> getAllUsers() {
        
            EM.getTransaction().begin();
            Query q = EM.createQuery("Select u From SEED_USER u");
            
            //Map
            
            EM.getTransaction().commit();
            return q.getResultList();
         
    }

    /*
  Return the Roles if users could be authenticated, otherwise null
     */
    @Override
    public List<String> authenticateUser(String username, String password) {
        try {
            System.out.println("User Before:" + username + ", " + password);
            IUser user = getUserByUserId(username);
            System.out.println("User After:" + user.getUsername() + ", " + user.getPasswordHash());
            return user != null && PasswordStorage.verifyPassword(password, user.getPasswordHash()) ? user.getRolesAsStrings() : null;
        } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException ex) {
            throw new NotAuthorizedException("Invalid username or password", Response.Status.FORBIDDEN);
        }
    }

    public User registerUser(User user) throws CreateNewUserException {
        try {
            EM.getTransaction().begin();
            EM.persist(user);
            EM.getTransaction().commit();
            return user;
        } catch (PersistenceException e) {
            throw new CreateNewUserException("Username already existing in Database.");
        }
    }
}
