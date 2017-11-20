package test.utils;



import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;
import javax.servlet.ServletException;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class EmbeddedTomcat {

  private final static String TMP_DIR = System.getProperty("java.io.tmpdir")+"/EmbeddedTomcat";
  //private static final int PORT = 9999;
  
  Tomcat tomcat;


  public void start(int port,String appContext) throws ServletException,
          MalformedURLException,
          LifecycleException {
    // Define a folder to hold web application contents.Not used by app, but is required by Tomcat. This directory MUST exist
    String webAppLocation = "WebContent/";
    tomcat = new Tomcat();

    new File("WebContent").mkdir();

    // new java.io.File("tmp").mkdir();
    tomcat.setBaseDir(TMP_DIR);
    tomcat.setPort(port);

    // Define a web application context.
    Context context = tomcat.addWebapp(appContext, new File(webAppLocation).getAbsolutePath());
    // Add servlet that will register Jersey REST resources
    Tomcat.addServlet(context, "jersey-container-servlet", resourceConfig());
    context.addServletMapping("/api/*", "jersey-container-servlet");
    tomcat.start();
    Server server = tomcat.getServer();
    System.out.println("Address: "+server.getAddress());
    for(Service s : server.findServices()){
      System.out.println("Service: "+s.getDomain()+" -- "+s.getName());
    }
    //Don't comment the line below in unless you know what you do. It will block here, waiting for the server to stop. 
    //This can be usefull for testing the embedded server from a browser or postman
    //tomcat.getServer().await();
  }

  private ServletContainer resourceConfig() {
    //Load the Wizard-generated rest.ApplicationConfig - class
    Set<Class<?>> resources = new rest.ApplicationConfig().getClasses();
    System.out.println("Loaded Classes Count: "+resources.size());
    return new ServletContainer(new ResourceConfig(resources));
    //return new ServletContainer(new ResourceConfig(new rest.ApplicationConfig().getClasses()));
  }

  public void stop() throws IOException {
    try {
      tomcat.stop();
      tomcat.destroy();
      //Delete our embedded Tomcat's temp  + WebContent folder
      FileUtils.deleteDirectory(new File("TMP_DIR"));
      FileUtils.deleteDirectory(new File("WebContent"));
    } catch (LifecycleException e) {
      throw new RuntimeException(e);

    }
  }
}