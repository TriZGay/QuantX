package io.futakotome.trade.dto.message;

public class MacroDataPoint {
    private String dataTime;       //数据日期 "yyyy-MM-dd"
    private String releaseTime;    //公布日期 "yyyy-MM-dd HH:mm:ss"
    private Double value;          //公布值(已还原为原始值)
    private Double predictValue;   //预测值(已还原)
    private Double previousValue;  //前值(已还原)
    private Integer unitType;        //MacroDataUnitType, 单位类型

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getPredictValue() {
        return predictValue;
    }

    public void setPredictValue(Double predictValue) {
        this.predictValue = predictValue;
    }

    public Double getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(Double previousValue) {
        this.previousValue = previousValue;
    }

    public Integer getUnitType() {
        return unitType;
    }

    public void setUnitType(Integer unitType) {
        this.unitType = unitType;
    }
}
