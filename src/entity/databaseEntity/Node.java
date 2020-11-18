package entity.databaseEntity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by winter on 2014/6/28.
 */
@XmlRootElement(name = "node")
public class Node {
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (dataId != null ? !dataId.equals(node.dataId) : node.dataId != null) return false;
        if (leaf != null ? !leaf.equals(node.leaf) : node.leaf != null) return false;
        if (isRoot != null ? !isRoot.equals(node.isRoot) : node.isRoot != null) return false;
        if (id != null ? !id.equals(node.id) : node.id != null) return false;
        if (name != null ? !name.equals(node.name) : node.name != null) return false;
        if (nodeOrder != null ? !nodeOrder.equals(node.nodeOrder) : node.nodeOrder != null) return false;
        if (nodeType != null ? !nodeType.equals(node.nodeType) : node.nodeType != null) return false;
        if (parentId != null ? !parentId.equals(node.parentId) : node.parentId != null) return false;
        if (rootId != null ? !rootId.equals(node.rootId) : node.rootId != null) return false;
        if (nodeFilePath != null ? !nodeFilePath.equals(node.nodeFilePath) : node.nodeFilePath != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (nodeType != null ? nodeType.hashCode() : 0);
        result = 31 * result + (nodeOrder != null ? nodeOrder.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (rootId != null ? rootId.hashCode() : 0);
        result = 31 * result + (isRoot != null ? isRoot.hashCode() : 0);
        result = 31 * result + (leaf != null ? leaf.hashCode() : 0);
        result = 31 * result + (dataId != null ? dataId.hashCode() : 0);
        result = 31 * result + (nodeFilePath != null ? nodeFilePath.hashCode() : 0);
        return result;
    }
}
