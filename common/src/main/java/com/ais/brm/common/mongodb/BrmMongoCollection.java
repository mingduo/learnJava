package com.ais.brm.common.mongodb;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * wrapper for mongoCollection with some health check.
 * Created by zhaocaiwen on 2017/3/2.
 *
 * @param <TDocument> the type of collection.
 * @author zhaocw@asiainfo-sec.com
 */
public class BrmMongoCollection<TDocument> {
    static Logger log = LoggerFactory.getLogger(MongoUtils.class);
    private final MongoCollection<TDocument> mongoCollection;
    private final MongoHealthChecker mongoHealthChecker;

    public BrmMongoCollection(MongoHealthChecker mongoHealthChecker,
                              MongoCollection<TDocument> mongoCollection) {
        this.mongoCollection = mongoCollection;
        this.mongoHealthChecker = mongoHealthChecker;
    }

    public long count(Bson var1) {
        return test(mongoCollection).count(var1);

    }

    public FindIterable<TDocument> find(Bson var1) {
        return test(mongoCollection).find(var1);
    }

    public FindIterable<TDocument> find() {
        return test(mongoCollection).find();
    }

    // added by lulj
    public void insertOne(TDocument document) {
        mongoCollection.insertOne(document);
    }

    // added by lulj
    public void insertMany(List<? extends TDocument> documents) {
        mongoCollection.insertMany(documents);
    }

    // add  by lulj
    public DeleteResult deleteOne(Bson filter) {
        return mongoCollection.deleteOne(filter);
    }

    // add  by lulj
    public DeleteResult deleteMany(Bson filter) {
        return mongoCollection.deleteMany(filter);
    }

    /////////------------private area----------------/////////
    private MongoCollection<TDocument> test(MongoCollection<TDocument> mongoCollection) {
        if (mongoHealthChecker.isMongoOk()) {
            return mongoCollection;
        } else {
            log.info("mongo test failed, try to reconnect...");
            mongoHealthChecker.reconnect();
            if (mongoHealthChecker.isMongoOk()) {
                log.info("reconnect to mongodb ok");
                return mongoCollection;
            }
        }
        throw new RuntimeException("mongodb not available now, retry failed.");
    }

    public AggregateIterable<TDocument> aggregate(List<Bson> pipeline) {
        // TODO Auto-generated method stub
        return mongoCollection.aggregate(pipeline);
    }


}
