package rest;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.glassfish.jersey.media.multipart.*;
import static rest.JSONConverter.getJSONfromObject;

@Path("imgUpload")
public class ImgUploadResource {

    private final ArrayList<String> TYPES = new ArrayList(Arrays.asList(".jpg", ".jpeg", ".png"));
    private final String s = File.separator;
    private String imagesFolder = "/var/lib/tomcat8/webapps/uploads/"; //This is the expected location for the images
    private String host = "www.g3ralt.club"; //Write here the droplet domain

    @Context
    ServletContext context;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(@DefaultValue("") @FormDataParam("file") InputStream file,
            @FormDataParam("file") FormDataContentDisposition fileDisposition,
            @FormDataParam("locationName") String locationName) {
        
        try {
            //Get the location dynamically
            String contextPath = context.getRealPath("/index.html");
            int index = contextPath.indexOf("HolidayHomesServer");
            imagesFolder = contextPath.substring(0, index).concat("uploads".concat(s));
            
            //Check if image
            String fileName = fileDisposition.getFileName();
            String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            if (!TYPES.contains(fileType)) {
                return Response.status(415).build(); //MediaType not supported
            }

            //Save the file
            //String imgURL = saveFile(file, locationName, fileType);
            //return Response.ok("{\"imgURL\": \"" + imgURL + "\"}").build();
            return Response.ok("{\"imgURL\": \"https://wyncode.co/wp-content/uploads/2014/08/31.jpg\"}").build(); //DELETE THIS and uncomment 52,53
        } catch (Exception e) {
            return Response.status(503).entity(getJSONfromObject(e.getMessage())).build(); //Service unavailable
        }
    }

    private String saveFile(InputStream is, String fileName, String fileType) throws IOException {
        String location = imagesFolder + fileName + fileType;
        try (OutputStream os = new FileOutputStream(new File(location))) {
            byte[] buffer = new byte[256];
            int bytes = 0;
            while ((bytes = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytes);
            }
        }
        return host.concat("/uploads/").concat(fileName).concat(fileType);
    }
    
}
