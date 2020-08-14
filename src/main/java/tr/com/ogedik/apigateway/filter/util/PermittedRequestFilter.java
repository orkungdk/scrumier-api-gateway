package tr.com.ogedik.apigateway.filter.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import tr.com.ogedik.apigateway.constants.ProxyConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author orkun.gedik
 */
public class PermittedRequestFilter {

    private static PermittedRequestFilter instance;
    private List<Matcher> matchers;

    private PermittedRequestFilter() {
        matchers = new ArrayList<>();
        matchers.add(new Matcher(HttpMethod.GET, ProxyConstants.Paths.API + ProxyConstants.Paths.AUTHENTICATE));
        matchers.add(new Matcher(HttpMethod.GET, ProxyConstants.Paths.API + ProxyConstants.Paths.JIRA_CONFIG));
        matchers.add(new Matcher(HttpMethod.POST, ProxyConstants.Paths.API + ProxyConstants.Paths.AUTHENTICATE));
        matchers.add(new Matcher(HttpMethod.POST, ProxyConstants.Paths.API + ProxyConstants.Paths.REGISTRATION));
        matchers.add(new Matcher(HttpMethod.POST, ProxyConstants.Paths.API + ProxyConstants.Paths.JIRA_CONNECTION_TEST));
        matchers.add(new Matcher(HttpMethod.POST, ProxyConstants.Paths.API + ProxyConstants.Paths.SETUP));
    }

    public static PermittedRequestFilter getInstance() {
        if (instance == null) {
            instance = new PermittedRequestFilter();
        }

        return instance;
    }

    public boolean isAuthenticationRequired(HttpServletRequest httpServletRequest) {
        return !matchers.stream()
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
