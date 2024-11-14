package io.futakotome.analyze.domain;

import io.futakotome.analyze.controller.vo.MaRequest;
import io.futakotome.analyze.controller.vo.MaResponse;
import io.futakotome.analyze.mapper.KLineMapper;
import io.futakotome.analyze.mapper.MaNMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MaN {
    private final MaNMapper repository;

    private Integer market;
    private String code;
    private Integer rehabType;
    private Double maValue;
    private String updateTime;

    public MaN(MaNMapper repository) {
        this.repository = repository;
    }

    /**
     * 使用归档好的K线数据计算获取MA线
     */
    public List<MaResponse> maNDataUseArc(MaRequest maRequest) {
        switch (maRequest.getGranularity()) {
            case 1:
                return repository.queryMa5Common(maRequest, KLineMapper.KL_MIN_1_ARC_TABLE_NAME)
                        .stream().map(maDto -> new MaResponse(maDto.getMarket(), maDto.getCode(),
                                maDto.getRehabType(), maDto.getMaValue(), maDto.getUpdateTime()
                        ))
                        .collect(Collectors.toList());
            case 2:
                return null;
        }
        return null;
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

    public Integer getRehabType() {
        return rehabType;
    }

    public void setRehabType(Integer rehabType) {
        this.rehabType = rehabType;
    }

    public Double getMaValue() {
        return maValue;
    }

    public void setMaValue(Double maValue) {
        this.maValue = maValue;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
