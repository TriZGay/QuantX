package io.futakotome.trade.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName(value = "t_ipo_cn_ex_winning")
public class IpoCnExWinningDto implements Serializable {
    @TableId
    private Long id;

    private String winningName;

    private String winningInfo;

    private Long ipoCnId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWinningName() {
        return winningName;
    }

    public void setWinningName(String winningName) {
        this.winningName = winningName;
    }

    public String getWinningInfo() {
        return winningInfo;
    }

    public void setWinningInfo(String winningInfo) {
        this.winningInfo = winningInfo;
    }

    public Long getIpoCnId() {
        return ipoCnId;
    }

    public void setIpoCnId(Long ipoCnId) {
        this.ipoCnId = ipoCnId;
    }
}