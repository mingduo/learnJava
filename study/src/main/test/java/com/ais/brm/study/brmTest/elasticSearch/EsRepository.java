package com.ais.brm.study.brmTest.elasticSearch;


import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchModule;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

//import org.elasticsearch.client.transport.TransportClient;

/**
 * @author by gengrc
 * @since 2018/5/14
 */
//@Repository
public class EsRepository {
    private static Logger log = LoggerFactory.getLogger(EsRepository.class);

    private EsUtil esUtil;
    private final RestHighLevelClient esClient;

    @Autowired
    public EsRepository(RestHighLevelClient esClient) {

//        this.esUtil = new EsUtil(transportClient);
        this.esClient = esClient;

    }


    /**
     * 创建索引
     *
     * @param index 索引
     */
    public void createIndex(String index) {
        esUtil.createIndex(index);

    }

    /**
     * 删除索引
     *
     * @param index 索引名称
     */
    public void deleteIndex(String index) {
        esUtil.deleteIndex(index);
    }

    /**
     * 判断索引是否存在
     *
     * @param index 索引名称
     * @return boolean
     */
    private boolean isIndexExist(String index) {
        return esUtil.isIndexExist(index);
    }


    /**
     * 插入数据
     *
     * @param map   要插入的document，类型map
     * @param index 索引，类似数据库
     * @param type  类型，类似表
     */
    public String addData(Map<String, ?> map, String index, String type) {
        return esUtil.addData(map, index, type);
    }

    /**
     * 插入数据
     *
     * @param xContentType 要增加的数据
     * @param index        索引，类似数据库
     * @param type         类型，类似表
     */
    public String addData(XContentBuilder xContentType, String index, String type) {

        return esUtil.addData(xContentType, index, type);
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
//        esUtil.batchAddData(list, batchSize, index, type);
        int count = 0;
        BulkRequest request = new BulkRequest();
        for (Map<String, Object> m : list) {
            request.add(new IndexRequest(index, type).routing(String.valueOf(m.get("risk_index_id"))).source(m));
            count++;
            if (count % batchSize == 0) {
                executeBulk(request);
                request = new BulkRequest();
            }
        }
        if (request.numberOfActions() > 0) {
            executeBulk(request);
        }

    }

    private void executeBulk(BulkRequest request) {
        try {
            BulkResponse bulkResponse = esClient.bulk(request);
            if (bulkResponse.hasFailures()) {
                log.warn("batch addData failure with error: " + bulkResponse.buildFailureMessage());
            } else {
                log.debug(" this batch added done,response status:{}", bulkResponse.status().getStatus());
            }
        } catch (IOException e) {
            log.error("batch addData error : ", e);
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

        esUtil.deleteDataById(index, type, id);
    }

    /**
     * 通过ID 更新数据
     *
     * @param jsonObject 要增加的数据
     * @param index      索引，类似数据库
     * @param type       类型，类似表
     * @param id         数据ID
     */
    public void updateDataById(Object jsonObject, String index, String type, String id) {

        esUtil.updateDataById(jsonObject, index, type, id);

    }

    /**
     * 通过ID获取数据
     *
     * @param index  索引，类似数据库
     * @param type   类型，类似表
     * @param id     数据ID
     * @param fields 需要显示的字段，逗号分隔（缺省为全部字段）
     */
    public Map<String, Object> searchDataById(String index, String type, String id, String fields) {

        return esUtil.searchDataById(index, type, id, fields);
    }


    //转化sql 参数
    public List<Map<String, Object>> queryByPostArgs(String query, List<Object> objects) {
        query = parseParam(query, objects);
        SearchRequest searchRequest = new SearchRequest("ES_INDEX_FOR_INDEX_RESULT_INFO").
                types("ES_TYPE_FOR_INDEX_RESULT_INFO");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SearchModule searchModule = new SearchModule(Settings.EMPTY, false, Collections.emptyList());
        try (XContentParser parser = XContentFactory.xContent(XContentType.JSON).
                createParser(new NamedXContentRegistry(searchModule
                        .getNamedXContents()), query)) {
            searchSourceBuilder.parseXContent(parser);
        } catch (IOException e) {
            log.error("parse json to SearchSourceBuilder failed:", e);
        }

        searchRequest.source(searchSourceBuilder);
//        searchRequest.scroll(new TimeValue(60000));
//        searchRequest.source().size(1000);
        SearchResponse response;
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            response = esClient.search(searchRequest);
            for (SearchHit hit : response.getHits().getHits()) {
                Map<String, Object> sourceMap = hit.getSourceAsMap();
                Map<String, Object> map = new HashMap<>();
                map.put("risk_object_id", sourceMap.get("risk_object_id"));
                map.put("risk_index_value", sourceMap.get("risk_index_value"));
                list.add(map);
            }
//            while(response.getHits().getHits().length != 0) {
//                for (SearchHit hit: response.getHits().getHits()) {
//                    Object value = hit.getSourceAsMap().get("risk_index_value_number");
//                    System.out.println(value.toString());
//                }
//
//                if (response.getScrollId() == null) {
//                    log.warn("@Elasticsearch@ 查询结果不支持SCROLL!");
//                    break;
//                }
//
//                response = elasticsearch.searchScroll(
//                        new SearchScrollRequest(response.getScrollId()).scroll(new TimeValue(60000)));
//
//            }
            return list;
        } catch (IOException e) {
            log.error("@Elasticsearch@ 获取全部值出错！", e);
            return list;
        }

    }

    private String parseParam(String query, List<Object> objects) {
        if (objects.isEmpty()) return query;
        else {
            StringBuilder stringBuilder = new StringBuilder();
            String[] strings = query.split("\\?");
            for (int i = 0; i < strings.length - 1; i++) {
                stringBuilder.append(strings[i]).append(" \"").append(String.valueOf(objects.get(i)).trim())
                        .append("\" "); //这边还要传过来paramType,是String就拼接上引号，不是就不管了
            }
            stringBuilder.append(strings[strings.length - 1]);
            return stringBuilder.toString();
        }
    }

}
