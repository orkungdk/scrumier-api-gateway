package tr.com.ogedik.apigateway.constants;

import lombok.experimental.UtilityClass;

/**
 * @author orkun.gedik
 */
@UtilityClass
public class ApiGatewayConstants {

  public static class Paths {
    public static final String API = "/api";
    public static final String AUTHENTICATE = "/authentication/authenticate";
    public static final String REGISTRATION = "/authentication/users";
    public static final String JIRA_CONNECTION_TEST = "/integration/jira/connect";
    public static final String JIRA_CONFIG = "/configuration/jira";
    public static final String SETUP = "/configuration/setup";
    public static final String WORKLOGS = "/time-tracker/tracker/worklogs";
  }
}
