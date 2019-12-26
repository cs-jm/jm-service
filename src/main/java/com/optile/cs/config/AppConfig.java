package com.optile.cs.config;

import com.mongodb.client.MongoClients;
import com.optile.cs.AppSetting;
import com.optile.cs.job.executor.SimpleJarJobExecutor;
import com.optile.cs.job.model.JobType;
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
import java.util.HashMap;
import java.util.Map;

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
    public Map<JobType, Class> executors() {
        return new HashMap() {{
            put(JobType.SIMPLE_JAR, SimpleJarJobExecutor.class);
        }};
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
