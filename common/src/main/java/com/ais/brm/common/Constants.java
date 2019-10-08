package com.ais.brm.common;

import org.apache.commons.lang.StringUtils;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * brm 系统常量.
 * Created by zhaocw on 2016/5/5.
 *
 * @author zhaocw
 */
public class Constants {
    // 风险详情汇总页面url映像
    public static final Map<String, String> RISK_DETAILS_MAPPING = new HashMap<String, String>();
    public static final int DEFAULT_DETAILER_THREADPOOL_SIZE = 5;//used by GeneralDetailer
    public static final int DEFAULT_COMPUTER_FOR_GENERAL_THREADPOOL_SIZE = 10;//used by GeneralDetailer
    public static final int DEFAULT_COMPUTER_FOR_GENERAL_BATCH_SIZE = 50;
    public static final long DEFAULT_REDIS_KEY_ALIVE_DAYS = 2; //缺省的redis key保持时间，超时自动删除.
    public static final long REDIS_KEY_ALIVE_DAYS = 31; //缺省的redis key保持时间，超时自动删除.

    public static final int DEFAULT_RISKRESULT_MODELER_BATCH_SIZE = 100;
    public static final int DEFAULT_RISKRESULT_MODELER_THREADPOOL_SIZE = 10;
    public static final String DELIM_RISK_OBJECT_NAME_BUFFER_KEY = "@@@";
    public static final String REDIS_KEY_LAST_MODEL_START_TIME = "LAST_MODEL_START_TIME";
    public static final int CONCURRENT_DO_MODEL_TIME_LIMIT_SECONDS = 30;


    static {
        RISK_DETAILS_MAPPING.put("Risk-Depart-NonWorkingTim", "/riskResultCategory/showNonWorkingTimeSummaryByHour.do");
        RISK_DETAILS_MAPPING.put("Risk-Staff-NonWorkingTim", "/riskResultCategory/showNonWorkingTimeSummaryByHour.do");
        RISK_DETAILS_MAPPING.put("Risk-User-HighSpeed", "/riskResultCategory/showNonWorkingTimeSummaryByHour.do");
        //RISK_DETAILS_MAPPING.put("Risk-User-HighSpeed", "/riskResultCategory/showHighSpeedSummaryByHour.do");
        RISK_DETAILS_MAPPING.put("Risk-User-AdjustCount", "/riskResultCategory/showAdjustCountSummaryByDay.do");
        RISK_DETAILS_MAPPING.put("Risk-User-AdjustSum", "/riskResultCategory/showAdjustSumSummaryByDay.do");
        RISK_DETAILS_MAPPING.put("Risk-Staff-Login", "/riskResultCategory/showStaffLoginSummaryByIP.do");
        RISK_DETAILS_MAPPING.put("Risk-Depart-HighSpeed", "/riskResultCategory/showHighSpeedSummaryByHour.do");
        RISK_DETAILS_MAPPING.put("Risk-Staff-HighSpeed", "/riskResultCategory/showHighSpeedSummaryByHour.do");
        RISK_DETAILS_MAPPING.put("Risk-Staff-MonthCount", "/riskResultCategory/showStaffMonthCountSummary.do");
        RISK_DETAILS_MAPPING.put("Risk-Staff-DayCount",
                "/riskResultCategory/showStaffDayCountSummary.do");
        //RISK_DETAILS_MAPPING.put("Risk-Depart-Discnt-Plenty",
        // "/riskResultIntegralDetails/loadChannelDiscntOrServiceSummary.do");
        RISK_DETAILS_MAPPING.put("Risk-User-IntegralDayAmount", "/riskResultIntegralDetails/loadIntegralSummary.do");
        RISK_DETAILS_MAPPING.put("Risk-User-IntegralDayTime", "/riskResultIntegralDetails/loadIntegralSummary.do");
        RISK_DETAILS_MAPPING.put("Risk-User-IntegralInTime",
                "/riskResultIntegralDetails/loadIntegralSummary.do");
        RISK_DETAILS_MAPPING.put("Risk-User-IntegralInAmount",
                "/riskResultIntegralDetails/loadIntegralSummary.do");
        RISK_DETAILS_MAPPING.put("Risk-User-IntegralOutTime",
                "/riskResultIntegralDetails/loadIntegralSummary.do");
        RISK_DETAILS_MAPPING.put("Risk-User-IntegralOutAmount",
                "/riskResultIntegralDetails/loadIntegralSummary.do");
    }

    //kafka topics.
    public static final String TOPIC_NOTIF_TASKDONE_DATACOLLECTOR = "BRM_NOTIF_TASKDONE_DATACOLLECTOR";
    public static final String TOPIC_NOTIF_TASKDONE_DATAREAD = "BRM_NOTIF_TASKDONE_DATAREAD";
    public static final String TOPIC_NOTIF_TASK_READDATA = "BRM_NOTIF_TASK_READDATA";
    public static final String TOPIC_NOTIF_TASK_ANALYZE = "BRM_NOTIF_TASK_ANALYZE";
    public static final String TOPIC_NOTIF_TASKDONE_ANALYZE = "BRM_NOTIF_TASKDONE_ANALYZE";
    public static final String TOPIC_NOTIF_TASKDONE_SUS = "BRM_NOTIF_TASKDONE_SUS";
    public static final String TOPIC_NOTIF_TASK_COLLECTDATA = "BRM_NOTIF_TASK_COLLECTDATA";

    //二阶段TOPICs
    public static final String TOPIC_NOTIF_TASK_RISK_COLLECT = "BRM_NOTIF_TASK_RISK_COLLECT";
    public static final String TOPIC_NOTIF_TASK_RISK_COMPUTE = "BRM_NOTIF_TASK_RISK_COMPUTE";
    public static final String TOPIC_LOGS = "BRM_LOGS";
    public static final String TOPIC_NOTIF_TASK_RISK_COMPUTEDONE = "BRM_NOTIF_TASK_RISK_COMPUTE_DONE";
    public static final String TOPIC_NOTIF_TASK_RISK_MODEL = "BRM_NOTIF_TASK_RISK_MODEL";
    public static final String TOPIC_NOTIF_TASK_MIGUETL_FIELDREQ = "BRM_NOTIF_TASK_MIGUETL_FIELDREQ";
    public static final String TOPIC_NOTIF_TASK_RISK_DETAIL_COLLECT_REQ = "BRM_NOTIF_TASK_RISK_DETAIL_COLLECT_REQ";
    public static final String TOPIC_NOTIF_TASK_MIGU_JIEKOUJI = "BRM_NOTIF_MIGU_JIEKOUJI";
    public static final String TOPIC_NOTIF_TASK_MIGU_ML = "BRM_NOTIF_MIGU_ML";
    public static final String TOPIC_NOTIF_JOB_START = "TOPIC_NOTIF_JOB_START";
    //同步数据
    public static final String TOPIC_NOTIF_TASK_SYNC_DATA = "BRM_NOTIF_TASK_SYNC_DATA";
    public static final String TOPIC_NOTIF_MONITOR_START = "TOPIC_NOTIF_MONITOR_START";//发起监控请求
    public static final String TOPIC_NOTIF_RULE_START = "NOTIF_RULE_START";//发起监控请求
    public static final String TOPIC_NOTIF_TASK_RISK_INDEX_COLLECT = "TOPIC_INDEX_COLLECT";//sql指标采集请求.
    public static final String TOPIC_NOTIF_TASK_RISK_INDEX_ANALYZE = "TOPIC_RULE_INDEX_ANALYZE";//sql指标分析请求.
    public static final String TOPIC_NOTIF_TASK_RISK_OFFLINE_DETAIL_COLLECT = "TOPIC_DETAILER";//离线明细采集请求.
    public static final String TOPIC_REINDEX_REQ_NEW = "TOPIC_REINDEX_REQ_NEW";//指标补录请求.
    //2.2.5.3	多指标后台JOB调度 topic
    public static final String TOPIC_MULTIINDEX_ANALYZE = "TOPIC_MULTIINDEX_ANALYZE";

    // 采集调度方式：按天
    public static final int COLLECT_INTERVAL_TYPE_DAY = 10;

    // 采集调度方式：按小时
    public static final int COLLECT_INTERVAL_TYPE_HOUR = 11;

    //stream
    public static final String TOPIC_STREAM = "TOPIC_STREAM";
    public static final String TOPIC_INDEX_RESULT = "TOPIC_INDEX_RESULT";

    //storage types referenced by notifs.
    public static final int STORAGE_TYPE_MYSQL = 1;
    public static final int STORAGE_TYPE_REDIS = 2;
    public static final int STORAGE_TYPE_ORACLE = 3;
    public static final int STORAGE_TYPE_FILE = 4;


    //kafka notif types.
    public static final int KAFKA_NOTIF_TYPE_DATACOLLECT_TASKDONE = 0;
    public static final int KAFKA_NOTIF_TYPE_NEWDATAREAD = 1;//a new dataread request.
    public static final int KAFKA_NOTIF_TYPE_ANALYZE = 2;
    public static final int KAFKA_NOTIF_TYPE_ANALYZE_TASKDONE = 3;
    public static final int KAFKA_NOTIF_TYPE_DATACOLLECT = 4;
    public static final int KAFKA_NOTIF_TYPE_RISKCOLLECT = 5;
    public static final int KAFKA_NOTIF_TYPE_RISKCOMPUTE = 6;
    public static final int KAFKA_NOTIF_TYPE_RISKMODEL = 7;
    public static final int KAFKA_NOTIF_TYPE_RISKCOMPUTEDONE = 8;
    public static final int KAFKA_NOTIF_TYPE_RISKDETAILCOLLECT = 11;//采集明细通知
    public static final int KAFKA_NOTIF_TYPE_JOB_START = 12;
    public static final int KAFKA_NOTIF_TYPE_MONITOR = 13;
    public static final int KAFKA_NOTIF_TYPE_RULEENGINE = 14;
    public static final int KAFKA_NOTIF_TYPE_RISKINDEX = 15;//指标采集任务
    public static final int KAFKA_NOTIF_TYPE_RISKANALYZE = 16;//指标采集任务
    public static final int KAFKA_NOTIF_TYPE_RISKREINDEX = 18;//指标补录采集任务
    public static final String MASTER_DATASOURCE_TYPE_ID = "000001";//主库数据源
    public static final String ANALYZE_DATASOURCE_TYPE_ID = "000002";//分析库数据源
    public static final String ORGIN_DATASOURCE_TYPE_ID = "000003";//源库数据源
    public static final String DETAIL_DATASOURCE_TYPE_ID = "000004";//明细库数据源
    public static final String HIVE_DATASOURCE_TYPE_ID = "000005";//hive数据源
    public static final String MONGODB_DATASOURCE_TYPE_ID = "000006";//mongodb数据源
    public static final int KAFKA_NOTIF_TYPE_OFFLINE_DETAIL = 17;//离线明细采集任务


    //同步数据
    public static final int KAFKA_NOTIF_TYPE_SYNCDATA = 9;

    //result codes.
    public static final int RESULTCODE_SUCCESS = 200;
    public static final int RESULTCODE_FAILED = 500;

    //其他常量
    public static final long KAFKA_PRODUCE_TIMEOUT = 5;//kafka生产者发送消息的超时时间，秒

    //redis keys.
    public static final String REDIS_KEY_KAFKA_RETRY_LIST = "KAFKA_RETRY_LIST";//the todotask keys.
    public static final String REDIS_KEY_KAFKA_RETRY_MAP = "KAFKA_RETRY_MAP"; // the key --> todotask objects.
    public static final String REDIS_KEY_MODEL_USER_BALANCE = "UserBalanceMap";
    public static final String REDIS_KEY_MODEL_USER_RETURN_COUNT = "UserReturnCountMap";
    public static final String REDIS_KEY_MODEL_USER_RETURN_FEE = "UserReturnFeeMap";
    public static final String REDIS_KEY_MODEL_WAIT_MAP = "modelWaitMap";
    public static final String REDIS_KEY_DETAIL_TRADES_STATEMAP =
            "redisKeyDetailTradesStateMap";//存放流水号处理状态
    public static final String REDIS_KEY_DETAIL_TRADES_MAP
            = "redisKeyDetailTradesMap";//存放riskResultStarId-->流水号明细列表映射.
    //通用采集结果
    public static final String REDIS_KEY_GENERAL_COLLECTOR_RESULT
            = "REDIS_KEY_GENERAL_COLLECTOR_RESULT";

    //明细采集任务表
    public static final String REDIS_KEY_DETAIL_COLLECTOR_REQUESTS
            = "REDIS_KEY_DETAIL_COLLECTOR_REQUESTS";


    //渠道相关模型.
    public static final String REDIS_KEY_MODEL_CHANNEL_SUM = "ChannelSumMap";//操作量.
    public static final String REDIS_KEY_MODEL_CHANNEL_BUKA = "ChannelBukaSet";//补卡.
    public static final String REDIS_KEY_MODEL_CHANNEL_CHANGEPWD = "ChannelChangePwdSet";//修改密码.
    public static final String REDIS_KEY_MODEL_CHANNEL_SUM_DAILY = "ChannelSumDailySet";//日操作量.（按照业务类型）
    public static final String REDIS_KEY_MODEL_CHANNEL_DAILY_AVG = "ChannelSumDailyAvgMap";//按照业务类型的均值模型.
    public static final String REDIS_KEY_MODEL_CHANNEL_SERVENT_HOURLY_SUM
            = "ChannelServantHourlyMap";//按照营业员统计小时操作量.

    //job重试相关
    public static final String REDIS_KEY_JOB_RETRY_TIMES = "RETRY_TIMES_MAP";

    public static final java.lang.String PREFIX_MODEL_CODE = "M_";//模型编码开头.

    //告警级别.
    public static final int LEVEL_1 = 1;
    public static final int LEVEL_2 = 2;
    public static final int LEVEL_3 = 3;
    public static final int LEVEL_4 = 4;

    public static final String LOGIN_USER = "LOGIN_USER";
    public static final int SUPER_ROLE_ID = 0;
    //url和ID的映射关系
    public static final String URL_PRIVID_MAP = "URL_PRIVID_MAP";
    public static final String START_TIME = " 00:00:00";
    public static final String END_TIME = " 23:59:59";

    //风险点相关
    /**
     * 用户高速办理
     */
    public static final String REDIS_KEY_RISK_USER_HIGHSPEED_MAP = "Risk-User-HighSpeed";
    /**
     * 用户调账次数
     */
    public static final String REDIS_KEY_RISK_USER_ADJUSTCOUNT_MAP = "Risk-User-AdjustCount";
    /**
     * 用户调账金额
     */
    public static final String REDIS_KEY_RISK_USER_ADJUSTSUM_MAP = "Risk-User-AdjustSum";

    /**
     * /**
     * 营业员高速办理
     */
    public static final String REDIS_KEY_RISK_STAFF_HIGHSPEED_MAP = "Risk-Staff-HighSpeed";
    /**
     * 营业员日总量
     */
    public static final String REDIS_KEY_RISK_STAFF_DAYCOUNT_MAP = "Risk-Staff-DayCount";
    /**
     * 营业员月总量
     */
    public static final String REDIS_KEY_RISK_STAFF_MONTHCOUNT_MAP = "Risk-Staff-MonthCount";
    /**
     * 营业员异常登录
     */
    public static final String REDIS_KEY_RISK_STAFF_LOGIN_MAP = "Risk-Staff-Login";

    /**
     * riskModelerImpl缓存的risk_object_ID和名称的对应关系
     */
    public static final String REDIS_KEY_RISK_OBJECT_NAME_BUFFER_MAP = "Risk_Object_ID";

    /**
     * 渠道高速办理
     */
    public static final String REDIS_KEY_RISK_DEPART_HIGHSPEED_MAP = "Risk-Depart-HighSpeed";
    /**
     * 用户操作额度过大
     */
    public static final String REDIS_KEY_RISK_USER_EXCEEDED_MAP = "Risk-User-Exceeded";
    /**
     * 用户积分转入转出次数过多
     */
    public static final String REDIS_KEY_RISK_USER_INTEGRAL_PRESENT_OR_RECEIVE_TIMES_MAP
            = "Risk-User-JiFenPresentOrReceiveTimes";
    /**
     * 用户积分转入转出值过高
     */
    public static final String REDIS_KEY_RISK_USER_INTEGRAL_PRESENT_OR_RECEIVE_AMOUNTS_MAP
            = "JiFenPresentOrReceiveAmount";
    /**
     * 用户办理互斥，重复的业务
     */
    public static final String REDIS_KEY_RISK_USER_SUBSCRIBE_MAP = "Risk-User-Subscribe-Trades";
    /**
     * 渠道反复办理优惠，服务，月头月尾办理集中
     */
    public static final String REDIS_KEY_RISK_CHANNEL_TRADES_MAP = "Risk-Channel-Trades";
    //风险点更新类型
    public static final int UPDATE_TYPE_ADD = 1;//累加
    public static final int UPDATE_TYPE_UPDATE = 2;//更新
    public static final String REDIS_KEY_RISK_USER_PREDEPOSIT_MAP = "Risk-User-PreDeposit";

    /**
     * 反复订购
     */
    public static final String REDIS_KEY_RISK_USER_TRADETOOMUCH_MAP = "Risk-User-TradeTooMuch";
    /**
     * 渠道低消费高活跃
     */
    public static final String REDIS_KEY_RISK_DEPART_LOWCONSUMPTIONHIGHACTIVIT_MAP
            = "Risk-Depart-LowConsumptionHighActivit";

    /**
     * 渠道高危操作
     */
    public static final String REDIS_KEY_RISK_DEPART_HIGHRISKOPER_MAP = "Risk-Depart-highRiskOper";


    /**
     * 用户反复开通
     */
    public static final String REDIS_KEY_RISK_USER_TRADEOPENTOOMUCH_MAP = "Risk_User_TradeOpenTooMuch";

    /**
     * 停机订购
     */
    public static final String REDIS_KEY_RISK_USER_STATESTOP_MAP = "Risk-User-StateStop";

    /**
     * 销户订购
     */
    public static final String REDIS_KEY_RISK_USER_STATECLOSE_MAP = "Risk-User-StateClose";

    /**
     * 转网订购
     */
    public static final String REDIS_KEY_RISK_USER_STATELEAVE_MAP = "Risk-User-Stateleave";

    /**
     * 电子渠道反复办理优惠
     */
    public static final String REDIS_KEY_RISK_USER_ECHANNELDISTINCT_MAP = "Risk-User-EchannelDistinct";


    //属性的KEY常量
    public static final String PROP_KEY_RISK_RESULT_STARS = "riskResultStarIdJson";

    public static final int DETAIL_TYPE_TRADES = 0;//台账类型的明细.
    public static final int DETAIL_TYPE_JIFEN = 1; //积分类型的明细.
    public static final int DETAIL_TYPE_ADJUST = 2; //调账类型的明细.

    public static final int IS_NOT_TEMPLATE = 0;// 不是模板
    public static final int IS_TEMPLATE = 1;// 是模板

    //风险监控对象
    /**
     * 用户
     */
    public static final int ROT_TELECOM_USER = 100001;
    /**
     * 操作员
     */
    public static final int ROT_TELECOM_STAFF = 100002;
    /**
     * 渠道
     */
    public static final int ROT_TELECOM_CHANNEL = 100003;
    /**
     * 咪咕视讯用户
     */
    public static final int ROT_MIGU_USER = 213101;
    /**
     * 咪咕视讯主播
     */
    public static final int ROT_MIGU_HOST = 213102;
    /**
     * 咪咕视讯渠道
     */
    public static final int ROT_MIGU_CHANNEL = 213103;
    /**
     * 咪咕视讯营销活动
     */
    public static final int ROT_MIGU_PROMO = 213104;
    /**
     * 咪咕视讯省份
     */
    public static final int ROT_MIGU_PROVINCE = 213105;
    /**
     * 咪咕视讯监播（打分）人员
     */
    public static final int ROT_MIGU_SCORE = 213106;
    /**
     * 咪咕主播作品
     */
    public static final int ROT_MIGU_HOSTWORK = 213107;


    /**
     * 北京电信用户
     */
    public static final int BJDX_USER = 121101;

    /**
     * 北京电信渠道
     */
    public static final int BJDX_CHANNEL = 121103;

    /**
     * 北京电信专线产品
     */
    public static final int BJDX_SPECIAL = 121107;

    /**
     * 北京电信集团订单
     */
    public static final int BJDX_BJDXGROUPORDER = 121108;
    /**
     * 北京电信用户实例
     */
    public static final int BJDX_USERINSTANCE = 121109;
    /**
     * 北京电信图表和模型配置信息
     */
    public static final String KEY_MODEL_CHARTS_INFO = "key-model-charts-info";
    public static final String IF_INIT_KEY_MODEL_CHARTS_INFO = "if-init-key-model-charts-info";


    /**
     * 江苏电信
     * 地域级别 0：全局（集团）
     */
    public static final int OVERALLSITUATION = 0;

    /**
     * 江苏电信
     * 地域级别 1：省级
     */
    public static final int PROVINCELEVEL = 1;

    /**
     * 江苏电信
     * 地域级别2：地市
     */
    public static final int CITY = 2;

    /**
     * 江苏电信
     * 地域级别3：县区
     */
    public static final int COUNTY = 3;

    /* CRM&计费均无用户资料  */
    public static final Integer NOUSER = 10;

    /* 用户新装未竣工  */
    public static final Integer START = 20;

    /* 计费无用户资料  */
    public static final Integer NONAVAIBLE = 30;

    /*  用户已拆机 */
    public static final Integer DELL = 40;

    /* 平台-计费产品不匹配  */
    public static final Integer PLATFORM = 50;

    /*  平台-计费IMSI不匹配 */
    public static final Integer IMSI = 60;

    /* 其他  */
    public static final Integer OTHER = 0;

    /* 业务平台:CRM&计费均无用户资料 */
    public static final String NOUSERINFO = "该协查单中涉及的用户在CRM和计费均无开户信息，请核实平台开户情况"
            + "建议处理方式："
            + "1.将此号码业务使用权限在平台关闭"
            + "2.在CRM系统补录相关用户资料"
            + "3.对于无法关停及补录的号码，请业管确认后通知计费部门做不计费处理";

    /* 业务平台:用户已拆机 */
    public static final String USERALREADYDISMANTLE = "该协查单中涉及的用户已经拆机，但在你处仍有使用话单，请尽快核查原因并处理。";
    public static final int PLAT = 4;
    /* 本地OSS:用户新装未竣工 */
    public static final String USERNOCOMPLETE = "该协查单中涉及的用户号码已经可以使用电信业务，"
            + "但由于在CRM系统存在在途工单，无法收取相关费用。请及时做报竣工处理。";

    /* 本地计费:计费无用户资料 */
    public static final String CHARGINGNOUSERINFO = "该协查单中涉及的号码在CRM侧资料正常，计费侧无资料，请检查计费档案异常原因并及时处理。";

    /* 本地计费:平台-计费产品不匹配 */
    public static final String CHARGINGPRODUCTNOMISMATCH = "该协查单中涉及的号码在CRM侧资料正常，计费侧无资料，"
            + "请检查计费档案异常原因并及时处理。";

    /* 本地CRM:平台-计费IMSI不匹配 */
    public static final String CHARGINGIMSINOMISMATCH = "该协查单中涉及用户话单和计费档案IMSI不一致，请核查是否补换卡操作存在异常并处理";
    public static final int CRM = 3;

    /**
     * 解码Url
     */
    public static String decodeUrlParam(String param) throws Exception {
        if (StringUtils.isNotEmpty(param)) {
            return URLDecoder.decode(param, "UTF-8");
        } else {
            return "";
        }
    }

    /*monitor相關*/
    public static final int MONITOR_ALERT_LEVEL_NORMAL = 0;
    public static final int MONITOR_ALERT_LEVEL_IMPORT = 1;
    public static final int MONITOR_ALERT_LEVEL_SERIOUS = 2;
    public static final int MONITOR_ALERT_READ = 1;
    public static final int MONITOR_ALERT_NOT_READ = 0;
    public static final String RISKER_MON_CONFIG_KEY = "Risker";


    /*monitor rule名稱*/
    public static final String RISKER_RULE_JVM_MEMORY = "RISKER_RULE_JVM_MEMORY";
    public static final String RISKER_RULE_APP_ALIVE = "RISKER_RULE_APP_ALIVE";

    public static final String RISKER_RULE_PHYSICAL_MEMORY = "RISKER_RULE_PHYSICAL_MEMORY";
    public static final String RISKER_RULE_CPU = "RISKER_RULE_CPU";
    public static final String RISKER_RULE_LOGS = "RISKER_RULE_LOGS";
    public static final String RULE_LOGS_KEY = "log_keys";
    public static final String IS_ALL_EXCEPTION = "IS_ALL_EXCEPTION";//是否找出所有的异常 add by zhangyuanyuan

    //0表示监控类型的任务
    public static final String JOB_FOR_MONITOR = "1";
    //1表示所有周期任务
    public static final String JOB_FOR_COMMON = "0";

    //0表示单指标监控规则新增类型	
    public static final String ADD = "0";
    //1表示单指标监控规则修改类型
    public static final String MODIFY = "1";

    //11表示单单指标监控规则告警方式为内部告警
    public static final int IN_ALARM = 10;

    //re模块


    //指标采集周期
    //按日采集
    public static final String DAILY_INTERVAL_CONFIG = "10";
    //按小时采集
    public static final String HOURLY_INTERVAL_CONFIG = "11";
    //实时
    public static final String REAL_TIME_INTERVAL_CONFIG = "12";
    public static final String TOPIC_INDEX_RULE_CHANGED = "TOPIC_INDEX_RULE_CHANGED";
    //规则变更类型：rule_change_type(10：新增、11：修改、12：删除）
    public static final int ADD_RULE_CHANGE = 10;
    public static final int MOD_RULE_CHANGE = 11;
    public static final int DEL_RULE_CHANGE = 12;

    //日志等级
    public static final int LOG_LEVEL_GENERAL = 10;//普通
    public static final int LOG_LEVEL_IMPORTANT = 11;//重要
    public static final int LOG_LEVEL_UNUSUAL = 12;//异常
    public static final int LOG_LEVEL_OTHER = 13;//其他
    //日志操作类别
    public static final int LOG_OPERATION_TYPE_INCREASE = 101;//增加
    public static final int LOG_OPERATION_TYPE_DELETE = 102;//删除
    public static final int LOG_OPERATION_TYPE_UPDATE = 103;//修改
    public static final int LOG_OPERATION_TYPE_READ = 104;//读取
    public static final int LOG_OPERATION_TYPE_OTHER = 105;//其他
    //多指标监控规则取值方式17
    public static final int MULTI_MON_SET_VALUE = 17;
    //多指标topic
    public static final String TOPIC_INDEX_MULTI_RULE_CHANGED = "TOPIC_INDEX_MULTI_RULE_CHANGED";
}