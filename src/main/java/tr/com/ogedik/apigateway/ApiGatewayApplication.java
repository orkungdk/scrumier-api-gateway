package tr.com.ogedik.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import tr.com.ogedik.apigateway.filter.ErrorFilter;
import tr.com.ogedik.apigateway.filter.RequestFilter;
import tr.com.ogedik.apigateway.filter.ResponseFilter;
import tr.com.ogedik.apigateway.filter.RoutingFilter;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ApiGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiGatewayApplication.class, args);
  }

  public RequestFilter requestFilter() {
    return new RequestFilter();
  }

  @Bean
  public ResponseFilter responseFilter() {
    return new ResponseFilter();
  }

  @Bean
  public ErrorFilter errorFilter() {
    return new ErrorFilter();
  }

  @Bean
  public RoutingFilter routingFilter() {
    return new RoutingFilter();
  }

}
