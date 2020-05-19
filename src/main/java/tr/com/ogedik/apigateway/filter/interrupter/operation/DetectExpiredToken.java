/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.apigateway.filter.interrupter.operation;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import tr.com.ogedik.apigateway.constants.ProxyConstants;
import tr.com.ogedik.apigateway.exception.TokenExpiredException;

/**
 * @author orkun.gedik
 */
@Service
public class DetectExpiredToken implements ProxyOperation<Void> {

  @Value("${jwt.secret}")
  private String secret;

  @Override
  public Void execute(HttpServletRequest request) {
    String requestTokenHeader = request.getHeader(ProxyConstants.Header.AUTH_TOKEN);

    if (isTokenExpired(requestTokenHeader)) {
      throw new TokenExpiredException("Token has been expired.");
    }
    return null;
  }

  /**
   * Check if the token has expired
   * 
   * @param requestTokenHeader token
   * @return {@code true} if token is still valid
   */
  private Boolean isTokenExpired(String requestTokenHeader) {
    if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
      return Boolean.FALSE;
    }
    // Token is in the form "Bearer token". Remove Bearer word and get only the Token
    final String token = requestTokenHeader.substring(7);
    final Date expiration = getExpirationDateFromToken(token);

    return expiration.before(new Date());
  }

  /**
   * Extracts the expiration date by given token
   * 
   * @param token parsed token
   * @return the expiration {@link Date}
   */
  private Date getExpirationDateFromToken(String token) {
    final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

    return claims.getExpiration();
  }
}
