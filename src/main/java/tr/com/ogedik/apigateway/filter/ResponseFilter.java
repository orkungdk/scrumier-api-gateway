package tr.com.ogedik.apigateway.filter;

import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import tr.com.ogedik.apigateway.wrapper.ProxyFilterWrapper;

import javax.servlet.http.HttpServletResponse;

/**
 * @author orkun.gedik
 */
@Component
public class ResponseFilter extends ProxyFilterWrapper {

    @Override
    public void construct() {
        super.construct(ResponseFilter.class, FilterConstants.POST_TYPE, 1, true);
    }

    @Override
    public Object run() {
        HttpServletResponse response = RequestContext.getCurrentContext().getResponse();

        logger.info("{}: response status is {}.", response.getStatus());

        return null;
    }
}
