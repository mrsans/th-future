package com.tianyan.domain;

import lombok.Data;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Data
public class RequestDomain {

//    private HttpServletRequest request;

    private String remoteAddr;

    private String protocol;

    private String method;

    private Map<String, String> headers;

    private String requestURI;

    private Cookie[] cookies;

    private Map<String, String[]> parameterMap;

    private String body;
}
