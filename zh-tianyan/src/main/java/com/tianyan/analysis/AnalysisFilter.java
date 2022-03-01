package com.tianyan.analysis;

import com.tianyan.domain.RequestDomain;
import com.tianyan.util.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@WebFilter
@Order(10)
@Component
public class AnalysisFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    public static List<RequestDomain> requestDomainList = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (request.getAttribute("pass") == null || !(boolean) request.getAttribute("pass")) {
            filterChain.doFilter(request, servletResponse);
            return ;
        }
        Map<String, String> allHeaders = RequestUtils.getAllHeaders(request);
        String method = request.getMethod();
        Map<String, String[]> parameterMap = request.getParameterMap();
        String requestURI = request.getRequestURI();
        String remoteAddr = request.getRemoteAddr();
        Cookie[] cookies = request.getCookies();
        String protocol = request.getProtocol();
        RequestDomain domain = new RequestDomain();
        if (StringUtils.equalsIgnoreCase("POST", method)) {
            int contentLength = request.getContentLength();
            byte[] bytes = new byte[contentLength];
            try (ServletInputStream inputStream = request.getInputStream();){
                inputStream.read(bytes, 0, contentLength);
                domain.setBody(new String(bytes));
            }
        }
        domain.setHeaders(allHeaders);
        domain.setRemoteAddr(remoteAddr);
        domain.setRequestURI(requestURI);
        domain.setProtocol(protocol);
        domain.setCookies(cookies);
        domain.setMethod(method);
        domain.setParameterMap(parameterMap);
        requestDomainList.add(domain);
        filterChain.doFilter(request, servletResponse);
    }

    /*private Map<String, String> getAllHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            Map<String, String> headerMap = new HashMap<>();
            while (headerNames.hasMoreElements()) {
                String element = headerNames.nextElement();
                String header = request.getHeader(element);
                String existHeader = headerMap.get(element);
                if (StringUtils.isNotBlank(existHeader)) {
                    existHeader = existHeader + ";" + header;
                    headerMap.put(element, existHeader);
                } else {
                    headerMap.put(element, header);
                }
            }
            return headerMap;
        }
        return null;
    }*/

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
