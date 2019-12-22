package com.optile.cs.config;

import com.mongodb.client.MongoClients;
import com.optile.cs.AppSetting;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;

@Configuration
public class AppConfig {
    @Autowired
    private AppSetting appSetting;

    private void startDevMongoDBServer() throws IOException {
        IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
                                                              .net(new Net(appSetting.getDb().getHost(), appSetting.getDb().getPort(), false))
                                                              .build();

        MongodStarter.getDefaultInstance().prepare(mongodConfig).start();
    }

    private MongoTemplate mongoTemplate() {
        return new MongoTemplate(
                MongoClients.create(
                        "mongodb://"
                                .concat(appSetting.getDb().getHost())
                                .concat(":")
                                .concat(appSetting.getDb().getPort().toString()))
                , appSetting.getDb().getName());
    }

    @Bean
    @Profile("dev")
    public MongoTemplate devMongoTemplate() throws IOException {
        this.startDevMongoDBServer();
        return
                this.mongoTemplate();
    }

    @Bean
    @Profile("prod")
    public MongoTemplate prodMongoTemplate() throws IOException {
        return this.mongoTemplate();
    }
}
