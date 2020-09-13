package com.springbootIntegration.demo.test.mongodb.java.pojo;

import com.mongodb.Block;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.List;

import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import static java.util.Arrays.asList;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * @author liukun
 * @description
 * @since 2020/9/13
 */
public class MongodbTest {
    public static void main(String[] args) {
//        pojoCodecRegistry是pojo与BSON格式相互转换的编码器
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        // 在实例化client时创建pojoCodecRegistry
        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .applyConnectionString(new ConnectionString("mongodb://192.168.52.134:27017"))
                .build();
        MongoClient mongoClient = MongoClients.create(settings);

        // 数据库
        MongoDatabase database = mongoClient.getDatabase("admin");
        database = database.withCodecRegistry(pojoCodecRegistry);

//        MongoCollection<Document> collection = database.getCollection("admin");
//        collection = collection.withCodecRegistry(pojoCodecRegistry);
        // 集合，指定类型
        MongoCollection<Person> collection = database.getCollection("people", Person.class);

        // 插入该类型数据
        Person ada = new Person("Ada Byron", 20, new Address("St James Square", "London", "W1"));
        collection.insertOne(ada);

        // 插入多个数据
        List<Person> people = asList(
                new Person("Charles Babbage", 45, new Address("5 Devonshire Street", "London", "W11")),
                new Person("Alan Turing", 28, new Address("Bletchley Hall", "Bletchley Park", "MK12")),
                new Person("Timothy Berners-Lee", 61, new Address("Colehill", "Wimborne", null))
        );

        collection.insertMany(people);

        // 查询数据
        Block<Person> printBlock = new Block<Person>() {
            @Override
            public void apply(final Person person) {
                System.out.println(person);
            }
        };
        collection.find().forEach(printBlock);
        System.out.println("---------------------------------------------------------");

        // 获取单个符合过滤器查询的结果（注意，这还是一个内嵌的对象，所以使用address.city）
        Person somebody = collection.find(Filters.eq("address.city", "Wimborne")).first();
        System.out.println(somebody);

        System.out.println("---------------------------------------------------------");
        // 获取所有符合的对象
        collection.find(Filters.gt("age", 45)).forEach(printBlock);

        System.out.println("---------------------------------------------------------");
        // 更新文档
        collection.updateOne(Filters.eq("name", "Ada Byron"), combine(set("age", 23), set("name", "Ada Lovelace")));
        System.out.println("---------------------------------------------------------");

    }


}
