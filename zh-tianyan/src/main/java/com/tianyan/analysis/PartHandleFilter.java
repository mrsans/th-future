package com.tianyan.analysis;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

@WebFilter
@Component
@Order(3)
public class PartHandleFilter implements Filter {

    public static Set<String> urlSets = Collections.synchronizedSet(new HashSet<>());

    public static Map<String, List<String>> headerMaps = new ConcurrentHashMap<>();

    public static Set<String> methodSets = Collections.synchronizedSet(new HashSet<>());

    private static Map<Integer, Predicate<Object>> orderFunction = new HashMap<>();

    private List<Integer> orderList = Collections.synchronizedList(new ArrayList<>());

    static {
        orderFunction.put(1, (a) -> {
         return false;
        });
        orderFunction.put(2, (a) -> {
           return false;
        });
        orderFunction.put(3, (a) -> {
            return false;
        });
        orderFunction.put(4, (a) -> {
            return false;
        });
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;



        String method = request.getMethod();
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
