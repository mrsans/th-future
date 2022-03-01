package com.tianyan.test;

import com.tianyan.analysis.AnalysisFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/test")
@RestController
public class TestContoller {

    @RequestMapping("/get")
    public Map<String, Object> test() {
        Map<String, Object> m = new HashMap<>();
        m.put("a", AnalysisFilter.requestDomainList);
        return m;
    }



}
