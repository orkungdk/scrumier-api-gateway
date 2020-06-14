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
    public static final String PREFIX = "Bearer ";
    public static final String AUTH_TOKEN = "X-Auth-Token";
    public static final String AUTHORITIES = "X-Authorities";
    public static final String AUTH_USER = "X-Auth-User";
    public static final String REQUEST_SOURCE = "X-Request-Source";
  }

  public static class Paths {
    public static final String API = "/api";
    public static final String AUTHENTICATE = "/authentication/authenticate";
    public static final String REGISTRATION = "/authentication/users";
    public static final String JIRA_CONNECTION_TEST = "/configuration/testJiraConnection";
    public static final String SETUP = "/configuration/setup";

  }

  public static class Fields {

    public static final String ACTIVE = "isActive";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String AUTHORITIES = "permissions";
  }
}
