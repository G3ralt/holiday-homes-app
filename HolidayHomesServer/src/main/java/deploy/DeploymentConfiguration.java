package deploy;

import entity.*;
import facades.FacadeFactory;
import facades.UserFacade;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import security.Secret;

@WebListener
public class DeploymentConfiguration implements ServletContextListener {

    public static String PU_NAME = "PU-Local";

    @Override
    @SuppressWarnings("empty-statement")
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("######################################################################################");
        System.out.println("############################ In ContextIntialized ####################################");
        System.out.println("######################################################################################");

        //Handling init-params from the properties file (secrets that should not be pushed to GIT)
        InputStream input = null;
        Properties prop = new Properties();
        try {
            input = getClass().getClassLoader().getResourceAsStream("/config.properties");;
            if (input == null) {
                System.out.println("Could not load init-properties");
                return;
            }
            prop.load(input);
            Secret.SHARED_SECRET = prop.getProperty("tokenSecret").getBytes();
            input.close();

        } catch (IOException ex) {
            Logger.getLogger(DeploymentConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
        ServletContext context = sce.getServletContext();

        boolean makeTestUser = context.getInitParameter("makeTestUser").toLowerCase().equals("true");
        if (makeTestUser) {
            EntityManager em = Persistence.createEntityManagerFactory("pu_development").createEntityManager();
            try {
                if (em.find(User.class, "user") == null) {
                    System.out.println("Creating TEST Users");
                    em.getTransaction().begin();
                    Role userRole = new Role("User");
                    Role adminRole = new Role("Admin");
                    User user = new User("user", "test");
                    user.addRole(userRole);
                    User admin = new User("admin", "test");
                    admin.addRole(adminRole);
                    User both = new User("user_admin", "test");
                    both.addRole(userRole);
                    both.addRole(adminRole);
                    em.persist(userRole);
                    em.persist(adminRole);
                    em.persist(user);
                    em.persist(admin);
                    em.persist(both);
                    em.getTransaction().commit();
                    System.out.println("Created TEST Users");
                    
                    System.out.println("Creating Test places");
                    Place place1 = new Place("Aloha Beach Camp", "Really nice place to stay for the Summer! Malibu, California", "https://s3-media2.fl.yelpcdn.com/bphoto/GuFvtho6Ok1jZVPJfRiAWA/o.jpg", 34.021724, -118.830704, user);
                    Place place2 = new Place("Green Park International Stadium", "Green Park Stadium is a 48,000 capacity floodlit multi-purpose stadium located in Kanpur, India, and the home of the Uttar Pradesh cricket team. The stadium is under the control of the Sports Department Uttar Pradesh.", "http://images.indianexpress.com/2017/09/green-park-upca-m.jpg", 26.482391, 80.349378, user);
                    Place place3 = new Place("Mafikeng Game Reserve", "Mafikeng Game Reserve is a game reserve in South Africa. Located in the city of Mafikeng, the reserve includes 46 square kilometres of Kalahari and Acacia bushveld,[1] serving as the home of rhinoceroses, giraffes, gemsboks, buffaloes, wildebeests and springboks.", "https://media-cdn.tripadvisor.com/media/photo-s/03/fe/e0/7b/mafikeng-game-reserve.jpg", 25.866786, 25.722544, user);
                    Place place4 = new Place("Menara Gardens", "12th-century botanical garden with a scenic pavilion, artificial lake & mountain views. Marrakech, Morocco.", "https://c1.staticflickr.com/4/3385/3343261617_42455fc204_b.jpg", 31.36, 8.01, user);
                    em.getTransaction().begin();
                    em.persist(place1);
                    em.persist(place2);
                    em.persist(place3);
                    em.persist(place4);
                    em.getTransaction().commit();
                    System.out.println("Created Test Places");
                    
                    System.out.println("Creating Test Ratings for Places");
                    FacadeFactory FF = new FacadeFactory();
                    FF.setPlaceFacade();
                    FF.getPlaceFacade().addRatingForPlace("Aloha Beach Camp", 3, "user");
                    FF.getPlaceFacade().addRatingForPlace("Aloha Beach Camp", 5, "admin");
                    FF.getPlaceFacade().addRatingForPlace("Aloha Beach Camp", 5, "user_admin");
                    FF.getPlaceFacade().addRatingForPlace("Green Park International Stadium", 3, "user_admin");
                    FF.getPlaceFacade().addRatingForPlace("Green Park International Stadium", 5, "user");
                    FF.getPlaceFacade().addRatingForPlace("Green Park International Stadium", 5, "admin");
                    FF.getPlaceFacade().addRatingForPlace("Mafikeng Game Reserve", 3, "admin");
                    FF.getPlaceFacade().addRatingForPlace("Mafikeng Game Reserve", 5, "user");
                    FF.getPlaceFacade().addRatingForPlace("Mafikeng Game Reserve", 5, "user_admin");
                    System.out.println("Created Test Ratings for Places");
                    
                    System.out.println("Creating Test Rentables");
                    Rentable rent1 = new Rentable("Marriot Hotel", "Kalvebod Brygge 5", "Copenhagen", 1560, "Denmark", 500, "http://aff.bstatic.com/images/hotel/max300/303/30373488.jpg", "The Copenhagen Marriott Hotel is ready to help you create lasting memories here in Denmark's beautiful capital city. We offer an unparalleled location right on the waterfront, just moments from popular attractions including Tivoli Gardens and the legendary Stroget. Uncommonly spacious, stylishly appointed rooms and suites provide free Wi-Fi, ample work areas and views of either the water or of the city of Copenhagen. Additional in-room perks include marble bathrooms and deluxe pillowtop bedding.", admin);
                    Rentable rent2 = new Rentable("The Witcher`s Fortress", "Somewhere in the mountains", "Kaer Morhen", 6969, "Kaedwen", 6996, "https://i.ytimg.com/vi/TJuPBBw-l-M/maxresdefault.jpg", "Kaer Morhen is an old keep where witchers used to be trained. The name is a corruption of the Caer a'Muirehen, which means \"Old Sea Keep\" in the Elder Speech due to the presence of fossilized sea creatures embedded in stones on which it was built.[1] It is located in the mountains of the kingdom of Kaedwen, right off the Gwenllech river.", admin);
                    em.getTransaction().begin();
                    em.persist(rent2);
                    em.persist(rent1);
                    em.getTransaction().commit();
                    System.out.println("Created Test Rentables");
                    
                    System.out.println("Creating Test Rating for Rentables");
                    FF.setRentableFacade();
                    FF.getRentableFacade().addRatingForRentable(rent1.getRentableName(), 3, "user");
                    FF.getRentableFacade().addRatingForRentable(rent1.getRentableName(), 5, "user_admin");
                    FF.getRentableFacade().addRatingForRentable(rent1.getRentableName(), 2, "admin");
                    FF.getRentableFacade().addRatingForRentable(rent2.getRentableName(), 3, "user");
                    FF.getRentableFacade().addRatingForRentable(rent2.getRentableName(), 5, "user_admin");
                    FF.getRentableFacade().addRatingForRentable(rent2.getRentableName(), 3, "admin");
                    System.out.println("Created Test Rating for Rentables");
                    
                    
                }
            } catch (Exception ex) {
                Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
                em.getTransaction().rollback();
            } finally {
                em.close();
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
