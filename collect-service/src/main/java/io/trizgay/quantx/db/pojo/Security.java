package io.trizgay.quantx.db.pojo;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.templates.annotations.Column;
import io.vertx.sqlclient.templates.annotations.ParametersMapped;
import io.vertx.sqlclient.templates.annotations.RowMapped;
import io.vertx.sqlclient.templates.annotations.TemplateParameter;

import java.time.LocalDateTime;

@DataObject(publicConverter = false, generateConverter = true)
@ParametersMapped
@RowMapped
public class Security {
    private Long id;

    @TemplateParameter(name = "name")
    @Column(name = "name")
    private String name;

    @TemplateParameter(name = "lotSize")
    @Column(name = "lot_size")
    private Integer lotSize;

    @TemplateParameter(name = "secType")
    @Column(name = "sec_type")
    private Integer securityType;

    @TemplateParameter(name = "listTime")
    @Column(name = "list_time")
    private LocalDateTime listedTime;

    @TemplateParameter(name = "deListing")
    @Column(name = "de_listing")
    private Integer isOut;

    @TemplateParameter(name = "exchangeType")
    @Column(name = "exchange_type")
    private Integer exchangeType;

    @TemplateParameter(name = "identity")
    @Column(name = "identity")
    private String outerId;

    @TemplateParameter(name = "market")
    @Column(name = "market")
    private Integer market;

    @TemplateParameter(name = "code")
    @Column(name = "code")
    private String code;

    public Security() {
    }

    public Security(String name, Integer lotSize, Integer securityType, LocalDateTime listedTime, Integer isOut, Integer exchangeType, String outerId, Integer market, String code) {
        this.name = name;
        this.lotSize = lotSize;
        this.securityType = securityType;
        this.listedTime = listedTime;
        this.isOut = isOut;
        this.exchangeType = exchangeType;
        this.outerId = outerId;
        this.market = market;
        this.code = code;
    }

    public Security(JsonObject jsonObject) {
        SecurityConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        SecurityConverter.toJson(this, jsonObject);
        return jsonObject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLotSize() {
        return lotSize;
    }

    public void setLotSize(Integer lotSize) {
        this.lotSize = lotSize;
    }

    public Integer getSecurityType() {
        return securityType;
    }

    public void setSecurityType(Integer securityType) {
        this.securityType = securityType;
    }

    public Integer getIsOut() {
        return isOut;
    }

    public void setIsOut(Integer isOut) {
        this.isOut = isOut;
    }

    public Integer getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(Integer exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getOuterId() {
        return outerId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public LocalDateTime getListedTime() {
        return listedTime;
    }

    public void setListedTime(LocalDateTime listedTime) {
        this.listedTime = listedTime;
    }

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Security{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lotSize=" + lotSize +
                ", securityType=" + securityType +
                ", listedTime=" + listedTime +
                ", isOut=" + isOut +
                ", exchangeType=" + exchangeType +
                ", outerId='" + outerId + '\'' +
                ", market=" + market +
                ", code='" + code + '\'' +
                '}';
    }
}
