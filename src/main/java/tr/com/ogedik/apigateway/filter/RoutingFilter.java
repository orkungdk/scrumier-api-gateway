/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.apigateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.netflix.zuul.context.RequestContext;

import tr.com.ogedik.apigateway.wrapper.ProxyFilterWrapper;

/**
 * @author orkun.gedik
 */
public class RoutingFilter extends ProxyFilterWrapper {

  @Override
  public void construct() {
    super.construct(RoutingFilter.class, FilterConstants.ROUTE_TYPE, 1, true);
  }

  @Override
  public Object run() {
    HttpServletRequest request = RequestContext.getCurrentContext().getRequest();

    logger.info("{}: {} request to {}", filterType(), request.getMethod(), request.getRequestURL().toString());

    return null;
  }
}