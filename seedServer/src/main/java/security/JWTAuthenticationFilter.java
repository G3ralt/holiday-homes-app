package security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import entity.User;
import facades.UserFacade;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.security.Principal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JWTAuthenticationFilter implements ContainerRequestFilter {

  private static final List<Class<? extends Annotation>> securityAnnotations = Arrays.asList(DenyAll.class, PermitAll.class, RolesAllowed.class);

  @Context
  private ResourceInfo resourceInfo;

  @Override
  public void filter(ContainerRequestContext request) throws IOException {

    if (isSecuredResource()) {
      String authorizationHeader = request.getHeaderString("Authorization");
      if (authorizationHeader == null) {
        throw new NotAuthorizedException("No authorization header provided", Response.Status.UNAUTHORIZED);
      }
      String token = request.getHeaderString("Authorization").substring("Bearer ".length());
      try {
        if (tokenIsExpired(token)) {
          throw new NotAuthorizedException("Your authorization token has timed out, please login again", Response.Status.UNAUTHORIZED);
        }

        String username = getUsernameFromToken(token);
        final UserPrincipal user = getPricipalByUserId(username);
        if (user == null) {
          throw new NotAuthorizedException("User could not be authenticated via the provided token", Response.Status.FORBIDDEN);
        }

        request.setSecurityContext(new SecurityContext() {

          @Override
          public boolean isUserInRole(String role) {
            return user.isUserInRole(role);
          }

          @Override
          public boolean isSecure() {
            return false;
          }

          @Override
          public Principal getUserPrincipal() {
            return user;
          }

          @Override
          public String getAuthenticationScheme() {
            return SecurityContext.BASIC_AUTH;
          }
        });

      } catch (ParseException | JOSEException e) {
        throw new NotAuthorizedException("You are not authorized to perform this action", Response.Status.FORBIDDEN);
      }
    }
  }

  private UserPrincipal getPricipalByUserId(String userId) {
    IUserFacade facade = UserFacadeFactory.getInstance();
    IUser user = facade.getUserByUserId(userId);
    if (user != null) {
      return new UserPrincipal(user.getUserName(), user.getRolesAsStrings());
    }
    return null;
  }

  private boolean isSecuredResource() {

    for (Class<? extends Annotation> securityClass : securityAnnotations) {
      if (resourceInfo.getResourceMethod().isAnnotationPresent(securityClass)) {
        return true;
      }
    }

    for (Class<? extends Annotation> securityClass : securityAnnotations) {
      if (resourceInfo.getResourceClass().isAnnotationPresent(securityClass)) {
        return true;
      }
    }

    return false;
  }

  private boolean tokenIsExpired(String token) throws ParseException, JOSEException {
    try {
      SignedJWT signedJWT = SignedJWT.parse(token);
      JWSVerifier verifier = new MACVerifier(Secret.SHARED_SECRET);

      if (signedJWT.verify(verifier)) {
        return new Date().getTime() > signedJWT.getJWTClaimsSet().getExpirationTime().getTime();
      }
    } catch(JOSEException | ParseException ex){
      throw ex;
    } catch(Exception ex){
      
      String message = "Token was not valid: (Did you Restart the server while a user was logged in))";
      Logger.getLogger(JWTAuthenticationFilter.class.getName()).log(Level.SEVERE, message,ex);
     throw new NotAuthorizedException("Your authorization token was not valid (try and login again)",Response.Status.UNAUTHORIZED);
    }
    return false;
  }

  private String getUsernameFromToken(String token) throws ParseException, JOSEException {

    SignedJWT signedJWT = SignedJWT.parse(token);
    JWSVerifier verifier = new MACVerifier(Secret.SHARED_SECRET);

    if (signedJWT.verify(verifier)) {
      return signedJWT.getJWTClaimsSet().getSubject();
    } else {
      throw new JOSEException("Firm is not verified.");
    }
  }
}
