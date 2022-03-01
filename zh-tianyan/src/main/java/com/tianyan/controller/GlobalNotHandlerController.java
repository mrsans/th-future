package com.tianyan.controller;

import com.tianyan.analysis.GlobalNotHandleFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/globalNothandle")
public class GlobalNotHandlerController {

    @RequestMapping("/url/add")
    public void setParamsUrl(@RequestParam String url) {
        if (!StringUtils.startsWith(url, "/")) {
            url = "/" + url;
        }
        GlobalNotHandleFilter.urlSets.add(url);
    }

    @RequestMapping("/url/remove")
    public void removeParamsUrl(String key) {
        GlobalNotHandleFilter.urlSets.remove(key );
    }

    @RequestMapping("/header/add")
    public void addParamsHeader(Map<String, List<String>> headerMap) {
        GlobalNotHandleFilter.headerMaps.putAll(headerMap);
    }

    @RequestMapping("/header/remove")
    public void removeParamsHeader(String key) {
        GlobalNotHandleFilter.headerMaps.remove(key);
    }

    @RequestMapping("/method/add")
    public void addParamsMethod(String method) {
        GlobalNotHandleFilter.methodSets.add(method);
    }

    @RequestMapping("/method/remove")
    public void removeParamsMethod(String method) {
        GlobalNotHandleFilter.methodSets.remove(method);
    }




}
