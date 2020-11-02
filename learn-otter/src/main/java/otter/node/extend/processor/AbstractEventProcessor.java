package otter.node.extend.processor;

import com.alibaba.otter.shared.etl.extend.processor.EventProcessor;
import com.alibaba.otter.shared.etl.model.EventColumn;
import com.alibaba.otter.shared.etl.model.EventData;
import org.apache.commons.lang.StringUtils;

/**
 * 业务自定义处理过程
 * 
 * @author jianghang 2012-6-25 下午02:26:36
 * @version 4.1.0
 */
public class AbstractEventProcessor implements EventProcessor {

    @Override
    public boolean process(EventData eventData) {
        // 默认啥都不处理
        return true;
    }

    protected EventColumn getColumn(EventData eventData, String columnName) {
        for (EventColumn column : eventData.getColumns()) {
            if (StringUtils.equalsIgnoreCase(column.getColumnName(), columnName)) {
                return column;
            }
        }

        for (EventColumn column : eventData.getKeys()) {
            if (StringUtils.equalsIgnoreCase(column.getColumnName(), columnName)) {
                return column;
            }
        }
        return null;
    }

}