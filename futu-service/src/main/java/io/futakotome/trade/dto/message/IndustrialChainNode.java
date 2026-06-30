package io.futakotome.trade.dto.message;

public class IndustrialChainNode {
    private Long nodeId;              // 节点ID
    private Long parentNodeId;        // 父节点ID (根节点为0)
    private Long layerSth;            // 节点层级(从1开始)
    private String name;               // 节点名称
    private Long plateId;             // 关联板块ID(可选)

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Long getParentNodeId() {
        return parentNodeId;
    }

    public void setParentNodeId(Long parentNodeId) {
        this.parentNodeId = parentNodeId;
    }

    public Long getLayerSth() {
        return layerSth;
    }

    public void setLayerSth(Long layerSth) {
        this.layerSth = layerSth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPlateId() {
        return plateId;
    }

    public void setPlateId(Long plateId) {
        this.plateId = plateId;
    }
}
