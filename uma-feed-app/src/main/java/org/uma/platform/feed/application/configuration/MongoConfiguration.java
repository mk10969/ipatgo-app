//package org.uma.platform.feed.application.configuration;
//
//
//import com.mongodb.ConnectionString;
//import com.mongodb.reactivestreams.client.MongoClient;
//import com.mongodb.reactivestreams.client.MongoClients;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
//import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
//
//@Configuration
//@EnableReactiveMongoRepositories
//public class MongoConfiguration extends AbstractReactiveMongoConfiguration {
//
//
//    @Override
//    public MongoClient reactiveMongoClient() {
//        return MongoClients.create(new ConnectionString("mongodb://10.0.2.2"));
//    }
//
//    @Override
//    protected String getDatabaseName() {
//        return "test";
//    }
//
//}
