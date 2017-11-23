package rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.*;
import static rest.JSONConverter.getJSONfromObject;

@Path("imgUpload")
public class ImgUploadResource {

    private final ArrayList<String> TYPES = new ArrayList(Arrays.asList(".jpg", ".jpeg", ".png"));
    private final String s = File.separator;
    private String imagesFolder = "/var/lib/tomcat8/webapps/uploads/";
    private String host = "www.g3ralt.club";

    @Context
    ServletContext context;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(@DefaultValue("") @FormDataParam("file") InputStream file,
            @FormDataParam("file") FormDataContentDisposition fileDisposition,
            @FormDataParam("locationName") String locationName) {
        try {
            String contextPath = context.getRealPath("/index.html");
            int index = contextPath.indexOf("CA3_Server");
            imagesFolder = contextPath.substring(0, index).concat("uploads".concat(s));
            //Check if image
            String fileName = fileDisposition.getFileName();
            String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            if (!TYPES.contains(fileType)) {
                return Response.status(415).build(); //MediaType not supported
            }

            //Save the file
            String imgURL = saveFile(file, locationName, fileType);
            return Response.ok("{\"imgURL:\" \"" + imgURL + "\"}").build();

        } catch (Exception e) {
            return Response.status(503).entity(getJSONfromObject(e.getMessage())).build(); //Service unavailable
        }
    }

    @GET
    public Response get() throws IOException {
        return Response.status(200).entity("GET IS WORKING!").build();
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
