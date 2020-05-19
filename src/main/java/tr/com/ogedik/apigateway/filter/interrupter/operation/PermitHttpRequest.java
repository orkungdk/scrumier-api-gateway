/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.apigateway.filter.interrupter.operation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import tr.com.ogedik.apigateway.filter.util.HttpFilter;

/**
 * @author orkun.gedik
 */
@Service
public class PermitHttpRequest implements ProxyOperation<Boolean> {

  @Override
  public Boolean execute(HttpServletRequest httpServletRequest) {
    return HttpFilter.getInstance().filter(httpServletRequest);
  }
}
