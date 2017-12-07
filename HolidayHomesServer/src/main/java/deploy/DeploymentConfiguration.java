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
                    
                    User anton = new User("Anton", "test");
                    anton.addRole(userRole);
                    User alex = new User("Alex", "test");
                    alex.addRole(userRole);
                    User andrian = new User("Andrian", "test");
                    andrian.addRole(userRole);
                    User kasper = new User("Kasper", "test");
                    kasper.addRole(userRole);
                    
                    em.persist(userRole);
                    em.persist(adminRole);
                    em.persist(user);
                    em.persist(admin);
                    em.persist(both);
                    em.persist(anton);
                    em.persist(alex);
                    em.persist(andrian);
                    em.persist(kasper);
                    em.getTransaction().commit();
                    System.out.println("Created TEST Users");
                    
                    System.out.println("Creating Test places");
                    Place place1 = new Place("Sunny Beach");
                    place1.setDescription("Sunny Beach is a resort on Bulgaria’s Black Sea coast. It’s known for its water sports, sand dunes and nightlife. Its waterfront is lined by a long sandy beach. Inland are a paintball park, a go-kart track and a water park many slides. The yacht marina at Sveti Vlas is to the northeast. To the south, the town of Nessebar has ancient ruins and an Archaeological Museum with millennia-old pottery and bronze vessels.");
                    place1.setImgURL("https://steemit-production-imageproxy-upload.s3.amazonaws.com/DQmVSteK5MguNFqj4fgz1bF9reDoEJypSu4JAxe42C1BzkT");
                    place1.setUserName(user);
                    place1.setGpsLat(42.687051);
                    place1.setGpsLong(27.712926);
                    
                    Place place2 = new Place("Green Park International Stadium", "Green Park Stadium is a 48,000 capacity floodlit multi-purpose stadium located in Kanpur, India, and the home of the Uttar Pradesh cricket team. The stadium is under the control of the Sports Department Uttar Pradesh.", "http://images.indianexpress.com/2017/09/green-park-upca-m.jpg", 26.482391, 80.349378, user);
                    Place place3 = new Place("Mafikeng Game Reserve", "Mafikeng Game Reserve is a game reserve in South Africa. Located in the city of Mafikeng, the reserve includes 46 square kilometres of Kalahari and Acacia bushveld,[1] serving as the home of rhinoceroses, giraffes, gemsboks, buffaloes, wildebeests and springboks.", "https://media-cdn.tripadvisor.com/media/photo-s/03/fe/e0/7b/mafikeng-game-reserve.jpg", 25.866786, 25.722544, user);
                    Place place4 = new Place("Menara Gardens", "12th-century botanical garden with a scenic pavilion, artificial lake & mountain views. Marrakech, Morocco.", "https://c1.staticflickr.com/4/3385/3343261617_42455fc204_b.jpg", 31.36, 8.01, user);
                    
                    Place place5 = new Place("Frederiksborg Castle");
                    place5.setDescription("Frederiksborg Castle (Danish: Frederiksborg Slot) is a palatial complex in Hillerød, Denmark. It was built as a royal residence for King Christian IV of Denmark-Norway in the early 17th century, replacing an older castle acquired by Frederick II and becoming the largest Renaissance residence in Scandinavia. Situated on three islets in the Slotssøen (castle lake), it is adjoined by a large formal garden in the Baroque style.");
                    place5.setImgURL("https://www.g3ralt.club/uploads/Frederiksborg Castle.png");
                    place5.setUserName(user);
                    place5.setGpsLat(55.934332);
                    place5.setGpsLong(12.30046);
                    
                    em.getTransaction().begin();
                    em.persist(place1);
                    em.persist(place2);
                    em.persist(place3);
                    em.persist(place4);
                    em.persist(place5);
                    em.getTransaction().commit();
                    System.out.println("Created Test Places");
                    
                    System.out.println("Creating Test Ratings for Places");
                    FacadeFactory FF = new FacadeFactory();
                    FF.setPlaceFacade();
                    FF.getPlaceFacade().addRatingForPlace("Sunny Beach", 2, "user");
                    FF.getPlaceFacade().addRatingForPlace("Sunny Beach", 2, "Alex");
                    FF.getPlaceFacade().addRatingForPlace("Sunny Beach", 4, "Anton");
                    FF.getPlaceFacade().addRatingForPlace("Sunny Beach", 3, "Andrian");
                    FF.getPlaceFacade().addRatingForPlace("Sunny Beach", 5, "Kasper");
                    FF.getPlaceFacade().addRatingForPlace("Frederiksborg Castle", 1, "user");
                    FF.getPlaceFacade().addRatingForPlace("Frederiksborg Castle", 3, "Alex");
                    FF.getPlaceFacade().addRatingForPlace("Frederiksborg Castle", 4, "Anton");
                    FF.getPlaceFacade().addRatingForPlace("Frederiksborg Castle", 4, "Andrian");
                    FF.getPlaceFacade().addRatingForPlace("Frederiksborg Castle", 5, "Kasper");
                    System.out.println("Created Test Ratings for Places");
                    
                    System.out.println("Creating Test Rentables");
                    
                    Rentable rent1 = new Rentable("2 room apartment of 66 m²");
                    rent1.setStreet("Fremads Alle");
                    rent1.setCity("Copenhagen");
                    rent1.setZipCode(2300);
                    rent1.setCountry("Denmark");
                    rent1.setPrice(2500);
                    rent1.setImgURL("https://www.g3ralt.club/uploads/2 room apartment of 66 m².png");
                    rent1.setDescription("København S: Lejelejlighed, 2 soveværelser, fælles stue og køkken, samt wc. leje i 24 mdr. , charmerende Lejlighed beligende på 2. sal i en ejendom med 3 lejligheder. Der er adgang fra lejligheden til loftsrum medskrå vægge, så man kun kan stå oprejst i noget af rummet.  Der er fremvisning d. 15/12 kl. 12.15.");
                    rent1.setAdmin(admin);
                    rent1.setGpsLat(55.64947189);
                    rent1.setGpsLong(12.6132404);
                    
                    Rentable rent2 = new Rentable("2 værelses lejlighed i Hillerød");
                    rent2.setStreet("Helsingørsgade 13");
                    rent2.setCity("Copenhagen");
                    rent2.setZipCode(3400);
                    rent2.setCountry("Denmark");
                    rent2.setPrice(3515);
                    rent2.setImgURL("https://www.g3ralt.club/uploads/2 værelses lejlighed i Hillerød.png");
                    rent2.setDescription("Husleje:  14.060 ?Indskud:  5 mdr. leje ?Ledig pr.:  01.01.18");
                    rent2.setAdmin(admin);
                    rent2.setGpsLat(55.9303898);
                    rent2.setGpsLong(12.3043457);
                    
                    Rentable rent3 = new Rentable("Apartment, Roskildevej");
                    rent3.setStreet("Roskildevej");
                    rent3.setCity("Copenhagen");
                    rent3.setZipCode(2610);
                    rent3.setCountry("Denmark");
                    rent3.setPrice(2940);
                    rent3.setImgURL("https://www.g3ralt.club/uploads/Apartment, Roskildevej.jpg");
                    rent3.setDescription("Here it is a bright and welcoming apartment of 112 square meters, with spaciousness, contemporary elements and an ideal floor plan just a few of the key words.");
                    rent3.setAdmin(admin);
                    rent3.setGpsLat(55.671442);
                    rent3.setGpsLong(12.457766);
                    
                    Rentable rent4 = new Rentable("Apartment, Godthåbsvej");
                    rent4.setStreet("Godthåbsvej, 93 m2, 4 rooms");
                    rent4.setCity("Copenhagen");
                    rent4.setZipCode(2000);
                    rent4.setCountry("Denmark");
                    rent4.setPrice(4125);
                    rent4.setImgURL("https://www.g3ralt.club/uploads/Godthåbsvej, 93 m2, 4 rooms.jpg");
                    rent4.setDescription("Apartment, Godthåbsvej, 2000 Frederiksberg. Description is on its way!");
                    rent4.setAdmin(admin);
                    rent4.setGpsLat(55.688969);
                    rent4.setGpsLong(12.527065);
                    
                    Rentable rent5 = new Rentable("Parking of 10 m²");
                    rent5.setStreet("Lyngbyvej 20");
                    rent5.setCity("Copenhagen");
                    rent5.setZipCode(2100);
                    rent5.setCountry("Denmark");
                    rent5.setPrice(150);
                    rent5.setImgURL("https://www.g3ralt.club/uploads/Parking of 10 m².png");
                    rent5.setDescription("Savner du en fast parkeringsplads lige ved Parken? Parkeringspladserne er beliggende i kælder. Den oplyste pris for en parkeringsplads er eksklusiv moms.");
                    rent5.setAdmin(admin);
                    rent5.setGpsLat(55.7086981);
                    rent5.setGpsLong(12.5631731);
                    
                    Rentable rent6 = new Rentable("Room for rent in Ballerup");
                    rent6.setStreet("Gl Rådhusvej");
                    rent6.setCity("Copenhagen");
                    rent6.setZipCode(2750);
                    rent6.setCountry("Denmark");
                    rent6.setPrice(875);
                    rent6.setImgURL("https://www.g3ralt.club/uploads/Room for rent in Ballerup.jpg");
                    rent6.setDescription("This is a 90 m2 linked houses, almost completely renovated in 2015(except bathroom in 2010). Fully working kitchen with dishwasher. Various spices is included in the rent. ");
                    rent6.setAdmin(admin);
                    rent6.setGpsLat(55.729993);
                    rent6.setGpsLong(12.368114);
                    
                    Rentable rent7 = new Rentable("Hotel Fenix");
                    rent7.setStreet("Sunny Beach");
                    rent7.setCity("Slunchev Briag");
                    rent7.setZipCode(8240);
                    rent7.setCountry("Bulgaria");
                    rent7.setPrice(696);
                    rent7.setImgURL("https://cdn.ostrovok.ru/t/1024x768/carsolize/af/05/af055ec05a90558b5f5badc8178b223801fb1ad5.jpeg");
                    rent7.setDescription("After a total renovation for Summer 2005, Fenix Hotel in Sunny Beach now offers even wider range of leisure facilities which will surely delight both, its new and regular guests. Set on the beach right next to the main promenade, the hotel's position grants an easy access to the beach, almost as convenient as to the hotel's large and pleasant swimming pool.");
                    rent7.setAdmin(admin);
                    rent7.setGpsLat(42.687552);
                    rent7.setGpsLong(27.709855);
                    
                    em.getTransaction().begin();
                    em.persist(rent1);
                    em.persist(rent2);
                    em.persist(rent3);
                    em.persist(rent4);
                    em.persist(rent5);
                    em.persist(rent6);
                    em.persist(rent7);
                    em.getTransaction().commit();
                    System.out.println("Created Test Rentables");
                    
                    System.out.println("Creating Test Rating for Rentables");
                    FF.setRentableFacade();
                    FF.getRentableFacade().addRatingForRentable(rent1.getRentableName(), 1, "user");
                    FF.getRentableFacade().addRatingForRentable(rent1.getRentableName(), 4, "Andrian");
                    FF.getRentableFacade().addRatingForRentable(rent1.getRentableName(), 5, "Alex");
                    FF.getRentableFacade().addRatingForRentable(rent1.getRentableName(), 2, "Anton");
                    FF.getRentableFacade().addRatingForRentable(rent1.getRentableName(), 2, "Kasper");
                    
                    FF.getRentableFacade().addRatingForRentable(rent2.getRentableName(), 1, "user");
                    FF.getRentableFacade().addRatingForRentable(rent2.getRentableName(), 3, "Andrian");
                    FF.getRentableFacade().addRatingForRentable(rent2.getRentableName(), 5, "Alex");
                    FF.getRentableFacade().addRatingForRentable(rent2.getRentableName(), 2, "Anton");
                    FF.getRentableFacade().addRatingForRentable(rent2.getRentableName(), 2, "Kasper");
                    
                    FF.getRentableFacade().addRatingForRentable(rent3.getRentableName(), 1, "user");
                    FF.getRentableFacade().addRatingForRentable(rent3.getRentableName(), 4, "Andrian");
                    FF.getRentableFacade().addRatingForRentable(rent3.getRentableName(), 5, "Alex");
                    FF.getRentableFacade().addRatingForRentable(rent3.getRentableName(), 5, "Anton");
                    FF.getRentableFacade().addRatingForRentable(rent3.getRentableName(), 2, "Kasper");
                    
                    FF.getRentableFacade().addRatingForRentable(rent4.getRentableName(), 1, "user");
                    FF.getRentableFacade().addRatingForRentable(rent4.getRentableName(), 4, "Andrian");
                    FF.getRentableFacade().addRatingForRentable(rent4.getRentableName(), 5, "Alex");
                    FF.getRentableFacade().addRatingForRentable(rent4.getRentableName(), 3, "Anton");
                    FF.getRentableFacade().addRatingForRentable(rent4.getRentableName(), 2, "Kasper");
                    
                    FF.getRentableFacade().addRatingForRentable(rent5.getRentableName(), 1, "user");
                    FF.getRentableFacade().addRatingForRentable(rent5.getRentableName(), 4, "Andrian");
                    FF.getRentableFacade().addRatingForRentable(rent5.getRentableName(), 5, "Alex");
                    FF.getRentableFacade().addRatingForRentable(rent5.getRentableName(), 2, "Anton");
                    FF.getRentableFacade().addRatingForRentable(rent5.getRentableName(), 5, "Kasper");
                    
                    FF.getRentableFacade().addRatingForRentable(rent6.getRentableName(), 5, "user");
                    FF.getRentableFacade().addRatingForRentable(rent6.getRentableName(), 4, "Andrian");
                    FF.getRentableFacade().addRatingForRentable(rent6.getRentableName(), 5, "Alex");
                    FF.getRentableFacade().addRatingForRentable(rent6.getRentableName(), 2, "Anton");
                    FF.getRentableFacade().addRatingForRentable(rent6.getRentableName(), 2, "Kasper");
                    
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
