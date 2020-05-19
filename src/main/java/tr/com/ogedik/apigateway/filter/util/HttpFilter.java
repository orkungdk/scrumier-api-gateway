/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.apigateway.filter.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import tr.com.ogedik.apigateway.constants.ProxyConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author orkun.gedik
 */
public class HttpFilter {

  private static HttpFilter instance;
  private List<Matcher> matchers;

  private HttpFilter() {
    matchers = new ArrayList<>();
    matchers.add(new Matcher(HttpMethod.POST, ProxyConstants.Paths.AUTHENTICATE));
    matchers.add(new Matcher(HttpMethod.POST, ProxyConstants.Paths.REGISTRATION));
  }

  public static HttpFilter getInstance() {
    if (instance == null) {
      instance = new HttpFilter();
    }

    return instance;
  }

  public boolean filter(HttpServletRequest httpServletRequest) {
    return matchers.stream()
        .filter(matcher -> httpServletRequest.getRequestURL().toString().contains(matcher.requestPath))
        .filter(matcher -> httpServletRequest.getMethod().equals(matcher.httpMethod.name()))
        .findAny()
        .isPresent();
  }

  @Getter
  @Setter
  @AllArgsConstructor
  class Matcher {
    private HttpMethod httpMethod;
    private String requestPath;
  }
}
