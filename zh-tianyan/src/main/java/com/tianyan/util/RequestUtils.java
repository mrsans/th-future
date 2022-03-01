package com.tianyan.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestUtils {

    public static Map<String, String> getAllHeaders(HttpServletRequest request) {
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
    }


}
