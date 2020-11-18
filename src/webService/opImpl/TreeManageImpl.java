package webService.opImpl;

import buaa.edu.sortEntity.CompAsyncNode;
import entity.databaseEntity.Node;
import webService.operator.ITreeManage;
import entity.NodeInfo;
import service.AllServices;


import java.util.Collections;
import java.util.List;
public class TreeManageImpl implements ITreeManage {
    private AllServices allServices;
    @Override
    public List<Node> getTreeNodes(String id,String nodeId) {
        List<Node> nodes = allServices.getSolutionService().getChildrenNode(id);
        CompAsyncNode cmt = new CompAsyncNode();
        Collections.sort(nodes, cmt);
        return nodes;
    }
    @Override
    public Node getRootNode(String id) {
        Node node = allServices.getSolutionService().getRootNode(id);
        return node;
    }
    @Override
    public NodeInfo addTreeNode(String userId, Node node) {
        NodeInfo nodeInfo = allServices.getSolutionService().addNode(userId,node);
        return nodeInfo;
    }
    @Override
    public Boolean updateTreeNode(Node node) {
        Boolean isUpdateSuccess = false;
        isUpdateSuccess = allServices.getSolutionService().updateNode(node);
        return isUpdateSuccess;
    }
    @Override
    public NodeInfo deleteTreeNode(String userId,Node node) {
        NodeInfo nodeInfo = allServices.getSolutionService().deleteNode(userId,node);
        return nodeInfo;
    }

    @Override
    public Boolean processSubmit(String currentProcess) {
        return null;
    }

    @Override
    public List<String> getAllSensorsBySolutionId(String solutionId) {
        return allServices.getTaskService().getAllSensorsBySolutionId(solutionId);
    }

    @Override
    public List<Node> getCoverAnaNodeBySolutionId(String solutionId) {
        return allServices.getTaskService().getCoverAnaNodeBySolutionId(solutionId);
    }

    public AllServices getAllServices() {
        return allServices;
    }
    public void setAllServices(AllServices allServices) {
        this.allServices = allServices;
    }
}
