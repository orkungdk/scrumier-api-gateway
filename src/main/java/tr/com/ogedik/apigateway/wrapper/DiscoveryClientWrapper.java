/**
 * © 2020 Copyright Amadeus Unauthorised use and disclosure strictly forbidden.
 */
package tr.com.ogedik.apigateway.wrapper;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author orkun.gedik
 */
@Service
public class DiscoveryClientWrapper {

  @Value("${zuul.routes.authentication.service-id}")
  private String authenticationServiceId;

  @Value("${zuul.routes.configuration.service-id}")
  private String configurationServiceId;

  @Qualifier("eurekaClient")
  @Autowired
  private EurekaClient discoveryClient;

  public InstanceInfo getAuthenticationServiceInstance() {
    return discoveryClient.getNextServerFromEureka(authenticationServiceId, false);
  }

  public InstanceInfo getConfigurationServiceInstance() {
    return discoveryClient.getNextServerFromEureka(configurationServiceId, false);
  }

}
