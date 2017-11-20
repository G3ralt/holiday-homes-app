package httpErrors;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

  private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

  @Context
  ServletContext context;

  @Override
  public Response toResponse(Exception ex) {
    JsonObject error = new JsonObject();
    JsonObject errorDetail = new JsonObject();
    int statusCode = 500;
    ex.printStackTrace();
    errorDetail.addProperty("code", statusCode);
    errorDetail.addProperty("message", "An unexpected problem occured on the server."+ex.getMessage());
    error.add("error", errorDetail);
    return Response.status(statusCode).entity(gson.toJson(error)).type(MediaType.APPLICATION_JSON).build();
  }
}
