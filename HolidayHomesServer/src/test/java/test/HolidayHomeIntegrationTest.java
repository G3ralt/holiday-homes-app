package test;

import org.junit.BeforeClass;
import io.restassured.*;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import static io.restassured.path.json.JsonPath.*;
import io.restassured.response.Response;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import junit.framework.Assert;
import org.apache.catalina.LifecycleException;
import static org.hamcrest.Matchers.*;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import test.utils.EmbeddedTomcat;

public class HolidayHomeIntegrationTest {

  private static final int SERVER_PORT = 9999;
  private static final String APP_CONTEXT = "/HolidayHomesServer";
  private static EmbeddedTomcat tomcat;

  public HolidayHomeIntegrationTest() {
  }
  private static String securityToken;

  //Utility method to login and set the securityToken
  private static void login(String username, String password) {
    String json = String.format("{username: \"%s\", password: \"%s\"}",username,password);
    System.out.println(json);
    String response = given()
            .contentType("application/json")
            .body(json)
            .when().post("/api/login").asString();
    
    System.out.println(response);
    System.out.println("Token: " + securityToken);

  }
 
  private void logOut(){
    securityToken = null;
  }

  @BeforeClass
  public static void setUpBeforeAll() throws ServletException, MalformedURLException, LifecycleException {
    tomcat = new EmbeddedTomcat();
    tomcat.start(SERVER_PORT, APP_CONTEXT);
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = SERVER_PORT;
    RestAssured.basePath = APP_CONTEXT;
    RestAssured.defaultParser = Parser.JSON;
  }

  @AfterClass
  public static void after() throws ServletException, MalformedURLException, LifecycleException, IOException {
    tomcat.stop();
  }

  @Test
  public void testRestGetAllPlaces() {
   
    Map<String, String> user = new HashMap<>();
    user.put("username", "unauthorized");
      Response response = 
            given()
            .contentType(ContentType.JSON)
            .body(user)
            .post("/api/places/all")
            .then()
            .contentType(ContentType.JSON).
            extract().response();
    
    String jsonAsString = response.asString();
    ArrayList<Map<String,?>> jsonAsArrayList = from(jsonAsString).get("");

    // now we count the number of entries in the JSON file, each entry is 1 ride
    boolean isEmpty = jsonAsArrayList.isEmpty();
    Assert.assertFalse(isEmpty);
            
  }

  
  @Test
  public void testRestGetAllRentables() {
   
    Map<String, String> user = new HashMap<>();
    user.put("username", "unauthorized");
      Response response = 
            given()
            .contentType(ContentType.JSON)
            .body(user)
            .post("/api/rentables/all")
            .then()
            .contentType(ContentType.JSON).
            extract().response();
    
    String jsonAsString = response.asString();
    ArrayList<Map<String,?>> jsonAsArrayList = from(jsonAsString).get("");

    // now we count the number of entries in the JSON file, each entry is 1 ride
    boolean isEmpty = jsonAsArrayList.isEmpty();
    Assert.assertFalse(isEmpty);
            
  }
  
  @Ignore
  @Test
  public void testRestAdminPageGetUserList() {
    
    Response response = given()
            .contentType("application/json")
            .when()
            .get("/api/demoadmin")
            .then()
            .contentType(ContentType.JSON).
            extract().response();
    
    String jsonAsString = response.asString();
    ArrayList<Map<String,?>> jsonAsArrayList = from(jsonAsString).get("");
    
    boolean isEmpty = jsonAsArrayList.isEmpty();
    Assert.assertFalse(isEmpty);
  }

  @Test
  public void testRestForUserBookings() {
    String username = "user";
    String password = "test";
    login(username,password);
    
    Response response = given()
            .pathParam("username", username)
            .contentType("application/json")
            .when()
            .get("/api/booking/allForUser/{username}").then()
            .contentType(ContentType.JSON).
            extract().response();
    
    String jsonAsString = response.asString();
    ArrayList<Map<String,?>> jsonAsArrayList = from(jsonAsString).get("");
    
    boolean isEmpty = jsonAsArrayList.isEmpty();
    Assert.assertFalse(isEmpty);
  }
  
  @Ignore
  @Test
  public void userNotAuthenticated() {
    logOut();
    String username = "user";
    Response response = given()
            .pathParam("username", username)
            .contentType("application/json")
            .when()
            .get("/api/booking/allForUser/{username}").then()
            .contentType(ContentType.JSON).
            extract().response();
    
    String jsonAsString = response.asString();
    ArrayList<Map<String,?>> jsonAsArrayList = from(jsonAsString).get("");
    
    boolean isEmpty = jsonAsArrayList.isEmpty();
    Assert.assertFalse(isEmpty);
  }
  
  @Test
  @Ignore
  public void adminNotAuthenticated() {
    logOut();
    given()
            .contentType("application/json")
            .when()
            .get("/api/demoadmin").then()
            .statusCode(401)
            .body("error.message", equalTo("No authorization header provided"));

  }

}
