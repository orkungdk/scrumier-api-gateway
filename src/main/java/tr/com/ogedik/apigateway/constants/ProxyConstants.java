/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.apigateway.constants;

import lombok.experimental.UtilityClass;

/**
 * @author orkun.gedik
 */
@UtilityClass
public class ProxyConstants {

  public static class Exception {
    public static final String UNABLE_GET_TOKEN = "Unable to get token.";
    public static final String UNAUTHORIZED = "Unauthorized request.";
    public static final String USER_NOT_FOUND = "User record cannot be found.";
    public static final String MISSING_AUTHORIZATION_TOKEN = "Authentication Token is missing in the request header.";
    public static final String USER_IS_NOT_ACTIVE = "User is not currently active.";
  }

  public static class Header {
    public static final String AUTH_TOKEN = "Auth-Token";
    public static final String AUTHORITIES = "Authorities";
  }

  public static class Paths {
    public static final String AUTHENTICATE = "/api/authentication/authenticate";
    public static final String REGISTRATION = "/api/authentication/users";
  }

  public static class Fields {

    public static final String ACTIVE = "isActive";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String AUTHORITIES = "permissions";
  }
}
