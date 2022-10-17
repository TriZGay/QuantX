package io.futakotome.quantx.collect.domain.ipo;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_ipo_ex_hk")
public class IpoExHk {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_ipo_ex_hk_id_seq")
    @Column(name = "id")
    private Long id;
    //最低发售价
    private Double ipoPriceMin;
    //最高售价
    private Double ipoPriceMax;
    //上市价
    private Double listPrice;
    //每手股数
    private Integer lotSize;
    //入场费
    private Double entrancePrice;
    //是否为认购状态
    private Integer isSubscribeStatus;
    //截至认购日期字符串
    private String applyEndTime;
    //截至认购日期时间戳
    private LocalDateTime applyEndTimeStamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getIpoPriceMin() {
        return ipoPriceMin;
    }

    public void setIpoPriceMin(Double ipoPriceMin) {
        this.ipoPriceMin = ipoPriceMin;
    }

    public Double getIpoPriceMax() {
        return ipoPriceMax;
    }

    public void setIpoPriceMax(Double ipoPriceMax) {
        this.ipoPriceMax = ipoPriceMax;
    }

    public Double getListPrice() {
        return listPrice;
    }

    public void setListPrice(Double listPrice) {
        this.listPrice = listPrice;
    }

    public Integer getLotSize() {
        return lotSize;
    }

    public void setLotSize(Integer lotSize) {
        this.lotSize = lotSize;
    }

    public Double getEntrancePrice() {
        return entrancePrice;
    }

    public void setEntrancePrice(Double entrancePrice) {
        this.entrancePrice = entrancePrice;
    }

    public Integer getIsSubscribeStatus() {
        return isSubscribeStatus;
    }

    public void setIsSubscribeStatus(Integer isSubscribeStatus) {
        this.isSubscribeStatus = isSubscribeStatus;
    }

    public String getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(String applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    public LocalDateTime getApplyEndTimeStamp() {
        return applyEndTimeStamp;
    }

    public void setApplyEndTimeStamp(LocalDateTime applyEndTimeStamp) {
        this.applyEndTimeStamp = applyEndTimeStamp;
    }
}
