/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.apigateway.filter.interrupter.operation;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface for classes that implements the a single operation of the functional workflow. Any operation represent a
 * single step in the functional workflow. All operations are called by an <Code>ProxyOrchestrator</Code>
 *
 * @author orkun.gedik
 */
public interface ProxyOperation<T> {

  T execute(HttpServletRequest httpServletRequest);
}
