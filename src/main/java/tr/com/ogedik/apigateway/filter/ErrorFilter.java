/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.apigateway.filter;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

/**
 * @author orkun.gedik
 */
public class ErrorFilter extends ZuulFilter {

  private static final Logger logger = LogManager.getLogger(ErrorFilter.class);

  @Override
  public String filterType() {
    return FilterConstants.ERROR_TYPE;
  }

  @Override
  public int filterOrder() {
    return 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() {
    HttpServletResponse response = RequestContext.getCurrentContext().getResponse();

    logger.info("{}: response status is {}.", response.getStatus());

    return null;
  }
}