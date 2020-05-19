/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.apigateway.exception;

import tr.com.ogedik.apigateway.exception.error.ProxyError;

/**
 * @author orkun.gedik
 */
public class ProxyException extends RuntimeException {

  public ProxyException(String msg) {
    super(msg);
  }

  public ProxyException(ProxyError message) {
    super(message.getMessage(), message.getThrowable());
  }
}
