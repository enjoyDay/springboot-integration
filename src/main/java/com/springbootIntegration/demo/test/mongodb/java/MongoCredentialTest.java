package com.springbootIntegration.demo.test.mongodb.java;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.Arrays;

/**
 * @author liukun
 * @description 身份验证机制
 * @since 2020/9/13
 */
public class MongoCredentialTest {
    public static void main(String[] args) {
        String user = "root";     // the user name
        String source = "admin";   // the source where the user is defined
        char[] password = {'1','2','3','4','5','6'}; // the password as a character array
        // ...
//        MongoCredential credential = MongoCredential.createCredential(user, source, password);
//        MongoClient mongoClient = MongoClients.create(
//                MongoClientSettings.builder()
//                        .applyToClusterSettings(builder ->
//                                builder.hosts(Arrays.asList(new ServerAddress("192.168.52.134", 27017))))
//                        .credential(credential)
//                        .build());

        // 或者使用新的字符串拼接方法进行身份验证
        MongoClient mongoClient = MongoClients.create("mongodb://root:123456@192.168.52.134/?authSource=admin&authMechanism=SCRAM-SHA-256");
        MongoDatabase database = mongoClient.getDatabase("admin");
        MongoCollection<Document> col1 = database.getCollection("admin");
        FindIterable<Document> findIterable = col1.find();
        MongoCursor<Document> iterator = findIterable.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
