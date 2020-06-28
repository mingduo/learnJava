package easyrule.ruleegine;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-4-16</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class RiskIndexAlertRel {
    private Long alertId;
    private Integer indexId;
    private String indexName;

    public Long getAlertId() {
        return alertId;
    }

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }

    public Integer getIndexId() {
        return indexId;
    }

    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public RiskIndexAlertRel(Long alertId, Integer indexId, String indexName) {
        this.alertId = alertId;
        this.indexId = indexId;
        this.indexName = indexName;
    }

    public RiskIndexAlertRel() {
    }
}
