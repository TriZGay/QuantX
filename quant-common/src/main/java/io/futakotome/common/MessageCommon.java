package io.futakotome.common;

public interface MessageCommon {
    //消费者组
    String RT_KL_DAY_CONSUMER_GROUP = "rt_kld_consumer_group";
    String RT_KL_WEEK_CONSUMER_GROUP = "rt_klw_consumer_group";
    String RT_KL_MONTH_CONSUMER_GROUP = "rt_klm_consumer_group";
    String RT_KL_QUARTER_CONSUMER_GROUP = "rt_klq_consumer_group";
    String RT_KL_YEAR_CONSUMER_GROUP = "rt_kly_consumer_group";
    String RT_KL_MIN_1_CONSUMER_GROUP = "rt_klm1_consumer_group";
    String RT_KL_MIN_1_CONSUMER_GROUP_STREAM = "rt_klm1_consumer_group_stream";
    String RT_KL_MIN_3_CONSUMER_GROUP = "rt_klm3_consumer_group";
    String RT_KL_MIN_5_CONSUMER_GROUP = "rt_klm5_consumer_group";
    String RT_KL_MIN_15_CONSUMER_GROUP = "rt_klm15_consumer_group";
    String RT_KL_MIN_30_CONSUMER_GROUP = "rt_klm30_consumer_group";
    String RT_KL_MIN_60_CONSUMER_GROUP = "rt_klm60_consumer_group";
    String HISTORY_KL_MIN_1_CONSUMER_GROUP = "history_klm1_consumer_group";
    //todo 指标 should be removed
    String RT_EMA5_TOPIC = "rk_ema5_topic";
    String RT_EMA5_CONSUMER_GROUP_STREAM = "rk_ema5_consumer_group_stream";
    //实时K
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
    //历史K
    String HISTORY_KL_DAY_TOPIC = "history_kld_topic";
    String HISTORY_KL_WEEK_TOPIC = "history_klw_topic";
    String HISTORY_KL_MONTH_TOPIC = "history_klm_topic";
    String HISTORY_KL_QUARTER_TOPIC = "history_klq_topic";
    String HISTORY_KL_YEAR_TOPIC = "history_kly_topic";
    String HISTORY_KL_MIN_1_TOPIC = "history_klm1_topic";
    String HISTORY_KL_MIN_3_TOPIC = "history_klm3_topic";
    String HISTORY_KL_MIN_5_TOPIC = "history_klm5_topic";
    String HISTORY_KL_MIN_15_TOPIC = "history_klm15_topic";
    String HISTORY_KL_MIN_30_TOPIC = "history_klm30_topic";
    String HISTORY_KL_MIN_60_TOPIC = "history_klm60_topic";
}
