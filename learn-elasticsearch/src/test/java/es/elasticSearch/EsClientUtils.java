package es.elasticSearch;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.function.Consumer;


/**
 * RedisTemplate工具类
 *
 * @author weizc
 */
public final class EsClientUtils {
    private static Logger logger = LoggerFactory.getLogger(EsClientUtils.class);

    /***
     * <table border="1">
     * <tr><th>@Description: 处理可滚动结果集</th></tr>
     * <tr><td>@Date Created in 2018-5-21</td></tr>
     * <tr><td>@param [elasticsearchClient, response, hitsHandler]</td></tr>
     * <tr><td>@return void</td></tr>
     * <tr><td>@author weizc</td></tr>
     * </table>
     */
    public static void processScrollHits(RestHighLevelClient elasticsearchClient,
                                         SearchResponse response, Consumer<SearchHit> hitsHandler)
            throws IOException {
        String scrollId = response.getScrollId();

        do {
            for (SearchHit hit : response.getHits().getHits()) {
                hitsHandler.accept(hit);
            }

            if (scrollId == null) {
                logger.warn("ES: 查询结果不支持SCROLL!");
                break;
            }
            response = elasticsearchClient.searchScroll(new SearchScrollRequest
                    (scrollId).scroll(new TimeValue(60000)));

        } while (response.getHits().getHits().length != 0);

    }


    /**
     * 查询无命中结果
     *
     * @param response
     * @return
     */
    public static boolean hasHits(SearchResponse response) {
        // 出错！
        if (response.status() != RestStatus.OK) {
            logger.error("@Elasticsearch@ 查询失败！[status = {}]", response.status());
            return false;
        }

        // 无结果！
        if (response.getHits() == null || response.getHits().getTotalHits() == 0) {
            return false;
        }

        return true;
    }


}
