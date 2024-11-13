package io.futakotome.trade.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@TableName(value = "t_ipo_us")
public class IpoUsDto implements Serializable {
    @TableId
    private Long id;

    private String name;

    private Integer market;

    private String code;

    private LocalDate listTime;

    private Double ipoPriceMin;

    private Double ipoPriceMax;

    private Long issueSize;

    private static final long serialVersionUID = 1L;

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

    public Object getMarket() {
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

    public LocalDate getListTime() {
        return listTime;
    }

    public void setListTime(LocalDate listTime) {
        this.listTime = listTime;
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

    public Long getIssueSize() {
        return issueSize;
    }

    public void setIssueSize(Long issueSize) {
        this.issueSize = issueSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpoUsDto ipoUsDto = (IpoUsDto) o;
        return code.equals(ipoUsDto.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}