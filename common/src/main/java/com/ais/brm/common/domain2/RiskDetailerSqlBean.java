package com.ais.brm.common.domain2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 用于配置型风险点明细采集的THE_SQL字段解析结果保存.
 * Created by zhaocw on 2017-1-9.
 *
 * @author zhaocw
 */
public class RiskDetailerSqlBean {
    private String theSql;//数据库完整的the_sql内容，含sql和参数配置
    private String realSql;//前面的sql.
    private List<RiskDetailerSqlParam> params;
   
    //数据源类型
    private String datasourceId;
    
    public String getDatasourceId() {
		return datasourceId;
	}

	public void setDatasourceId(String datasourceId) {
		this.datasourceId = datasourceId;
	}

	@Override
    public String toString() {
//		String paramsdesc = params == null? "<empty>" :
//			String.join(", \n", params.stream()
//					.filter(Objects::nonNull)
//					.map(Object::toString)
//					.toArray(String[]::new));

        String paramsdesc = "";
        if(params==null) {
            paramsdesc = "<empty>";
        }else{
            List<String> paramStrs = new ArrayList<>();
            for(RiskDetailerSqlParam param : params){
                if(Objects.nonNull(param)){
                    paramStrs.add(param.toString());
                }
            }
            paramsdesc = String.join(", \n",paramStrs.toArray(new String[paramStrs.size()]));
        }
        return "RiskDetailerSqlBean {" +
                "theSql='" + theSql + '\'' +
                ", realSql='" + realSql + '\''+
                "'}' with Params [ " + paramsdesc + " ]";
    }

    public String getTheSql() {
        return theSql;
    }

    public void setTheSql(String theSql) {
        this.theSql = theSql;
    }

    public String getRealSql() {
        return realSql;
    }

    public void setRealSql(String realSql) {
        this.realSql = realSql;
    }

    public List<RiskDetailerSqlParam> getParams() {
        return params;
    }

    public void setParams(List<RiskDetailerSqlParam> params) {
        this.params = params;
    }
}
