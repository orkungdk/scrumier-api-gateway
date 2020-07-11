/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.apigateway.utils;

import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author orkun.gedik
 */
@UtilityClass
public class HttpRequestHelper {

  private static final Logger logger = LogManager.getLogger(HttpRequestHelper.class);

  public static HttpStatus get(HttpHeaders httpHeaders, String homeUrl, String path) {
    RestTemplate restTemplate = new RestTemplate();
    HttpEntity request = new HttpEntity(httpHeaders);

    String url = homeUrl + path;
    logger.info("GET request is sending to {}...", url);
    // make an HTTP GET request with headers
    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class, 1);
    return response.getStatusCode();
  }
}
