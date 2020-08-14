package tr.com.ogedik.apigateway.filter;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import tr.com.ogedik.apigateway.wrapper.ProxyFilterWrapper;

/**
 * @author orkun.gedik
 */
public class ErrorFilter extends ProxyFilterWrapper {

    @Override
    public void construct() {
        super.construct(ErrorFilter.class, FilterConstants.ERROR_TYPE, 1, true);
    }

    @Override
    public Object run() {
        HttpServletResponse response = RequestContext.getCurrentContext().getResponse();

        logger.info("{}: response status is {}.", response.getStatus());

        return null;
    }
}