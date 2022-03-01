package com.tianyan.controller;

import com.tianyan.analysis.GlobalHandleFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/globalhandle")
public class GlobalHandlerController {

    @RequestMapping("/url/add")
    public void setParamsUrl(@RequestParam String url) {
        if (!StringUtils.startsWith(url, "/")) {
            url = "/" + url;
        }
        GlobalHandleFilter.urlSets.add(url);
    }

    @RequestMapping("/url/remove")
    public void removeParamsUrl(String key) {
        GlobalHandleFilter.urlSets.remove(key );
    }

    @RequestMapping("/header/add")
    public void addParamsHeader(Map<String, List<String>> headerMap) {
        GlobalHandleFilter.headerMaps.putAll(headerMap);
    }

    @RequestMapping("/header/remove")
    public void removeParamsHeader(String key) {
        GlobalHandleFilter.headerMaps.remove(key);
    }

    @RequestMapping("/method/add")
    public void addParamsMethod(String method) {
        GlobalHandleFilter.methodSets.add(method);
    }

    @RequestMapping("/method/remove")
    public void removeParamsMethod(String method) {
        GlobalHandleFilter.methodSets.remove(method);
    }

}
