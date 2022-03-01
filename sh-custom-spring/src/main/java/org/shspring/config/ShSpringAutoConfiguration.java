package org.shspring.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "sh.spring")
@ConditionalOnProperty(name = "sh.spring.active", havingValue = "true")
@Configuration
public class ShSpringAutoConfiguration {

    @Getter
    @Setter
    private String active;

    @Getter
    @Setter
    private String version;




}
