package com.ais.brm.common.mongodb;

import com.ais.brm.common.domain.BrmException;
import com.ais.brm.common.utils.StringUtils;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Arrays.copyOfRange;

/**
 * mongodb utils for other modules.
 * <p>
 * reference document:  http://mongodb.github.io/mongo-java-driver/3.4/driver/
 * <p>
 * Created by zhaocaiwen on 2017/2/27.
 *
 * @author zhaocw@asiainfo-sec.com
 */
@Component
public class MongoUtils implements MongoHealthChecker {
    static Logger log = LoggerFactory.getLogger(MongoUtils.class);

    private static MongoClient mongoClient;//to be cached.

    private static MongoUtils mongoUtils;

    private IMongoSettings lastSettings;

    //连接默认库，getCollection传进去的无效
    private static String db;

    /**
     * You should try to use @Autowired to get this object, if failed, you can use getMongoUtils
     * 获得MongoUtils
     */
    public static MongoUtils getMongoUtils() {
        if (mongoUtils == null) {
            synchronized (MongoUtils.class) {
                mongoUtils = new MongoUtils();
            }
        }
        return mongoUtils;
    }

    public MongoUtils connect(IMongoSettings mongoSettings) throws BrmException {
        db = mongoSettings.getDb();
        if (mongoSettings == null) {
            throw new IllegalArgumentException("null mongosettings");
        }
        if (isMongoOk()) {
            log.debug("mongodb is ok, no need to connect.");
            return this;
        }
        connect2Mongo(mongoSettings);
        lastSettings = mongoSettings;
        return this;
    }

    /**
     * xx.
     *
     * @param dbName
     * @param collectionName
     * @return
     * @throws BrmException
     */
    public BrmMongoCollection<Document> getCollection(String dbName, String collectionName) throws BrmException {
        if (StringUtils.isEmpty(dbName)
                || StringUtils.isEmpty(collectionName)) {
            throw new IllegalArgumentException("bad param to get mongodb collection");
        }
        dbName = db;
        MongoDatabase database = mongoClient.getDatabase(dbName);
        if (database == null) {
            throw new BrmException("mongodb database not found :" + dbName);
        }
        return new BrmMongoCollection(this, database.getCollection(collectionName));
    }

    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    /**
     * implement the HealthChecker interface.
     *
     * @return
     */
    @Override
    public boolean isMongoOk() {
        try {
            if (mongoClient != null) {
                MongoDatabase adminDb = mongoClient.getDatabase("admin");
                if (adminDb != null) {
                    adminDb.runCommand(new Document("ping", 1));
                    return true;
                }
            }
        } catch (MongoException me) {
            mongoClient = null;
        }
        return false;
    }

    @Override
    public void reconnect() {
        if (lastSettings == null) {
            log.warn("no settings to reconnect.");
            return;
        }
        synchronized (this) {
            try {
                connect2Mongo(lastSettings);
            } catch (BrmException e) {
                log.error("reconnect to mongo failed", e);
            }
        }
    }


    //////////---------------- private methods ----------------/////////////////////

    private void connect2Mongo(IMongoSettings mongoSettings) throws BrmException {
        if (mongoSettings == null) {
            throw new BrmException("null mongoSettings found");
        }
        if (mongoClient == null || !isMongoOk()) {
            try {
                String hostStr = mongoSettings.getHostIp();
                int port = mongoSettings.getPort();
                log.info("try to connecting to mongodb {}:{}",
                        hostStr, port);
                String[] hosts = hostStr.split(",");
                List<ServerAddress> addresses = new ArrayList<>();
                for (String host : hosts) {
                    ServerAddress address = new ServerAddress(host, port);
                    addresses.add(address);
                }
                if (StringUtils.isEmpty(mongoSettings.getUsername()) ||
                        StringUtils.isEmpty(mongoSettings.getPassword())) {
                    log.warn("username or password is empty, connect to mongodb: {} with no auth!",
                            mongoSettings.getDb());

                    mongoClient = new MongoClient(addresses);
                } else {
                    log.warn("connect to mongodb: {} with auth, username: {}!",
                            mongoSettings.getDb(),
                            mongoSettings.getUsername());

                    mongoClient = new MongoClient(
                            addresses,
                            Collections.singletonList(
                                    MongoCredential.createCredential(
                                            mongoSettings.getUsername(),
                                            mongoSettings.getDb(),
                                            mongoSettings.getPassword().toCharArray())));
                }

                if (isMongoOk()) {
                    log.info("mongodb connected");
                } else {
                    log.error("mongodb connect failed.");
                }
            } catch (Exception e) {
                log.error("connect to mongodb failed,host {}, port {}",
                        mongoSettings.getHostIp(),
                        mongoSettings.getPort());
                throw new BrmException("mongodb connect error. check the log to see the details.");
            }

        }

    }

    /**
     * 批量插入到指定集合
     *
     * @param col       集合名
     * @param docs      插入文档列表
     * @param batchSize 批量大小
     * @return 插入次数
     */
    public int autoInsertMongoCol(BrmMongoCollection<Document> col, Document[] docs, int batchSize) {
        int size = docs.length;
        if (size == 0) {
            return 0;
        }
        if (size <= batchSize) {
            col.insertMany(asList(docs));
            return 1;
        }
        int num = size / batchSize;
        for (int i = 0; i < num; i++) {
            col.insertMany(asList(copyOfRange(docs, i * batchSize, i * batchSize + batchSize)));
        }
        if (size % batchSize != 0) {
            col.insertMany(asList(copyOfRange(docs, num * batchSize, size)));
            return num + 1;
        }
        return num;
    }
}
