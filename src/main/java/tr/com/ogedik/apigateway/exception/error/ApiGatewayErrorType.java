/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.apigateway.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tr.com.ogedik.commons.expection.constants.ErrorType;

/**
 * @author orkun.gedik
 */
@Getter
@AllArgsConstructor
public enum ApiGatewayErrorType implements ErrorType {

  UNAUTHORIZED("Request is unauthorized!", 401),
  TOKEN_EXPIRED("Token has been expired.", 401),
  TOKEN_NOT_FOUND("Token is missing.", 401);

  private String title;
  private int status;

}
