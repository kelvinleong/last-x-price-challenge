package kl.challenge.consumer.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;

/**
 * Created by kelvinleung on 22/7/2018.
 */
@Component
public class HeaderInterceptor implements HandlerInterceptor {
    public static final String HEADER_X_INTERNAL_TRUSTED_USER_ID = "X-FDTAI-User-Id";
    public static final String HEADER_X_REQUEST_ID = "X-Request-Id";
    public static final String HEADER_LOCATION = "Location";
    public static final BigInteger ANONYMOUS_USER_ID = BigInteger.ZERO;
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String MAX_AGE = "max-age";

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

    public static void addCacheControlHeader(HttpServletResponse response, Long second){
        response.addHeader(HEADER_CACHE_CONTROL, MAX_AGE + "=" + second);
    }
}
