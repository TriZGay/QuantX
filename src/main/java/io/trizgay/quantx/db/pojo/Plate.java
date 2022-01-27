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

    @TemplateParameter(name = "plate")
    @Column(name = "plate")
    private String plate;

    @TemplateParameter(name = "name")
    @Column(name = "plate_name")
    private String name;

    @TemplateParameter(name = "type")
    @Column(name = "plate_type")
    private Integer type;

    public Plate() {
    }

    public Plate(String plate, String name, Integer type) {
        this.plate = plate;
        this.name = name;
        this.type = type;
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

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
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

    @Override
    public String toString() {
        return "Plate{" +
                "id=" + id +
                ", plate='" + plate + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
