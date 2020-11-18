package service;

/**
 * 管理方案书的各个节点
 * Created by yezhang989@163.com on 14-5-30.
 */
import buaa.edu.global.IDGeneratorUtil;
import dao.AllDAO;
import entity.*;
import buaa.edu.global.AppPlatConstant;
import buaa.edu.sortEntity.CompSolution;
import entity.databaseEntity.Node;

import java.util.*;

public class SolutionService {
    private AllDAO allDAO;
    private AllServices allServices;
    public AllDAO getAllDAO() {
        return allDAO;
    }
    public void setAllDAO(AllDAO allDAOs) {
        this.allDAO = allDAOs;
    }
    public AllServices getAllServices() {
        return allServices;
    }
    public void setAllServices(AllServices allServices) {
        this.allServices = allServices;
    }

    public List<Node> getChildrenNode(String id ){
        List<Node> nodes = allDAO.getNodeDAO().findByParentId(id);
        for(int i = 0;i<nodes.size();i++){
            if(nodes.get(i).getId().equals(nodes.get(i).getParentId())){
                nodes.remove(i);
            }
        }
        return nodes;
    }
    public Node getRootNode(String nodeId){
        List<Object> objectList = allDAO.getNodeDAO().findByDataId(nodeId);
        Node node = (Node)objectList.get(0);
        return node;
    }

    /**
     * 向树中添加+对应的节点，并且在文件结构中创建对应的文件夹
     * @param node
     * @return
     */
    public NodeInfo addNode(String userId,Node node){
        String type = node.getNodeType();
        if(type.equals(AppPlatConstant.NODETYPE_SOLUTION)){  //添加方案节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.SOLUTION_PRE_ID));
        }else if(type.equals(AppPlatConstant.NODETYPE_SATELLITE)){ //添加卫星节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.SATELLITE_PRE_ID));
        }else if(type.equals(AppPlatConstant.NODETYPE_GROUNDSTATION)){  //添加地面站节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.GROUNDSTATION_PRE_ID));
        }else if(type.equals(AppPlatConstant.NODETYPE_SENSOR)){   //添加传感器节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.SENSOR_PRE_ID));
        }else if(type.equals(AppPlatConstant.NODETYPE_ATTITUDEDATA)){   //添加姿态数据节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.ATTITUDEDATA_PRE_ID));
        }else if(type.equals(AppPlatConstant.NODETYPE_ORBITDATA)){   //添加轨道数据节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.ORBITDATA_PRE_ID));
        }else if(type.equals(AppPlatConstant.NODETYPE_STRUCTURE)){   //添加结构节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.STRUCTURE_PRE_ID));
        }else if(type.equals(AppPlatConstant.NODETYPE_COVER_ANALYSIS)){   //添加覆盖分析节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.COVER_ANALYSIS_PRE_ID));
        }else if(type.equals(AppPlatConstant.NODETYPE_DATATRANS_ANALYSIS)){   //添加数传分析节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.DATATRANS_ANALYSIS_PRE_ID));
        }else if(type.equals(AppPlatConstant.NODETYPE_OVERSTATION_ANALYSIS)){   //添加数传分析节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.OVERSTATION_ANALYSIS_PRE_ID));
        }else if(type.equals(AppPlatConstant.NODETYPE_CHAIN_ANALYSIS)){   //添加数传分析节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.CHAIN_ANALYSIS_PRE_ID));
        }else if(type.equals(AppPlatConstant.NODETYPE_SKY_COVER_POINT)){   //添加数传分析节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.SKY_COVER_POINT_PRE_ID));
        }else if(type.equals(AppPlatConstant.NODETYPE_SKY_COVER_REGIN)){   //添加数传分析节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.SKY_COVER_REGIN_PRE_ID));
        }else if(type.equals(AppPlatConstant.NODETYPE_SENSOR)){   //添加数传分析节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.SENSOR_PRE_ID));
        }
        Node parentNode = allDAO.getNodeDAO().findById( node.getParentId());
        String parentPath = parentNode.getNodeFilePath();
        String nodePath = parentPath +"/"+node.getId();
        node.setNodeFilePath(nodePath);
        allDAO.getNodeDAO().save(node);
        //云存储操作
        Boolean isSave = allServices.getServerStoreService().createFolder(nodePath);
        NodeInfo nodeInfo = new NodeInfo();
        nodeInfo.setNode(node);
        nodeInfo.setIsOK(isSave);
        if(!nodeInfo.getIsOK()){
            allDAO.getNodeDAO().delete(node);
        }
        return nodeInfo;
    }

    public Node findNodeById(String nodeId){
       return allDAO.getNodeDAO().findById(nodeId);
    }

    //更新方案的名称
    public Boolean updateNode(Node node){
        Boolean isUpdateSuccess = false;
        Node oldNode = allDAO.getNodeDAO().findById(node.getId());
        if(node != null){
            oldNode.setName(node.getName());
            try{
                allDAO.getNodeDAO().merge(oldNode);
            }catch (Exception e){
                return false;
            }
            isUpdateSuccess = true;
        }
        return  isUpdateSuccess;
    }
    public NodeInfo deleteNode(String userId,Node node) {
        Boolean isDel = false;
        Node delNode = allDAO.getNodeDAO().findById(node.getId());
        if (delNode != null) {
            isDel = allServices.getServerStoreService().deleteDir(delNode.getNodeFilePath());
            if (isDel) {
                allDAO.getNodeDAO().delete(delNode);
            }
        }
        NodeInfo nodeInfo = new NodeInfo();
//        nodeInfo.setNode(delNode);
        nodeInfo.setIsOK(isDel);
        return nodeInfo;
    }
}
