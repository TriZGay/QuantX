package io.futakotome.trade.dto.message;

public class PlateInfoContent {
    private CommonSecurity plate;
    private String name;
    private Integer plateType;

    public CommonSecurity getPlate() {
        return plate;
    }

    public void setPlate(CommonSecurity plate) {
        this.plate = plate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlateType() {
        return plateType;
    }

    public void setPlateType(Integer plateType) {
        this.plateType = plateType;
    }
}
