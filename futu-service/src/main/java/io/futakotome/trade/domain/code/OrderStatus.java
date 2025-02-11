package io.futakotome.trade.domain.code;

public enum OrderStatus {
    UN_SUBMITTED(0, "未提交"),
    UNKNOWN(-1, "未知状态"),
    WAITING_SUBMIT(1, "等待提交"),
    SUBMITTING(2, "提交中"),
    SUBMIT_FAILED(3, "提交失败，下单失败（已废弃）"),
    TIME_OUT(4, "处理超时，结果未知（已废弃）"),
    SUBMITTED(5, "已提交，等待成交"),
    FILLED_PART(10, "部分成交"),
    FILLED_ALL(11, "全部成交"),
    CANCELLING_PART(12, "正在撤单_部分(部分已成交，正在撤销剩余部分)（已废弃）"),
    CANCELLING_ALL(13, "正在撤单_全部（已废弃）"),
    CANCELLED_PART(14, "部分成交，剩余部分已撤单"),
    CANCELLED_ALL(15, "全部已撤单，无成交"),
    FAILED(21, "下单失败，服务拒绝"),
    DISABLED(22, "已失效"),
    DELETED(23, "已删除，无成交的订单才能删除"),
    FILL_CANCELLED(24, "成交被撤销（一般遇不到，意思是已经成交的订单被回滚撤销，成交无效变为废单）");

    private final Integer code;
    private final String name;

    OrderStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
