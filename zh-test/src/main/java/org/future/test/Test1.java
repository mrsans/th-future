package org.future.test;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Test1 {

    private static Logger logger = LogManager.getLogger(Test1.class);

    public static void main(String[] args) throws IOException {
        List<String> result = new ArrayList<>();
        FileInputStream fileInputStream = new FileInputStream(new File("H:\\Java\\th-future\\b.json"));
        List<String> strings = IOUtils.readLines(fileInputStream);
        for (String string : strings) {
            JSONObject jsonObject = JSONUtil.toBean(string, JSONObject.class);
            String id = jsonObject.getStr("id");
            String substring0 = StringUtils.substring(id, 0,8);
            String substring1 = StringUtils.substring(id, 8,12);
            String substring2 = StringUtils.substring(id, 12, 16);
            String substring3 = StringUtils.substring(id, 16, 20);
            String newS = substring0 + '-' + substring1 + "-" + substring2 + "-" +substring3 + "-" + StringUtils.substring(id, 20);
            jsonObject.put("id", newS);
            result.add(JSONUtil.toJsonStr(jsonObject));
        }
        IOUtils.writeLines(result, "\n", new FileOutputStream(new File("H:\\Java\\th-future\\c.json")));
    }



    

}
