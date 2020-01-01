package com.optile.cs.config;

import com.mongodb.client.MongoClients;
import com.optile.cs.AppSetting;
import com.optile.cs.job.executor.SimpleBootJobExecutor;
import com.optile.cs.job.model.JobType;
import com.optile.cs.job.receiver.model.EventMessage;
import com.optile.cs.job.receiver.model.StatusMessage;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {
    @Autowired
    private AppSetting appSetting;

    private MongodExecutable mongodExecutable;

    private void startDevMongoDBServer() throws IOException {
        IMongodConfig mongodbConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(appSetting.getDb().getHost(), appSetting.getDb().getPort(), false))
                .build();

        MongodExecutable mongodExecutable = MongodStarter
                .getDefaultInstance()
                .prepare(mongodbConfig);

        mongodExecutable.start();
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

    private Map<String, Class<?>> typeIds() {
        Map<String, Class<?>> typeIds = new HashMap<>();

        typeIds.put(
                StatusMessage.class.getSimpleName(),
                StatusMessage.class);

        typeIds.put(
                EventMessage.class.getSimpleName(),
                EventMessage.class);

        return typeIds;
    }

    @Bean
    public Map<JobType, Class> executors() {
        Map<JobType, Class> executors = new EnumMap<>(JobType.class);

        executors.put(
                JobType.SPRING_BOOT_JAR,
                SimpleBootJobExecutor.class);

        return executors;
    }

    @Bean
    @Profile("dev")
    public MongoTemplate devMongoTemplate() throws IOException {
        this.startDevMongoDBServer();
        return this.mongoTemplate();
    }

    @Bean
    @Profile("prod")
    public MongoTemplate prodMongoTemplate() throws IOException {
        return this.mongoTemplate();
    }

    @Bean
    public BrokerService brokerService() throws Exception {
        BrokerService brokerService = new BrokerService();

        brokerService.addConnector("tcp://localhost:61616");
        brokerService.setPersistent(false);

        return brokerService;
    }

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();

        messageConverter.setTargetType(MessageType.TEXT);
        messageConverter.setTypeIdMappings(typeIds());
        messageConverter.setTypeIdPropertyName("_type");

        return messageConverter;
    }
}
