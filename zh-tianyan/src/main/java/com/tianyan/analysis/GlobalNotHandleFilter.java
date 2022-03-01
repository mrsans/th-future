package com.tianyan.analysis;

import com.tianyan.util.RequestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@WebFilter()
@Order(2)
@Component
public class GlobalNotHandleFilter implements Filter {

    public static Set<String> urlSets = Collections.synchronizedSet(new HashSet<>());

    public static Map<String, List<String>> headerMaps = new ConcurrentHashMap<>();

    public static Set<String> methodSets = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        // pass url
        String requestURI = request.getRequestURI();
        for (String urlSet : urlSets) {
            if (!antPathMatcher.match(urlSet, requestURI)) {
                 request.setAttribute("pass", true);
            }
        }

        // pass params and body

        // pass method
        String method = request.getMethod();
        for (String methodSet : methodSets) {
            if (!StringUtils.equalsIgnoreCase(method, methodSet)) {
                request.setAttribute("pass", true);
            }
        }

        // pass headers
        Map<String, String> allHeaders = RequestUtils.getAllHeaders(request);
        if (MapUtils.isNotEmpty(allHeaders)) {
            for (Map.Entry<String, List<String>> header : headerMaps.entrySet()) {
                List<String> valueList = header.getValue();
                if (CollectionUtils.isNotEmpty(valueList)) {
                    for (String value : valueList) {
                        String headerValue = request.getHeader(header.getKey());
                        if (!StringUtils.containsIgnoreCase(headerValue, value)) {
                            request.setAttribute("pass", true);
                        }
                    }
                }
            }
        }
        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
