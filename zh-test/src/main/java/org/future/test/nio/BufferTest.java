package org.future.test.nio;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class BufferTest {

    @Test
    public void bufferTest_1() throws IOException {
        String file = "C:\\Users\\peng\\Desktop\\databases\\new\\43.real.sql";
        String jing = "###";
        String insert = "INSERT";
        FileInputStream fileInputStream = new FileInputStream(new File(file));
        List<String> lines = IOUtils.readLines(fileInputStream);

        for (String line : lines) {
            String sql = "";
            if (StringUtils.startsWith(line, "### INSERT")) {
                sql += line.substring(4);
            } else if (StringUtils.startsWith(line, "### UPDATE")) {
                sql += line.substring(4);
            } else if (StringUtils.startsWith(line, "### DELETE")) {

            } else if (StringUtils.startsWith(line, "### SELECT")) {

            } else {
                String replace = StringUtils.replace(line, "###   @\\d=", "");
                sql += replace;
            }

        }


        for (String line : lines) {
            if (StringUtils.startsWith(line, jing)) {
                line = line.substring(4);
            }
        }


    }

}
