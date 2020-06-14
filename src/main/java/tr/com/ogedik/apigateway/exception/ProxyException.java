/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.apigateway.exception;

import com.netflix.zuul.exception.ZuulException;
import tr.com.ogedik.apigateway.exception.error.ProxyError;

/**
 * @author orkun.gedik
 */
public class ProxyException extends ZuulException {

  public ProxyException(ProxyError error, String cause) {
    super(error.getMessage(), error.getStatus(), cause);
  }

  public ProxyException(ProxyError error) {
    super(error.getMessage(), error.getStatus(), "");
  }

  public ProxyException(ProxyError error, Throwable throwable) {
    super(throwable, error.getMessage(), error.getStatus(), "");
  }
}
