package com.optile.cs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")
@Getter @Setter
public class AppSetting {
    private DB db;

    @Getter @Setter
    public static class DB {
        private String name;
        private String host;
        private Integer port;
    }
}
