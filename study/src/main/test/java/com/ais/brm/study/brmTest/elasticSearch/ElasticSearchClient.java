package com.ais.brm.study.brmTest.elasticSearch;

import com.ais.brm.common.domain2.ruleegine.RiskIndexResultHis;
import com.ais.brm.common.utils.MiscUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.percentiles.Percentile;
import org.elasticsearch.search.aggregations.metrics.percentiles.Percentiles;
import org.elasticsearch.search.aggregations.metrics.percentiles.PercentilesAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;

/**
 * Created by SEELE on 2018/4/25.
 */
public class ElasticSearchClient {
    static Logger logger = LoggerFactory.getLogger(ElasticSearchClient.class);


    private RestHighLevelClient getClient() throws UnknownHostException {
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("172.26.3.244", 9200, "http")/*,
                new HttpHost("10.21.16.142", 9200, "http")*/);
        Header[] defaultHeaders = new Header[]{new BasicHeader("header", "value")};
        builder.setDefaultHeaders(defaultHeaders);
        RestHighLevelClient restClient = new RestHighLevelClient(builder);
        return restClient;

    }

    @Test
    public void testIndex() throws IOException {
        RestHighLevelClient client = getClient();
        System.out.println(client);

        IndexRequest indexRequest = new IndexRequest("brm", "index_result_info_now", "1")
                .source("user", "kimchy",
                        "postDate", new Date(),
                        "message", "trying out Elasticsearch");
        IndexResponse indexResponse = client.index(indexRequest);
        System.out.println(indexResponse);

        client.close();
    }

    @Test
    public void testSearch() throws IOException {
        try (RestHighLevelClient client = getClient()) {
            List<RiskIndexResultHis> resultHisList = new ArrayList<>();

            SearchRequest searchRequest = new SearchRequest("risk_index_result_db")
                    .types("risk_index_result_info");

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.termQuery("risk_index_id", 10)).postFilter(rangeQuery("collect_time")
                    .gte("2018051700").lt("2018052100")
                    .format("yyyyMMddHH"));
            ;
            searchRequest.source(searchSourceBuilder);

            searchRequest.scroll(new TimeValue(60000));
            searchRequest.source().sort("collect_time").sort("save_time");

            SearchResponse response = client.search(searchRequest);


            System.out.println("response = [" + response + "]");
            int i = 0;
            //String scrollId = response.getScrollId();
            processScrollHits(response, searchHit -> {
                resultHisList.add(MiscUtils.fromJson(searchHit.getSourceAsString(), RiskIndexResultHis.class));
            });


         /*   do {
                for (SearchHit hit : response.getHits().getHits()) {
                    System.out.println(hit.getSourceAsString());
                    RiskIndexResultHis riskIndexResultHis = MiscUtils.fromJson(hit.getSourceAsString(), RiskIndexResultHis.class);
                    System.out.println(++i + ":" + riskIndexResultHis);
                    resultHisList.add(riskIndexResultHis);
                }

                if (scrollId == null) {
                    logger.warn("ES: 查询结果不支持SCROLL!");
                    break;
                }
                response = client.searchScroll(new SearchScrollRequest(scrollId).scroll(new TimeValue(60000)));

            } while (response.getHits().getHits().length != 0);*/

            System.out.println("final size:" + resultHisList.size());
        }


    }

    private void processScrollHits(SearchResponse response, Consumer<SearchHit> hitsHandler) throws IOException {
        String scrollId = response.getScrollId();

        do {
            for (SearchHit hit : response.getHits().getHits()) {
                hitsHandler.accept(hit);
            }

            if (scrollId == null) {
                logger.warn("ES: 查询结果不支持SCROLL!");
                break;
            }
            response = getClient().searchScroll(new SearchScrollRequest
                    (scrollId).scroll(new TimeValue(60000)));

        } while (response.getHits().getHits().length != 0);

    }

    @Test
    public void testSearch2() throws IOException {
        try (RestHighLevelClient client = getClient()) {
            List<RiskIndexResultHis> resultHisList = new ArrayList<>();

            SearchRequest searchRequest = new SearchRequest("risk_index_result_db")
                    .types("risk_index_result_info");

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            searchSourceBuilder.query(QueryBuilders.boolQuery().filter(rangeQuery("collect_time")
                    .lt("2016052100")
                    .format("yyyyMMddHH")).must(QueryBuilders.termQuery("risk_index_id", 10)))
                    .size(1)
                    .sort("collect_time", SortOrder.DESC)
                    .sort("save_time", SortOrder.DESC);


            searchRequest.source(searchSourceBuilder);

            searchRequest.scroll(new TimeValue(60000));
            searchRequest.source().sort("collect_time").sort("save_time");

            SearchResponse response = client.search(searchRequest);


            System.out.println("response = [" + response + "]");
            int i = 0;
            //String scrollId = response.getScrollId();
            processScrollHits(response, searchHit -> {
                resultHisList.add(MiscUtils.fromJson(searchHit.getSourceAsString(), RiskIndexResultHis.class));
            });

            System.out.println("final size:" + resultHisList.size());
        }


    }

    @Test
    public void testAggMetricsSearch() throws IOException {
        try (RestHighLevelClient client = getClient()) {

            TermsAggregationBuilder aggregation = AggregationBuilders
                    .terms("agg")
                    .field("risk_object_id").subAggregation(AggregationBuilders.avg("AVG").field("risk_index_value"));


            SearchRequest searchRequest = new SearchRequest("risk_index_result_db")
                    .types("risk_index_result_info");

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.termQuery("risk_index_id", 10)).postFilter(rangeQuery("collect_time")
                    .gte("2018051700").lt("2018052100")
                    .format("yyyyMMddHH"));
            ;
            searchRequest.source(searchSourceBuilder);

            searchSourceBuilder.aggregation(aggregation);

            SearchResponse response = client.search(searchRequest);
            Aggregation agg1 = response.getAggregations().get("agg");

            System.out.println(agg1);

        }
    }


    @Test
    public void testAggMetricsSearch3() throws IOException {
        try (RestHighLevelClient client = getClient()) {

            PercentilesAggregationBuilder aggregation =
                    AggregationBuilders
                            .percentiles("agg")
                            .field("risk_index_value")
                            .percentiles(1.0, 5.0, 10.0, 20.0, 30.0, 75.0, 95.0, 99.0);


            CardinalityAggregationBuilder aggregation3 =
                    AggregationBuilders
                            .cardinality("agg3")
                            .field("risk_index_value");


            SearchRequest searchRequest = new SearchRequest("brm-dev").types("index_result_info");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery())
                    .postFilter(rangeQuery("risk_index_value").from(20).to(30));
            searchSourceBuilder.aggregation(aggregation).aggregation(aggregation3);
            searchRequest.source(searchSourceBuilder);

            SearchResponse response = client.search(searchRequest);


            // Get your facet results
            Percentiles agg = response.getAggregations().get("agg");
            System.out.println(agg);
            for (Percentile entry : agg) {
                double percent = entry.getPercent();    // Percent
                double value = entry.getValue();        // Value

                logger.info("percent [{}], value [{}]", percent, value);
            }


            Cardinality agg3 = response.getAggregations().get("agg3");
            System.out.println(agg3.getValue());


            // Histogram agg2 = sr.getAggregations().get("agg2");
        }
    }

    @Test
    public void testAggBukletSearch() throws IOException {
        try (RestHighLevelClient client = getClient()) {

            TermsAggregationBuilder aggregation = AggregationBuilders
                    .terms("agg")
                    .field("risk_object_id").subAggregation(AggregationBuilders.avg("AVG").field("risk_index_value"));


            SearchRequest searchRequest = new SearchRequest("brm-dev").types("index_result_info");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());

            searchSourceBuilder.aggregation(aggregation);
            searchRequest.source(searchSourceBuilder);

            SearchResponse response = client.search(searchRequest);
            Terms agg = response.getAggregations().get("agg");

            for (Terms.Bucket entry : agg.getBuckets()) {
                String key = entry.getKeyAsString();            // bucket key
                long docCount = entry.getDocCount();            // Doc count
                Avg avg = entry.getAggregations().get("AVG");

                logger.info("key [{}], doc_count [{}],value [{}]", key, docCount, avg.getValue());
            }

        }
    }

    @Test
    public void testAggBukletSearch2() throws IOException {
        try (RestHighLevelClient client = getClient()) {

            TermsAggregationBuilder aggregation = AggregationBuilders
                    .terms("agg")
                    .field("risk_object_id").subAggregation(AggregationBuilders.percentiles("per")
                            .field("risk_index_value").percentiles(10.0, 20.0, 25, 50, 75.0, 95.0, 99.0));


            SearchRequest searchRequest = new SearchRequest("brm-dev").types("index_result_info");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            searchSourceBuilder.aggregation(aggregation);
            searchRequest.source(searchSourceBuilder);

            SearchResponse response = client.search(searchRequest);


            // Get your facet results
            Terms agg = response.getAggregations().get("agg");
            System.out.println(agg);
            for (Terms.Bucket entry : agg.getBuckets()) {
                String key = entry.getKeyAsString();            // bucket key
                long docCount = entry.getDocCount();            // Doc count
                Percentiles p = entry.getAggregations().get("per");
                String percentile = p.percentileAsString(50);
                logger.info("key [{}], ,value [{}]", key, percentile);
            }


            // Histogram agg2 = sr.getAggregations().get("agg2");
        }
    }

    @Test
    public void testAggBukletSearch3() throws IOException {
        try (RestHighLevelClient client = getClient()) {

            TermsAggregationBuilder aggregation = AggregationBuilders
                    .terms("risk_object_type_id").field("risk_object_type_id")
                    .subAggregation(AggregationBuilders
                            .terms("risk_object_id").field("risk_object_id"));
            SearchRequest searchRequest = new SearchRequest("risk_index_result_db").types("risk_index_result_info");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());

            searchSourceBuilder.size(0).aggregation(aggregation);
            searchRequest.source(searchSourceBuilder);

            SearchResponse response = client.search(searchRequest);
            Terms agg = response.getAggregations().get("risk_object_type_id");

            for (Terms.Bucket entry : agg.getBuckets()) {
                String key = entry.getKeyAsString();            // bucket key
                long docCount = entry.getDocCount();            // Doc count
                Terms avg = entry.getAggregations().get("risk_object_id");

                logger.info("key [{}], doc_count [{}],value [{}]", key, docCount, avg.getBuckets());
            }

        }
    }

    @Test
    public void testAggMetricsSearch4() throws IOException {
        try (RestHighLevelClient client = getClient()) {


            TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders
                    .terms("agg").field("risk_object_id")
                    .subAggregation(AggregationBuilders
                            .avg("xxx").field("risk_index_value"));


            SearchRequest searchRequest = new SearchRequest("brm-dev").types("index_result_info");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery())
                    .postFilter(rangeQuery("risk_index_value").from(20).to(30));
            searchSourceBuilder.aggregation(termsAggregationBuilder);
            searchRequest.source(searchSourceBuilder);

            SearchResponse search = client.search(searchRequest);


        }

    }


}