package io.futakotome.common;

public interface MessageCommon {
    //消费者组
    String RT_BASIC_QUO_CONSUMER_GROUP_INDIES = "rtbo_consumer_group_indies";
    String RT_BASIC_QUO_CONSUMER_GROUP_STOCK = "rtbo_consumer_group_stock";
    String RT_KL_DAY_CONSUMER_GROUP = "rt_kld_consumer_group";
    String RT_KL_MIN_5_CONSUMER_GROUP = "rt_klm5_consumer_group";
    String RT_TICKER_CONSUMER_GROUP = "rt_ticker_consumer_group";
    String RT_TIMESHARE_CONSUMER_GROUP = "rt_timeshare_consumer_group";
    String RT_BROKER_CONSUMER_GROUP = "rt_broker_consumer_group";
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
    //日K
    String RT_KL_DAY_TOPIC = "rt_kld_topic";
    //5分K
    String RT_KL_MIN_5_TOPIC = "rt_klm5_topic";
}
