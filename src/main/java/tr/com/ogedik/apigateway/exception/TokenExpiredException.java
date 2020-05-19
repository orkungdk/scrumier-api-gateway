/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.apigateway.exception;

/**
 * @author orkun.gedik
 */
public class TokenExpiredException extends ProxyException {

  public TokenExpiredException(String msg) {
    super(msg);
  }
}
