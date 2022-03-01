package org.shspring.test;

import org.shspring.config.ShSpringAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestConfig implements CommandLineRunner {

    @Autowired
    private ShSpringAutoConfiguration shSpringAutoConfiguration;

    @Autowired
    private AACompoment aaCompoment;

    @Override
    public void run(String... args) throws Exception {
        String aa = new String();
        System.out.println(shSpringAutoConfiguration);
    }
}
