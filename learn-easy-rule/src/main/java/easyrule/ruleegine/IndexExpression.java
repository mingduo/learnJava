package easyrule.ruleegine;

import org.jeasy.rules.api.Facts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <table border="1">
 * <tr><th>@Description:指标编码的指标规则表达式</th></tr>
 * <tr><td>@Date:Created in 2018-4-8</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class IndexExpression {
    static Logger logger = LoggerFactory.getLogger(IndexExpression.class);

    private String indexId;
    //IDX_1.value>0
    private String name;
    //IDX_1.value>0
    private String value;

    public String getIndexId() {
        return indexId;
    }

    public void setIndexId(String indexId) {
        this.indexId = indexId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public IndexExpression(String indexId, String name, String value) {
        this.indexId = indexId;
        this.name = name;
        this.value = value;
    }

    public IndexExpression(String name) {
        this.name = name;
    }

    public IndexExpression(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public IndexExpression() {
    }

    public void putExpressFact(Facts facts) {
        facts.put(getName(), this);
    }

    @Override
    public String toString() {
        return "IndexExpression{" +
                "indexId='" + indexId + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
