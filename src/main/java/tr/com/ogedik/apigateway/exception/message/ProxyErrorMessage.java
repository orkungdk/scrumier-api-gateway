/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.apigateway.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author orkun.gedik
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProxyErrorMessage {

  private String message;

  private String details;

  private String cause;

  public ProxyErrorMessage(String message) {
    this.message = message;
  }

  public String getDetails() {
    return "message: " + message + ".\n" + "details: " + details + ".\n" + "cause: " + cause + ".";
  }
}
