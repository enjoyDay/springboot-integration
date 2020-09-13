package com.springbootIntegration.demo.test.mongodb.java;


import com.mongodb.Block;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Projections.*;


/**
 * @author liukun
 * @description 纯Java测试
 * @since 2020/9/8
 */
public class MongoDBJDBC {
    private static final String uriAlone = "mongodb://readWrite:readWrite@192.168.52.134:27017";

    private static final String uriCluster = "mongodb://readWrite:readWrite@xxx.xxx.xxx.243,xxx.xxx.xxx.244,xxx.xxx.xxx.245/database01?authSource=database01&readPreference=secondary&maxStalenessSeconds=120&connectTimeoutMS=60000";

    public static void main(String[] args) {
//        mongoCluster();
        MongoDBJDBC mongoDBJDBC = new MongoDBJDBC();
        mongoDBJDBC.createIndex();

        //mongoStandAlone();
    }

    /**
     * 单个插入
     */
    public void insertOneDocument() {
        MongoCollection dbCollection = getDbCollection();

        /**
         * 插入的数据类型
         *  {
         *    "name" : "MongoDB",
         *    "type" : "database",
         *    "count" : 1,
         *    "versions": [ "v3.2", "v3.0", "v2.6" ],
         *    "info" : { x : 203, y : 102 }
         *   }
         *
         *   BSON格式的数组类型和Java中的java.util.List相对应
         */
        Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                .append("info", new Document("x", 203).append("y", 102));

        // 插入单个文档，如果在文档最外层中没有添加_id字段，mongo会自动添加_id域
        dbCollection.insertOne(doc);
    }

    /**
     * 批量插入
     */
    public void insertManyDocument() {
        MongoCollection dbCollection = getDbCollection();
        List<Document> documents = new ArrayList<Document>();
        for (int i = 0; i < 100; i++) {
            documents.add(new Document("i", i));
        }

        dbCollection.insertMany(documents);

        // 获取集合数量
        System.out.println("集合中的数量：" + dbCollection.countDocuments());
    }

    /**
     * 查询集合
     */
    public void findDocument() {
        MongoCollection collection = getDbCollection();
        Document myDoc;
        // 获取集合中第一个文档
//        Document myDoc = (Document) getDbCollection().find().first();
//        System.out.println(myDoc.get("_id"));
//        System.out.println(myDoc.toJson());

        // 获取i值等于71的document
//        myDoc = (Document)collection.find(Filters.eq("i", 71)).first();
//        System.out.println(myDoc.toJson());

        Block<Document> printBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document.toJson());
            }
        };

        // 获取大于71的document
//        collection.find(Filters.gt("i", 71)).forEach(printBlock);

        // 带范围的查找 50<i<=100
//        collection.find(Filters.and(Filters.gt("i", 50), Filters.lte("i", 100))).forEach(printBlock);


        // 返回指定字段的文档
        collection.find(and(gte("stars", 2), Filters.lt("stars", 5), Filters.eq("categories", "Bakery")))
                .projection(new Document("name", 1)
                        .append("stars", 1)
                        .append("categories",1)
                        .append("_id", 0))
                .forEach(printBlock);
        // 为了比较方便创建要返回的字段，可使用下面的方式
        collection.find(and(gte("stars", 2), Filters.lt("stars", 5), Filters.eq("categories", "Bakery")))
                .projection(fields(include("name", "stars", "categories"), excludeId()))
                .forEach(printBlock);


        // 排序，按照指定字段升降序排序
        collection.find(and(gte("stars", 2), Filters.lt("stars", 5), Filters.eq("categories", "Bakery")))
                .sort(Sorts.ascending("name"))
                .forEach(printBlock);



    }

    /**
     * 更新文档
     */
    public void updateDocument() {
        MongoCollection collection = getDbCollection();
        // 更新单个文档，第一个参数是查询参数，第二个参数是要对查询到的文档更新的参数
        // $set用来指定一个字段的值，如果这个字段不存在，则创建它
//        UpdateResult updateResult = collection.updateOne(Filters.eq("i", 20), new Document("$set", new Document("i", 110)));
//        System.out.println("更新文档的数量" + updateResult.getModifiedCount());
//        System.out.println("匹配文档的数量" + updateResult.getMatchedCount());

        // 把所有i值小于100的，都加100
        UpdateResult updateResult = collection.updateMany(Filters.lt("i", 100), Updates.inc("i", 100));
        System.out.println(updateResult.getModifiedCount());
    }

    /**
     * 删除文档
     */
    public void deleteDocument() {
        MongoCollection collection = getDbCollection();
        // 删除指定条件的单个文档，并返回删除后的信息，该信息包含数量
//        DeleteResult deleteResult = collection.deleteOne(Filters.eq("i", 110));
//        System.out.println("删除文档数量：" + deleteResult.getDeletedCount());

        // 删除符合条件的多个文档，并返回删除后的信息，该信息包含数量
        DeleteResult deleteResult = collection.deleteMany(gte("i", 100));
        System.out.println(deleteResult.getDeletedCount());
    }

    /**
     * 创建索引
     */
    public void createIndex() {
        MongoCollection collection = getDbCollection();

        // 创建索引：
        collection.createIndex(new Document("i", 1));
    }

    /**
     * 运行命令
     */
    public void runCommand() {
        MongoClient mongoClient = MongoClients.create("mongodb://root:123456@192.168.52.134/?authSource=admin&authMechanism=SCRAM-SHA-256");
        MongoDatabase database = mongoClient.getDatabase("admin");

        Document buildInfoResults = database.runCommand(new Document("buildInfo", 1));
        System.out.println(buildInfoResults.toJson());

        Document collStatsResults = database.runCommand(new Document("collStats", "restaurants"));
        System.out.println(collStatsResults.toJson());
    }

    /**
     * 获取指定集合、创建数据库和集合
     *
     * @return 指定集合
     */
    private MongoCollection getDbCollection() {
        MongoClient mongoClient = MongoClients.create("mongodb://192.168.52.134:27017");
        // 如果指定的数据库不存在，则会在存储第一个数据时创建该数据库
        MongoDatabase database = mongoClient.getDatabase("admin");
        // 如果指定的集合不存在，则会在第一次存储文档时创建该集合
        MongoCollection<Document> collection = database.getCollection("admin");
        return collection;
    }

    //连接mongodb单机版
    public static void mongoStandAlone() {
        MongoClient mongoClient = MongoClients.create("mongodb://192.168.52.134:27017");
        MongoDatabase database = mongoClient.getDatabase("admin");
        MongoCollection<Document> col1 = database.getCollection("admin");
//        col1.drop(); // 删除集合
        // 查询方法会返回FindIterable迭代器
        FindIterable<Document> findIterable = col1.find();
        MongoCursor<Document> iterator = findIterable.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    //连接mongodb集群版
    public static void mongoCluster() {
//        try {
//            // 连接到 mongodb 服务
//            MongoClient mongoClient = MongoClients.create(uriCluster);
//            // 连接到数据库
//            MongoDatabase mongoDatabase = mongoClient.getDatabase("database01");
//            MongoCollection<Document> user = mongoDatabase.getCollection("person");
//            FindIterable<Document> findIterable = user.find();
//            MongoCursor<Document> mongoCursor = findIterable.iterator();
//            int i = 0;
//            while (mongoCursor.hasNext()) {
//                System.out.println(i++);
//                System.out.println(mongoCursor.next());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
