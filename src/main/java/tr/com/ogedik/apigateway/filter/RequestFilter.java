/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.apigateway.filter;

import javax.servlet.http.HttpServletRequest;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.netflix.zuul.context.RequestContext;

import tr.com.ogedik.apigateway.constants.ProxyConstants;
import tr.com.ogedik.apigateway.exception.ApiGatewayException;
import tr.com.ogedik.apigateway.exception.error.ApiGatewayErrorType;
import tr.com.ogedik.apigateway.filter.util.PermittedRequestFilter;
import tr.com.ogedik.apigateway.utils.HttpRequestHelper;
import tr.com.ogedik.apigateway.wrapper.DiscoveryClientWrapper;
import tr.com.ogedik.apigateway.wrapper.ProxyFilterWrapper;
import tr.com.ogedik.commons.constants.Services;
import tr.com.ogedik.commons.helper.TokenHelper;
import tr.com.ogedik.commons.model.Headers;
import tr.com.ogedik.commons.request.rest.HttpRestClient;
import tr.com.ogedik.commons.request.rest.helper.RequestURLDetails;

import java.util.Collections;

/**
 * @author orkun.gedik
 */
@Component
public class RequestFilter extends ProxyFilterWrapper {

  @Qualifier("eurekaClient")
  @Autowired
  private EurekaClient eurekaClient;

  @Override
  public void construct() {
    super.construct(RequestFilter.class, FilterConstants.PRE_TYPE, 1, true);
  }

  @Override
  public Object run() throws ApiGatewayException {
    HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
    logger.info("Request has been interrupted by Pre-Request filter. The http method is {}, target is {}", filterType(),
        request.getMethod(), request.getRequestURL());

    return PermittedRequestFilter.getInstance().filter(request) ? ignoreFilter(request) : executeFilter(request);
  }

  private Object ignoreFilter(HttpServletRequest request) {
    logger.info("Request filtering is ignored. Authentication check will not be performed. Url:{}, Method:{}",
        request.getRequestURL(), request.getMethod());

    return null;
  }

  private Object executeFilter(HttpServletRequest request) throws ApiGatewayException {
    String token = extractToken(request);
    doFilterRequest(token);
    authenticateRequest(request);
    return null;
  }

  private void authenticateRequest(HttpServletRequest request) throws ApiGatewayException {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    httpHeaders.add(Headers.REQUEST_SOURCE, "Api-Gateway");
    httpHeaders.add(Headers.AUTH_TOKEN, request.getHeader(Headers.AUTH_TOKEN));
    httpHeaders.add(Headers.AUTH_USER,
        RequestContext.getCurrentContext().getZuulRequestHeaders().get(Headers.AUTH_USER.toLowerCase()));

    InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka(Services.AUTHENTICATION, false);
    RequestURLDetails requestURLDetails = new RequestURLDetails(instanceInfo.getHomePageUrl(),
            instanceInfo.getVIPAddress(), ProxyConstants.Paths.AUTHENTICATE, null);

    HttpRestClient.doGet(requestURLDetails, httpHeaders, null);

//    if (authenticationStatus.equals(HttpStatus.UNAUTHORIZED)) {
//      throw new ApiGatewayException(ApiGatewayErrorType.UNAUTHORIZED);
//    }
  }

  private void doFilterRequest(String token) throws ApiGatewayException {
    Boolean isTokenExpired = TokenHelper.isTokenExpired(token);

    if (BooleanUtils.isFalse(isTokenExpired)) {
      String username = TokenHelper.getUsernameFromToken(token);
      RequestContext.getCurrentContext().addZuulRequestHeader(Headers.AUTH_USER, username);
    } else {
      throw new ApiGatewayException(ApiGatewayErrorType.TOKEN_EXPIRED);
    }
  }

  private String extractToken(HttpServletRequest request) throws ApiGatewayException {
    String requestTokenHeader = request.getHeader(Headers.AUTH_TOKEN);

    if (isTokenMissing(requestTokenHeader)) {
      throw new ApiGatewayException(ApiGatewayErrorType.TOKEN_NOT_FOUND);
    }
    String token = requestTokenHeader.substring(7);

    return token;
  }

  private boolean isTokenMissing(String requestTokenHeader) {
    return StringUtils.isEmpty(requestTokenHeader)
        || !StringUtils.startsWithIgnoreCase(requestTokenHeader, Headers.PREFIX);
  }
}
