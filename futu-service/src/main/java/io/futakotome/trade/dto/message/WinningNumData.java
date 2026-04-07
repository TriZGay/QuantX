package io.futakotome.trade.dto.message;

public class WinningNumData {
    // 分组名
    private String winningName;
    // 中签号信息
    private String winningInfo;

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
}
