package io.futakotome.common;

public interface MessageCommon {
    //消费者组
    String RT_BASIC_QUO_CONSUMER_GROUP_INDIES = "rtbo_consumer_group_indies";
    String RT_BASIC_QUO_CONSUMER_GROUP_STOCK = "rtbo_consumer_group_stock";
    String RT_BASIC_QUO_CONSUMER_GROUP_PLATE = "rtbo_consumer_group_plate";
    String RT_KL_DAY_CONSUMER_GROUP = "rt_kld_consumer_group";
    String RT_KL_WEEK_CONSUMER_GROUP = "rt_klw_consumer_group";
    String RT_KL_MONTH_CONSUMER_GROUP = "rt_klm_consumer_group";
    String RT_KL_QUARTER_CONSUMER_GROUP = "rt_klq_consumer_group";
    String RT_KL_YEAR_CONSUMER_GROUP = "rt_kly_consumer_group";
    String RT_KL_MIN_1_CONSUMER_GROUP = "rt_klm1_consumer_group";
    String RT_KL_MIN_3_CONSUMER_GROUP = "rt_klm3_consumer_group";
    String RT_KL_MIN_5_CONSUMER_GROUP = "rt_klm5_consumer_group";
    String RT_KL_MIN_15_CONSUMER_GROUP = "rt_klm15_consumer_group";
    String RT_KL_MIN_30_CONSUMER_GROUP = "rt_klm30_consumer_group";
    String RT_KL_MIN_60_CONSUMER_GROUP = "rt_klm60_consumer_group";
    String RT_TICKER_CONSUMER_GROUP = "rt_ticker_consumer_group";
    String RT_TIMESHARE_CONSUMER_GROUP = "rt_timeshare_consumer_group";
    String RT_BROKER_CONSUMER_GROUP = "rt_broker_consumer_group";
    String NOTIFY_CONSUMER_GROUP = "notify_consumer_group";
    String MARKET_STATE_CONSUMER_GROUP = "market_state_consumer_group";
    String CAPITAL_FLOW_CONSUMER_GROUP = "capital_flow_consumer_group";
    String REHAB_CONSUMER_GROUP = "rehab_consumer_group";
    String HISTORY_KL_DETAIL_CONSUMER_GROUP = "history_k_detail_consumer_group";
    //经纪人队列
    String RT_BROKER_TOPIC = "rt_broker_topic";
    //分时
    String RT_TIMESHARE_TOPIC = "rt_timeshare_topic";
    //逐笔
    String RT_TICKER_TOPIC = "rt_ticker_topic";
    //指数报价
    String RT_BASIC_QUO_TOPIC_INDEX = "rtbo_topic_index";
    //正股报价
    String RT_BASIC_QUO_TOPIC_STOCK = "rtbo_topic_stock";
    //板块报价
    String RT_BASIC_QUO_TOPIC_PLATE = "rtbo_topic_plate";
    //K
    String RT_KL_DAY_TOPIC = "rt_kld_topic";
    String RT_KL_WEEK_TOPIC = "rt_klw_topic";
    String RT_KL_MONTH_TOPIC = "rt_klm_topic";
    String RT_KL_QUARTER_TOPIC = "rt_klq_topic";
    String RT_KL_YEAR_TOPIC = "rt_kly_topic";
    String RT_KL_MIN_1_TOPIC = "rt_klm1_topic";
    String RT_KL_MIN_3_TOPIC = "rt_klm3_topic";
    String RT_KL_MIN_5_TOPIC = "rt_klm5_topic";
    String RT_KL_MIN_15_TOPIC = "rt_klm15_topic";
    String RT_KL_MIN_30_TOPIC = "rt_klm30_topic";
    String RT_KL_MIN_60_TOPIC = "rt_klm60_topic";
    //通知
    String NOTIFY_TOPIC = "notify_topic";
    //全局市场状态
    String MARKET_STATE_TOPIC = "market_state_topic";
    String CAPITAL_FLOW_TOPIC = "capital_flow_topic";
    //复权因子
    String REHAB_TOPIC = "rehab_topic";
    //历史K线额度使用明细
    String HISTORY_KL_DETAIL_TOPIC = "history_k_detail_topic";
}
