/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.apigateway.filter.interrupter.operation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

/**
 * @author orkun.gedik
 */
@Service
public class RequestHeaderValidator implements ProxyOperation<Void> {

  @Override
  public Void execute(HttpServletRequest httpServletRequest) {
    return null;
  }
}
