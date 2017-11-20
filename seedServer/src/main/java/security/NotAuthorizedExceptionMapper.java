package security;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javax.servlet.ServletContext;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotAuthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {

  private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

  @Context
  ServletContext context;

  @Override
  public Response toResponse(NotAuthorizedException ex) {

    JsonObject error = new JsonObject();
    JsonObject errorDetail = new JsonObject();
    int statusCode = ex.getResponse().getStatus();
    try{
      statusCode = ((Response.Status)ex.getChallenges().get(0)).getStatusCode();
     }
    catch(Exception e){}
    errorDetail.addProperty("code", statusCode);
    errorDetail.addProperty("message", ex.getMessage());
    error.add("error", errorDetail);
    return Response.status(statusCode).entity(gson.toJson(error)).type(MediaType.APPLICATION_JSON).build();
  }
}
