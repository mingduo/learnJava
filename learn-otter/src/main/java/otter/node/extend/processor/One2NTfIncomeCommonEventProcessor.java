package otter.node.extend.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.node.extend.processor.AbstractEventProcessor;
import com.alibaba.otter.shared.etl.model.EventColumn;
import com.alibaba.otter.shared.etl.model.EventData;

import java.math.BigInteger;

/**
 * <p>
 * 根据id_own_org转换成group
 * 查开关配置，判断是否同步
 * 如果需同步，则计算分片并改变写入表
 * <p>
 */
public class One2NTfIncomeCommonEventProcessor extends AbstractEventProcessor {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(One2NTfIncomeCommonEventProcessor.class);

    private static final String GROPU_ID = "id_own_group";
    private static final BigInteger BI_32 = BigInteger.valueOf(32L);
    private static final BigInteger BI_100 = BigInteger.valueOf(100L);

    /**
     * `tf_income` ->tf_income_shard_[0.31]
     *
     * @param eventData
     * @return
     */
    @Override
    public boolean process(EventData eventData) {
        EventColumn groupEventColumn = getColumn(eventData,GROPU_ID);
        if (groupEventColumn == null) {
            logger.error("[otter] column id_own_group null  :{}", JSON
                    .toJSONString(eventData.getColumns()));
            return false;
        }
        String groupId = groupEventColumn.getColumnValue();
        if (org.apache.commons.lang.StringUtils.isBlank(groupId)) {
            logger.error("[otter] column id_own_group null  :{}", JSON
                    .toJSONString(eventData.getColumns()));
            return false;
        }
        String tableName = eventData.getTableName();
        eventData.setTableName(tableName + "_shard_" + getShardNum(new BigInteger(groupId)));
        return true;


    }

    public static void main(String[] args) {
        System.out.println(getShardNum(BigInteger.valueOf(25965086392727926L)));
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


}