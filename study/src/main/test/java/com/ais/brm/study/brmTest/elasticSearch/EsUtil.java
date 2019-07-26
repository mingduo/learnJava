package com.ais.brm.study.brmTest.elasticSearch;


import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author by gengrc
 * @since 2018/5/14
 */
public final class EsUtil {
    private static Logger log = LoggerFactory.getLogger(EsUtil.class);

    private final TransportClient client;

    public EsUtil(TransportClient transportClient) {
        this.client = transportClient;
    }


    /**
     * 创建索引
     *
     * @param index 索引
     */
    public void createIndex(String index) {
        if (!isIndexExist(index)) {
            log.info("Index is not exits!");
        }
        CreateIndexResponse indexResponse = client.admin().indices().
                prepareCreate(index).execute().actionGet();
        log.info("执行建立成功？" + indexResponse.isAcknowledged());

    }

    /**
     * 删除索引
     *
     * @param index 索引名称
     */
    public void deleteIndex(String index) {
        if (!isIndexExist(index)) {
            log.info("Index is not exits!");
            return;
        }
        DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(index).
                execute().actionGet();
        if (dResponse.isAcknowledged()) {
            log.info("delete index " + index + "  successfully!");
        } else {
            log.info("Fail to delete index " + index);
        }
    }

    /**
     * 判断索引是否存在
     *
     * @param index 索引名称
     * @return boolean
     */
    public boolean isIndexExist(String index) {
        IndicesExistsResponse inExistsResponse = client.admin().indices().exists(
                new IndicesExistsRequest(index)).actionGet();
        if (inExistsResponse.isExists()) {
            log.info("Index [" + index + "] is exist!");
            return true;
        } else {
            log.info("Index [" + index + "] is not exist!");
            return false;
        }
    }


    /**
     * 插入数据
     *
     * @param map   要插入的document，类型map
     * @param index 索引，类似数据库
     * @param type  类型，类似表
     */
    public String addData(Map<String, ?> map, String index, String type) {
        IndexResponse response = client.prepareIndex(index, type, UUID.randomUUID().toString().
                replaceAll("-", "").toUpperCase()).
                setSource(map).get();

        //log.info("addData response status:{},id:{}", response.status().getStatus(), response.getId());

        return response.getId();
    }

    /**
     * 插入数据
     *
     * @param xContentType 要增加的数据
     * @param index        索引，类似数据库
     * @param type         类型，类似表
     */
    public String addData(XContentBuilder xContentType, String index, String type) {

        IndexResponse response = client.prepareIndex(index, type, UUID.randomUUID().toString().
                replaceAll("-", "").toUpperCase()).
                setSource(xContentType).get();

        //log.info("addData response status:{},id:{}", response.status().getStatus(), response.getId());

        return response.getId();
    }

    /**
     * 批量插入document
     *
     * @param list      要插入的list
     * @param batchSize 每批次的数据量
     * @param index     index名称
     * @param type      type名称
     */
    public void batchAddData(List<Map<String, Object>> list, int batchSize, String index, String type) {
        BulkRequestBuilder bulkBuilder = client.prepareBulk();
        int count = 0;
        for (Map<String, Object> m : list) {
            bulkBuilder.add(client.prepareIndex(index, type).setSource(m));
            count++;
            if (count % batchSize == 0) {
                BulkResponse response = bulkBuilder.execute().actionGet();
                if (response.hasFailures()) {
                    log.error("batch addData failure with error: " + response.buildFailureMessage());
                } else {
                    //log.info(" this batch added done,response status:{}", response.status().getStatus());
                }
                bulkBuilder = client.prepareBulk();

            }
        }
        if (bulkBuilder.numberOfActions() > 0) {
            BulkResponse response = bulkBuilder.execute().actionGet();
            if (response.hasFailures()) {
                log.error("batch addData failure with error: " + response.buildFailureMessage());
            } else {
            //    log.info(" this batch added done,response status:{}", response.status().getStatus());
            }
        }
    }


    /**
     * 通过ID删除数据
     *
     * @param index 索引，类似数据库
     * @param type  类型，类似表
     * @param id    数据ID
     */
    public void deleteDataById(String index, String type, String id) {

        DeleteResponse response = client.prepareDelete(index, type, id).
                execute().actionGet();

     //   log.info("deleteDataById response status:{},id:{}", response.status().getStatus(), response.getId());
    }

    /**
     * 通过ID 更新数据
     *
     * @param jsonObject 要增加的数据
     * @param index      索引，类似数据库
     * @param type       类型，类似表
     * @param id         数据ID
     * @return
     */
    public void updateDataById(Object jsonObject, String index, String type, String id) {

        UpdateRequest updateRequest = new UpdateRequest();

        updateRequest.index(index).type(type).id(id).doc(jsonObject);

        client.update(updateRequest);

    }

    /**
     * 通过ID获取数据
     *
     * @param index  索引，类似数据库
     * @param type   类型，类似表
     * @param id     数据ID
     * @param fields 需要显示的字段，逗号分隔（缺省为全部字段）
     * @return
     */
    public Map<String, Object> searchDataById(String index, String type, String id, String fields) {

        GetRequestBuilder getRequestBuilder = client.prepareGet(index, type, id);

        if (!"".equals(fields)) {
            getRequestBuilder.setFetchSource(fields.split(","), null);
        }

        GetResponse getResponse = getRequestBuilder.execute().actionGet();

        return getResponse.getSource();
    }


    /**
     * 关闭elasticsearch连接
     */
    public void destroy() {

        try {
            log.info("Closing elasticSearch client");
            if (client != null) {
                client.close();
            }
        } catch (Exception e) {
            log.error("Error closing ElasticSearch client: ", e);
        }
    }
}
