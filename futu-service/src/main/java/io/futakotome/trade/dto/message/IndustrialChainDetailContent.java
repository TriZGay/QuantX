package io.futakotome.trade.dto.message;

import java.util.List;

public class IndustrialChainDetailContent {
    private Long chainId;                         // 产业链ID
    private Integer chainType;                       // 类型
    private String name;                           // 名称
    private List<IndustrialChainNode> nodeList;          // 节点列表(按层级分组)
    private List<InformationLink> informationList;       // 资讯链接

    public Long getChainId() {
        return chainId;
    }

    public void setChainId(Long chainId) {
        this.chainId = chainId;
    }

    public Integer getChainType() {
        return chainType;
    }

    public void setChainType(Integer chainType) {
        this.chainType = chainType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IndustrialChainNode> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<IndustrialChainNode> nodeList) {
        this.nodeList = nodeList;
    }

    public List<InformationLink> getInformationList() {
        return informationList;
    }

    public void setInformationList(List<InformationLink> informationList) {
        this.informationList = informationList;
    }
}
