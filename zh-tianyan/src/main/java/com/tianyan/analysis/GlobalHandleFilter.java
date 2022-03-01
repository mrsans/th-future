package com.tianyan.analysis;

import cn.hutool.core.map.MapUtil;
import com.tianyan.util.RequestUtils;
import org.apache.commons.collections4.CollectionUtils;
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

@WebFilter
@Component
@Order(1)
public class GlobalHandleFilter implements Filter {

//    @Value("${path:param}")
    private String path = "/param";

    public static Set<String> urlSets = Collections.synchronizedSet(new HashSet<>());

    public static Map<String, List<String>> headerMaps = new ConcurrentHashMap<>();

    public static Set<String> methodSets = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String requestURI = request.getRequestURI();
        if (StringUtils.startsWith(requestURI, path)) {
            filterChain.doFilter(request, servletResponse);
            return ;
        }

        // 过滤url
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String url : urlSets) {
            if (antPathMatcher.match(url, requestURI)) {
                request.setAttribute("pass", true);
            }
        }

        // 过滤header
        for (Map.Entry<String, List<String>> headerMap : headerMaps.entrySet()) {
            Map<String, String> allHeaders = RequestUtils.getAllHeaders(request);
            if (MapUtil.isNotEmpty(allHeaders)) {
                String requestHeaderValue = allHeaders.get(headerMap.getKey());
                List<String> headerMapValueList = headerMap.getValue();
                if (CollectionUtils.isNotEmpty(headerMapValueList)) {
                    for (String headerMapValue : headerMapValueList) {
                        if (StringUtils.containsIgnoreCase(requestHeaderValue, headerMapValue)) {
                            request.setAttribute("pass", true);
                        }
                    }
                }

            }
        }

        // 过滤params和body

        // 过滤method
        String method = request.getMethod();

        for (String tempMethod : methodSets) {
            if (StringUtils.equalsIgnoreCase(tempMethod, method)) {
                request.setAttribute("pass", true);
            }
        }
        filterChain.doFilter(request, servletResponse);
    }




    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
