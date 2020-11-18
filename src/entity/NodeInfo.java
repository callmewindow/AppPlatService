package entity;

import entity.databaseEntity.Node;

/**
 * Created by winter on 2014/7/20.
 */
public class NodeInfo {
    private String id;
    private String name;
    private String nodeType;
    private Integer nodeOrder;
    private String parentId;
    private String rootId;
    private Boolean isRoot;
    private Boolean leaf;
    private String dataId;
    private String nodeFilePath;
    private boolean isOK ;
    private String errMsg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public Integer getNodeOrder() {
        return nodeOrder;
    }

    public void setNodeOrder(Integer nodeOrder) {
        this.nodeOrder = nodeOrder;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public Boolean getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(Boolean isRoot) {
        this.isRoot = isRoot;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getNodeFilePath() {
        return nodeFilePath;
    }

    public void setNodeFilePath(String nodeFilePath) {
        this.nodeFilePath = nodeFilePath;
    }

    public boolean getIsOK() {
        return isOK;
    }

    public void setNode( Node node){
        this.setId(node.getId());
        this.setName(node.getName());
        this.setNodeType(node.getNodeType());
        this.setNodeOrder(node.getNodeOrder());
        this.setParentId(node.getParentId());
        this.setRootId(node.getRootId());
        this.setIsRoot(node.getIsRoot());
        this.setLeaf(node.getLeaf());
        this.setDataId(node.getDataId());
        this.setNodeFilePath(node.getNodeFilePath());
    }

    public void setIsOK(boolean isOK) {
        this.isOK = isOK;
    }
    public String getErrMsg() {
        return errMsg;
    }
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
