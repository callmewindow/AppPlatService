package webService.opImpl;

import buaa.edu.global.AppPlatConstant;
import buaa.edu.sortEntity.CompAsyncNode;
import entity.*;
import entity.databaseEntity.Node;
import entity.databaseEntity.PersonalTask;
import webService.operator.IOrbitFileManage;
import service.AllServices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.Collections;
import java.util.List;

/**
 * Created by winter on 2014/8/23.
 */
public class OrbitFileManage implements IOrbitFileManage {
    private AllServices allServices;
    public AllServices getAllServices() {
        return allServices;
    }
    public void setAllServices(AllServices allServices) {
        this.allServices = allServices;
    }
    //创建文件夹
    @Override
    public Boolean saveOrbit(Struct s) {
        Boolean feedback = allServices.getOrbitFileService().saveOrbit(s);
        return feedback;
    }
    //删除卫星文件
    @Override
    public Boolean deleteOrbit(Struct s) {
        Boolean feedback = allServices.getOrbitFileService().deleteOrbit(s);
        return feedback;
    }
    //删除场景文件夹
    @Override
    public Boolean deleteSceneDir(Struct s) {
        Boolean feedback = allServices.getOrbitFileService().deleteSceneDir(s);
        return feedback;
    }
    @Override
    public List<Node> getTreeNodes(String id) {
        List<Node> nodes = allServices.getOrbitFileService().getChildrenNode(id);
        CompAsyncNode cmt = new CompAsyncNode();
        Collections.sort(nodes, cmt);
        return nodes;
    }

    @Override
    public NodeInfo addTreeNode(String userId, Node node) {
        NodeInfo nodeInfo = allServices.getOrbitFileService().addNode(userId,node);
        return nodeInfo;
    }


    @Override
    public Boolean mergeTaskToDB(Struct s) {
       Boolean isSavedSuccess = allServices.getOrbitFileService().mergeTaskInfo(s);
        return  isSavedSuccess;
    }

    @Override
    public Boolean updateTreeNode(Node node) {
        Boolean isUpdateSuccess = false;
        isUpdateSuccess = allServices.getSolutionService().updateNode(node);
        isUpdateSuccess = allServices.getPerTaskCommonService().updatePersonalTaskByTaskNode(node);
        return isUpdateSuccess;
    }

//    @Override
//    public Boolean deletePerTaskInDB(String userId, String perTaskName) {
//        Boolean isDel = allServices.getOrbitFileService().deletePerTaskInDB( userId, perTaskName);
//        return isDel;
//    }

    @Override
    public NodeInfo deleteTreeNode(String userId, Node node) {
        NodeInfo nodeInfo = allServices.getOrbitFileService().deleteSatelliteNode(userId,node);
        return nodeInfo;
    }
    //从DLL文件中获取行星位置
    @Override
    public String getPlanetPosition(int y, int m, int d, int h, int mi, int sec, int a, int aT, int b, int bT){
        String feedback = allServices.getOrbitFileService().getPlanetPosition(y, m, d, h, mi, sec, a, aT, b, bT);
        return feedback;
    }
    //从Dll文件中获得卫星转移时的起始速度和到达速度
    @Override
    public String getSatTransferVelocity(double [] R1, double [] R2, double FTIME){
        String feedback = allServices.getOrbitFileService().getSatTransferVelocity(R1, R2, FTIME);
        return feedback;
    }
    //获取文件内容
    @Override
    public String getFileByString(String path, String nodeId){
        String feedback = allServices.getOrbitFileService().getFileByString(path, nodeId);
        return feedback;
    }
    //获取卫星轨道文件
    @Override
    public List<SaveNode> getFilesNameWithoutExtension(String userId){
        List<SaveNode> fileNameList = allServices.getOrbitFileService().getChildFileNameList(userId);
        return fileNameList;
    }
    //获取辐射带的点
    @Override
    public String getRadBeltPoint(){
        String feedback = allServices.getOrbitFileService().getRadBeltPoint();
        return feedback;
    }
    //获取磁力线的点
    @Override
    public String getTrackPoints(int lineId){
        String feedback = allServices.getOrbitFileService().getTrackPoints(lineId);
        return feedback;
    }
    //指定能级的辐射带粒子通量
    @Override
    public double[][][] getRadBeltData(int degree){
        double [][][]feedback = allServices.getOrbitFileService().getRadBeltData(degree);
        return feedback;
    }
    @Override
    public List<PersonalTask> getTaskNameByUserId(String userId) {
        List<PersonalTask> personalTasks = allServices.getPerTaskCommonService().findtaskNameByUserIdAndType(userId, AppPlatConstant.PER_TASK_TYPE_ORBIT_DESIGN);
        return personalTasks;
    }

    @Override
    public List<SaveNode> getSatelliteNameByParentId(String nodeId) {
        List<SaveNode> saveNodes = allServices.getOrbitFileService().findSatelliteNameByTaskId(nodeId);
        return saveNodes;
    }
    @Override
    public List<Node> getAllNodesByRootId(String rootId) {
        return allServices.getOrbitFileService().getAllNodesByRootId(rootId);
    }

    @Override
    public Object addOrbitSection(String id, OrbitSection orbitSection) {
        return null;
    }

    @Override
    public Object getOrbitSection(String id) {
        return null;
    }

    @Override
    public Object updateOrbitSection(String id, OrbitSection orbitSection) {
        return null;
    }

    @Override
    public Object deleteOrbitSection(String id, OrbitSection orbitSection) {
        return null;
    }
}
