package service;

import buaa.edu.global.AppPlatConstant;
import buaa.edu.global.IDGeneratorUtil;
import dao.AllDAO;
import entity.*;
import entity.databaseEntity.Node;
import entity.databaseEntity.PersonalTask;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by winter on 2014/9/20.
 */
public class PerTaskCommonService {
    private AllDAO allDAO;
    public AllDAO getAllDAO() {
        return allDAO;
    }
    public void setAllDAO(AllDAO allDAOs) {
        this.allDAO = allDAOs;
    }
    public NodeInfo deleteNode(Node node) {
        Node newNode = allDAO.getNodeDAO().findById(node.getId());
        allDAO.getNodeDAO().delete(newNode);
        NodeInfo nodeInfo = new NodeInfo();
        nodeInfo.setIsOK(true);
        return nodeInfo;
    }
    public NodeInfo addNode( String userId, Node node,String perTaskId){
        String type = node.getNodeType();
        if(type.equals(AppPlatConstant.NODETYPE_TASK)){  //添加方案节点
            String nodeId = IDGeneratorUtil.getUUID(AppPlatConstant.TASK_PRE_ID);
            node.setId(nodeId);
            node.setParentId(nodeId);
            node.setRootId(nodeId);
            node.setDataId(perTaskId);//标识这个树中的根节点所属的个人任务
        }else if(type.equals(AppPlatConstant.NODETYPE_SATELLITE)){ //添加卫星节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.SATELLITE_PRE_ID));
        }else if(type.equals(AppPlatConstant.NODETYPE_SENSOR)){ //添加传感器节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.SENSOR_PRE_ID));
        }else if(type.equals(AppPlatConstant.NODETYPE_PLANET_COVER_POINT)){ //添加传感器节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.PLANET_COVER_POINT_PRE_ID));
        }else if(type.equals(AppPlatConstant.NODETYPE_PLANET_COVER_SQUARE)){ //添加传感器节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.PLANET_COVER_SQUARE_PRE_ID));
        }else if(type.equals(AppPlatConstant.NODETYPE_PLANET_COVER_GLOBAL)){ //添加传感器节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.PLANET_COVER_GLOBAL_PRE_ID));
        }else if(type.equals(AppPlatConstant.NODETYPE_GROUNDSTATION)){ //添加传感器节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.GROUNDSTATION_PRE_ID));
        }else if(type.equals(AppPlatConstant.NODETYPE_SKY_COVER_REGIN)){ //添加传感器节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.SKY_COVER_REGIN_PRE_ID));
        }else if(type.equals(AppPlatConstant.NODETYPE_SKY_COVER_POINT)){ //添加传感器节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.SKY_COVER_POINT_PRE_ID));
        }else if(type.equals(AppPlatConstant.NODETYPE_SENSOR)){ //添加传感器节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.SENSOR_PRE_ID));
        }else if(type.equals(AppPlatConstant.NODETYPE_CHAIN_ANALYSIS)){ //添加传感器节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.CHAIN_ANALYSIS_PRE_ID));
        }
        allDAO.getNodeDAO().save(node);
        NodeInfo nodeInfo = new NodeInfo();
        nodeInfo.setNode(node);
        return nodeInfo;
    }
     public boolean  updatePersonalTaskByTaskNode(Node node){
         Boolean bool = true;
         if(node.getDataId() != null){
             PersonalTask personalTask = allDAO.getPersonalTaskDAO().findById(node.getDataId());
             if(personalTask != null){
                 personalTask.setPerTaskName(node.getName());
                 try {
                     allDAO.getPersonalTaskDAO().merge(personalTask);
                 } catch (Exception e) {
                     bool = false;
                     e.printStackTrace();
                 }
             }
         }
         return bool;
     }
    public PersonalTask findPersonaltaskById(String perTaskId){
        return allDAO.getPersonalTaskDAO().findById(perTaskId);
    }
    public String addPerTask(String userId,String taskName, String taskType){
        PersonalTask personalTask = new PersonalTask();
        personalTask.setTaskType(taskType);
        personalTask.setIsSaved(AppPlatConstant.IS_PER_TASK_SAVED_NOT);
        personalTask.setPerTaskName(taskName);
        personalTask.setUserId(userId);
        personalTask.setTaskDirPath("");
        personalTask.setRecentlyModified(new Timestamp(System.currentTimeMillis()));
        //暂时不保存路径
        try {
            allDAO.getPersonalTaskDAO().save(personalTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  personalTask.getPerTaskId();
    }
     public Boolean mergePerTask(String nodeId,String dirPath){
         Node node = allDAO.getNodeDAO().findById(nodeId);
         PersonalTask personalTask = allDAO.getPersonalTaskDAO().findById(node.getDataId());
         node.setNodeFilePath(dirPath);
         personalTask.setTaskDirPath(dirPath);
         try {
             allDAO.getNodeDAO().merge(node);
             allDAO.getPersonalTaskDAO().merge(personalTask);
         } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
         return true;
     }

//    public Boolean deletePerTask(String userId, String perTaskName) {
//        Boolean isSaved = false;
//        List<PersonalTask> personalTasks = allDAO.getPersonalTaskDAO().findByPerTaskName(perTaskName);
//        PersonalTask personalTask = new PersonalTask();
//        if(personalTasks!=null&&personalTasks.size()>0){
//            for(int i = 0;i <personalTasks.size(); i ++){
//                if(personalTasks.get(i).getUserId().equals(userId)&&personalTasks.get(i).getIsSaved()==0){
//                    personalTask = personalTasks.get(i);
//                    break;
//                }
//            }
//            List<Node> nodes = allDAO.getNodeDAO().findByDataId(personalTask.getPerTaskId());
//            Node node = new Node();
//            if(nodes != null && nodes.size() > 0){
//                node = nodes.get(0);
//            }
//            try {
//                allDAO.getNodeDAO().delete(node);
//                allDAO.getPersonalTaskDAO().delete(personalTask);
//            } catch (Exception e) {
//                isSaved = true;
//                e.printStackTrace();
//            }
//
//        }
//        return  isSaved;
//    }
public List<PersonalTask> findtaskNameByUserIdAndType(String userId,String taskType) {
    List<PersonalTask> personalTasks = allDAO.getPersonalTaskDAO().findByUserId(userId);
    for(int i =0;i < personalTasks.size();i++){
        if(!personalTasks.get(i).getTaskType().equals(taskType)){
            personalTasks.remove(i);
            i--;
        }
    }
    return personalTasks;
}
    public List<PersonalTask> findtaskNameByUserId(String userId) {
        List<PersonalTask> personalTasks = allDAO.getPersonalTaskDAO().findByUserId(userId);
        return personalTasks;
    }

    public List<SaveNode> findNameByParentId(String nodeId) {
        List<Node> childNodes = allDAO.getNodeDAO().findByParentId(nodeId);
        List<SaveNode> saveNodes = new ArrayList<SaveNode>();
        for( int i = 0;i < childNodes.size(); i++){
            if(!childNodes.get(i).getId().equals(childNodes.get(i).getParentId())){
                SaveNode saveNode = new SaveNode();
                saveNode.setName(childNodes.get(i).getName());
                saveNodes.add(saveNode);
            }
        }
        return saveNodes;
    }
}
