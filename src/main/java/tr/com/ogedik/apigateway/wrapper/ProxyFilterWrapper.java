package tr.com.ogedik.apigateway.wrapper;

import com.netflix.zuul.ZuulFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tr.com.ogedik.apigateway.filter.RequestFilter;

import javax.annotation.PostConstruct;

/**
 * @author orkun.gedik
 */
public abstract class ProxyFilterWrapper extends ZuulFilter {

  public static Logger logger;

  private String filterType;
  private int filterOrder;
  private boolean shouldFilter;

  @PostConstruct
  public abstract void construct();

  public void construct(Class<?> clazz, String filterType, int filterOrder, boolean shouldFilter) {
    this.logger = LogManager.getLogger(clazz);
    this.filterType = filterType;
    this.filterOrder = filterOrder;
    this.shouldFilter = shouldFilter;
  }

  @Override
  public String filterType() {
    return filterType;
  }

  @Override
  public int filterOrder() {
    return filterOrder;
  }

  @Override
  public boolean shouldFilter() {
    return shouldFilter;
  }
}
