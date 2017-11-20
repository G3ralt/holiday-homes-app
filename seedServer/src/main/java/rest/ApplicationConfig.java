package rest;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(cors.CorsRequestFilter.class);
        resources.add(cors.CorsResponseFilter.class);
        resources.add(httpErrors.GenericExceptionMapper.class);
        resources.add(httpErrors.NotFoundExceptionMapper.class);
        resources.add(rest.Admin.class);
        resources.add(rest.All.class);
        resources.add(rest.Login.class);
        resources.add(rest.PlaceRest.class);
        resources.add(rest.Register.class);
        resources.add(rest.User.class);
        resources.add(security.JWTAuthenticationFilter.class);
        resources.add(security.NotAuthorizedExceptionMapper.class);
        resources.add(security.RolesAllowedFilter.class);
    }

}
