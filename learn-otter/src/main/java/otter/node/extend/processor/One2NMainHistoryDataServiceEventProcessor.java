package otter.node.extend.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.node.extend.processor.AbstractEventProcessor;
import com.alibaba.otter.shared.etl.extend.processor.support.DataSourceFetcher;
import com.alibaba.otter.shared.etl.extend.processor.support.DataSourceFetcherAware;
import com.alibaba.otter.shared.etl.model.EventColumn;
import com.alibaba.otter.shared.etl.model.EventData;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 根据id_own_org转换成group
 * 查开关配置，判断是否同步
 * 如果需同步，则计算分片并改变写入表
 * <p>
 */
public class One2NMainHistoryDataServiceEventProcessor extends AbstractEventProcessor implements DataSourceFetcherAware {

    private static final Logger logger = LoggerFactory.getLogger(One2NMainHistoryDataServiceEventProcessor.class);

    private static final String ORG_ID = "id_own_org";
    private static final String GROPU_ID = "id_own_group";
    private static final BigInteger BI_32 = BigInteger.valueOf(32L);
    private static final BigInteger BI_100 = BigInteger.valueOf(100L);
    private JdbcTemplate jdbcTemplate;


    private static final List<String> EXCLUDE_COLS = Arrays.asList("virtual_employee_name",
            "virtual_service_name", "virtual_stuff_name");

    /**
     * `ts_maintain_history_data` ->ts_maintain_history_data_shard_[0.31]
     * 增加id_own_group
     *
     * @param eventData
     * @return
     */
    @Override
    public boolean process(EventData eventData) {
        EventColumn orgEventColumn = getColumn(eventData, ORG_ID);
        if (orgEventColumn == null) {
            logger.error("[otter] column org_id is null  :{}", JSON
                    .toJSONString(eventData.getColumns()));
            return false;
        }
        String orgId = orgEventColumn.getColumnValue();
        String groupId = getGroupId(orgId);
        if (StringUtils.isBlank(groupId)) {
            logger.error("[otter] column id_own_group null  :{}", JSON
                    .toJSONString(eventData.getColumns()));
            return false;
        }
        String tableName = eventData.getTableName();

        eventData.setTableName(tableName + "_shard_" + getShardNum(new BigInteger(groupId)));
        removeVirtualColums(eventData);
        addColumn(eventData, groupId);
        return true;


    }

    private void removeVirtualColums(EventData eventData) {
        eventData.getColumns()
                .removeIf(e -> EXCLUDE_COLS.contains(e.getColumnName()));
    }

    private void addColumn(EventData eventData, String groupId) {

        EventColumn eventColumn = new EventColumn();
        eventColumn.setColumnName(GROPU_ID);
        eventColumn.setColumnValue(groupId);
        eventColumn.setColumnType(Types.DECIMAL);
        eventColumn.setUpdate(true);
        eventData.getColumns().add(eventColumn);
    }


    private String getGroupId(String orgId) {
        String sql = " SELECT group_id from tg_org_group_member where org_id=? ";
        return jdbcTemplate.queryForObject(sql, new Object[]{orgId}, String.class);
    }


    /**
     * 获取分片键
     *
     * @param groupId
     * @return
     */
    private static BigInteger getShardNum(BigInteger groupId) {
        return groupId.divide(BI_100).mod(BI_32);
    }


    @Override
    public void setDataSourceFetcher(DataSourceFetcher dataSourceFetcher) {

        //这里是表的id,也就是源数据库里的某个表
        DataSource dataSource = dataSourceFetcher.fetch(1L);
        this.jdbcTemplate = new JdbcTemplate(dataSource);

    }
}