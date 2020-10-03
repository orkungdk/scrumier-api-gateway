package tr.com.ogedik.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import tr.com.ogedik.apigateway.filter.ErrorFilter;
import tr.com.ogedik.apigateway.filter.RequestFilter;
import tr.com.ogedik.apigateway.filter.ResponseFilter;
import tr.com.ogedik.apigateway.filter.RoutingFilter;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@EnableFeignClients("tr.com.ogedik.scrumier.proxy.clients")
public class ApiGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiGatewayApplication.class, args);
  }

}
