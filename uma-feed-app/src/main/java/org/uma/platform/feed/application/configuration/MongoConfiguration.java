package org.uma.platform.feed.application.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories
public class MongoConfiguration {

    @Value("${mongo.host}")
    private String host;

    @Value("${mongo.port}")
    private String port;

    @Value("${mongo.database}")
    private String database;

    protected String getDatabaseName() {
        return this.database;
    }

    @Bean
    public MongoDbFactory mongoDbFactory() {
        String connection = "mongodb://" + this.host + ":" + this.port + "/" + this.database;
        return new SimpleMongoClientDbFactory(connection);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }

}
