/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.apigateway.exception.error;

import lombok.Getter;
import lombok.Setter;

/**
 * @author orkun.gedik
 */
@Getter
@Setter
public class ProxyError {

  private String message;

  private Throwable throwable;
}
