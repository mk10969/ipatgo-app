package org.uma.platform.feed.application.configuration;


import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories
public class MongoConfiguration extends AbstractReactiveMongoConfiguration {

    @Value("${mongo.host}")
    private String host;

    @Value("${mongo.port}")
    private String port;

    @Value("${mongo.database}")
    private String database;


    @Override
    public MongoClient reactiveMongoClient() {
//        return MongoClients.create(new ConnectionString("mongodb://10.0.2.2"));
        return MongoClients.create(new ConnectionString("mongodb://" + this.host + ":" + this.port + "/?replicaSet=rs0"));
    }

    @Override
    protected String getDatabaseName() {
        return this.database;
    }

    /**
     * 非推奨になったAPIがあり、
     *
     * @return ReactiveMongoTemplate
     * @throws Exception
     * @see org.springframework.data.mongodb.core.ReactiveMongoOperations#inTransaction()
     * 型をReactiveMongoOperationsから、ReactiveMongoTemplateに変更すれば利用可能。
     */
    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() throws Exception {
        return new ReactiveMongoTemplate(reactiveMongoDbFactory(), mappingMongoConverter());
    }

}
