package io.trizgay.quantx.db.pojo;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.templates.annotations.Column;
import io.vertx.sqlclient.templates.annotations.ParametersMapped;
import io.vertx.sqlclient.templates.annotations.RowMapped;
import io.vertx.sqlclient.templates.annotations.TemplateParameter;

@DataObject(publicConverter = false, generateConverter = true)
@RowMapped
@ParametersMapped
public class Plate {
    private Long id;

    @TemplateParameter(name = "name")
    @Column(name = "plate_name")
    private String name;

    @TemplateParameter(name = "type")
    @Column(name = "plate_type")
    private Integer type;

    @TemplateParameter(name = "market")
    @Column(name = "market")
    private Integer market;

    @TemplateParameter(name = "code")
    @Column(name = "code")
    private String code;

    public Plate() {
    }

    public Plate(String name, Integer type, Integer market, String code) {
        this.name = name;
        this.type = type;
        this.market = market;
        this.code = code;
    }

    public Plate(String name, Integer market, String code) {
        this.name = name;
        this.market = market;
        this.code = code;
    }

    public Plate(JsonObject jsonObject) {
        PlateConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        PlateConverter.toJson(this, jsonObject);
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
        return "Plate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", market=" + market +
                ", code='" + code + '\'' +
                '}';
    }
}
