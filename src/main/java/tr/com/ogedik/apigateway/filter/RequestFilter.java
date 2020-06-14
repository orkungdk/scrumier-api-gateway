/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.apigateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.netflix.zuul.context.RequestContext;

import tr.com.ogedik.apigateway.constants.ProxyConstants;
import tr.com.ogedik.apigateway.exception.ProxyException;
import tr.com.ogedik.apigateway.exception.error.ProxyError;
import tr.com.ogedik.apigateway.filter.util.PermittedRequestFilter;
import tr.com.ogedik.apigateway.utils.HttpRequestHelper;
import tr.com.ogedik.apigateway.utils.TokenConsumer;
import tr.com.ogedik.apigateway.wrapper.DiscoveryClientWrapper;
import tr.com.ogedik.apigateway.wrapper.ProxyFilterWrapper;

import java.util.Collections;

/**
 * @author orkun.gedik
 */
@Component
public class RequestFilter extends ProxyFilterWrapper {

  @Autowired
  private TokenConsumer tokenConsumer;
  @Autowired
  private DiscoveryClientWrapper discoveryClientWrapper;

  @Override
  public void construct() {
    super.construct(RequestFilter.class, FilterConstants.PRE_TYPE, 1, true);
  }

  @Override
  public Object run() throws ProxyException {
    HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
    logger.info("Request has been interrupted by Pre-Request filter. The http method is {}, target is {}", filterType(),
        request.getMethod(), request.getRequestURL().toString());

    return PermittedRequestFilter.getInstance().filter(request) ? ignoreFilter(request) : executeFilter(request);
  }

  private Object ignoreFilter(HttpServletRequest request) {
    logger.info("Request filtering is ignored. Authentication check will not be performed. Url:{}, Method:{}",
        request.getRequestURL(), request.getMethod());

    return null;
  }

  private Object executeFilter(HttpServletRequest request) throws ProxyException {
    String token = extractToken(request);
    doFilterRequest(token);
    authenticateRequest(request);
    return null;
  }

  private void authenticateRequest(HttpServletRequest request) throws ProxyException {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    httpHeaders.add(ProxyConstants.Header.REQUEST_SOURCE, "Api-Gateway");
    httpHeaders.add(ProxyConstants.Header.AUTH_TOKEN, request.getHeader(ProxyConstants.Header.AUTH_TOKEN));
    httpHeaders.add(ProxyConstants.Header.AUTH_USER,
        RequestContext.getCurrentContext().getZuulRequestHeaders().get(ProxyConstants.Header.AUTH_USER.toLowerCase()));

    HttpStatus authenticationStatus = HttpRequestHelper.get(httpHeaders,
        discoveryClientWrapper.getAuthenticationServiceInstance().getHomePageUrl(), ProxyConstants.Paths.AUTHENTICATE);

    if (authenticationStatus.equals(HttpStatus.UNAUTHORIZED)) {
      throw new ProxyException(ProxyError.UNAUTHORIZED);
    }
  }

  private void doFilterRequest(String token) throws ProxyException {
    Boolean isTokenExpired = tokenConsumer.isTokenExpired(token);

    if (BooleanUtils.isFalse(isTokenExpired)) {
      String username = tokenConsumer.getUsernameFromToken(token);
      RequestContext.getCurrentContext().addZuulRequestHeader(ProxyConstants.Header.AUTH_USER, username);
    } else {
      throw new ProxyException(ProxyError.TOKEN_EXPIRED);
    }
  }

  private String extractToken(HttpServletRequest request) throws ProxyException {
    String requestTokenHeader = request.getHeader(ProxyConstants.Header.AUTH_TOKEN);

    if (isTokenMissing(requestTokenHeader)) {
      throw new ProxyException(ProxyError.TOKEN_NOT_FOUND);
    }
    String token = requestTokenHeader.substring(7);

    return token;
  }

  private boolean isTokenMissing(String requestTokenHeader) {
    return StringUtils.isEmpty(requestTokenHeader)
        || !StringUtils.startsWithIgnoreCase(requestTokenHeader, ProxyConstants.Header.PREFIX);
  }
}
