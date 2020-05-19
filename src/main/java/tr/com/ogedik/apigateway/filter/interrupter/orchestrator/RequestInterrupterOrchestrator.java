/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.apigateway.filter.interrupter.orchestrator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.ogedik.apigateway.filter.interrupter.operation.DetectExpiredToken;
import tr.com.ogedik.apigateway.filter.interrupter.operation.PermitHttpRequest;
import tr.com.ogedik.apigateway.filter.interrupter.operation.RequestHeaderValidator;
import tr.com.ogedik.apigateway.filter.interrupter.operation.SignatureProxyHeader;

/**
 * @author orkun.gedik
 */
@Service
public class RequestInterrupterOrchestrator extends ProxyOrchestrator {

  @Autowired
  private PermitHttpRequest permitHttpRequest;
  @Autowired
  private DetectExpiredToken detectExpiredToken;
  @Autowired
  private RequestHeaderValidator requestHeaderValidator;
  @Autowired
  private SignatureProxyHeader signatureProxyHeader;

  public void interruptOnRequestReceived(HttpServletRequest request) {
    if (permitHttpRequest.execute(request)) {
      return;
    }
    detectExpiredToken.execute(request);
    requestHeaderValidator.execute(request);
    signatureProxyHeader.execute(request);
  }

}
