package tr.com.ogedik.apigateway.filter;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import tr.com.ogedik.apigateway.exception.ApiGatewayException;
import tr.com.ogedik.apigateway.exception.error.ApiGatewayErrorType;
import tr.com.ogedik.apigateway.filter.util.PermittedRequestFilter;
import tr.com.ogedik.apigateway.wrapper.ProxyFilterWrapper;
import tr.com.ogedik.commons.constants.Services;
import tr.com.ogedik.commons.constants.Headers;
import tr.com.ogedik.commons.rest.request.client.HttpRestClient;
import tr.com.ogedik.commons.rest.request.client.helper.RequestURLDetails;

import javax.servlet.http.HttpServletRequest;

@Component
public class RequestFilter extends ProxyFilterWrapper {

    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient eurekaClient;

    @Override
    public void construct() {
        super.construct(RequestFilter.class, FilterConstants.PRE_TYPE, 1, true);
    }

    @Override
    public Object run() throws ApiGatewayException {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        logger.info("Request has been interrupted by Pre-Request filter. The http method is {}, target is {}",
                request.getRequestURL());

        if (PermittedRequestFilter.getInstance().isAuthenticationRequired(request)) {
            interrupt(request);
        }

        return null;
    }

    private void interrupt(HttpServletRequest request) throws ApiGatewayException {
        String token = extractToken(request);
        String username = getUsername(token);

        RequestContext.getCurrentContext().addZuulRequestHeader(Headers.AUTH_USER, username);
    }

    private String getUsername(String token) {
        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka(Services.AUTHENTICATION, false);
        RequestURLDetails requestURLDetails = new RequestURLDetails(instanceInfo.getHomePageUrl(),
                Services.Path.VALIDATE, null);

        return HttpRestClient.doPost(requestURLDetails, token, String.class).getBody();
    }

    private String extractToken(HttpServletRequest request) throws ApiGatewayException {
        String requestTokenHeader = request.getHeader(Headers.AUTH_TOKEN);
        if (isTokenMissing(requestTokenHeader)) {
            throw new ApiGatewayException(ApiGatewayErrorType.TOKEN_NOT_FOUND);
        }

        return requestTokenHeader.substring(7);
    }

    private boolean isTokenMissing(String requestTokenHeader) {
        return StringUtils.isEmpty(requestTokenHeader)
                || !StringUtils.startsWithIgnoreCase(requestTokenHeader, Headers.PREFIX);
    }
}
