/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.apigateway.filter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.netflix.zuul.context.RequestContext;

import tr.com.ogedik.apigateway.wrapper.ProxyFilterWrapper;

/**
 * @author orkun.gedik
 */
public class ResponseFilter extends ProxyFilterWrapper {

  @Override
  public void construct() {
    super.construct(ResponseFilter.class, FilterConstants.POST_TYPE, 1, true);
  }

  @Override
  public Object run() {
    HttpServletResponse response = RequestContext.getCurrentContext().getResponse();

    logger.info("{}: response status is {}.", response.getStatus());

    return null;
  }
}
