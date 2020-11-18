package service;

import buaa.edu.global.AppPlatConstant;
import entity.collaborativeDesignEntity.*;
import entity.databaseEntity.Node;
import entity.databaseEntity.Task;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by winter on 2014/11/28.
 */
public class CollaDesignService {
    private static   List<CachedSceneData>  cachedSceneDataList = new ArrayList<CachedSceneData>();
    private static   AllServices allServices;

    public static List<CachedSceneData> getCachedSceneDataList() {
        return cachedSceneDataList;
    }

    public static void setCachedSceneDataList(List<CachedSceneData> cachedSceneDataList) {
        CollaDesignService.cachedSceneDataList = cachedSceneDataList;


    }

    public AllServices getAllServices() {
        return allServices;
    }

    public void setAllServices(AllServices allServices) {
        this.allServices = allServices;
    }

    public static String getCachedSceneData(String userId,String solutionId,String taskId,String nodeId,String toolType){
        Boolean isHasScene = false;
        if(cachedSceneDataList.size() > 0){
            //遍历所有保存的设计场景
            for(int i = 0; i < cachedSceneDataList.size();i++){
                ToolInfo toolInfo = cachedSceneDataList.get(i).getToolInfo();
                //如果该场景是当前任务下的结构设计场景，需要把用户isoperating设置为false，如果发现是跳转的那个场景再改为true
                if(toolInfo.getTaskId().equals(taskId)){
                    List<CollaUser> collaUsers = cachedSceneDataList.get(i).getCollaUserList();
                    for(int j = 0; j < collaUsers.size(); j++){
                        //将该任务下其它界面的数据缓存的isOperating状态置为false
                        if(collaUsers.get(j).getUserId().equals(userId)){
                            collaUsers.get(j).setIsOperating(false);
                            break;
                        }
                    }
                    //将该场景下该用户的状态改为活跃，即正在操作
                    if(toolInfo.getSolutionId().equals(solutionId)&&toolInfo.getNodeId().equals(nodeId)&&toolInfo.getToolType().equals(toolType)){
                        isHasScene = true;
                        Boolean isHasUser = false;
                        //找到CollaUser,改变isOperating属性的值
                        for(int j = 0; j < cachedSceneDataList.get(i).getCollaUserList().size(); j++){
                            if(cachedSceneDataList.get(i).getCollaUserList().get(j).getUserId().equals(userId)){
                                isHasUser = true;
                                cachedSceneDataList.get(i).getCollaUserList().get(j).setIsOperating(true);
                            }
                        }
                        //如果不包含当前的用户，也就是该用户是新加入协同论证的用户,则在用户组中加入该用户
                        if(!isHasUser){
                            CollaUser collaUser = new CollaUser(userId,true);
                            cachedSceneDataList.get(i).getCollaUserList().add(collaUser);
                        }
                        return cachedSceneDataList.get(i).getCachedSceneJsonData();
                    }
                }
            }
        }
        //当前缓存中没有这个结构设计场景，那么就新建场景，并且初始化值
        if(!isHasScene){
            CachedSceneData cachedSceneData = new CachedSceneData();
            //初始化toolInfo
            Node node = allServices.getSolutionService().findNodeById(nodeId);
            String  filePath = "";
            String sceneDataString = "";
            if(toolType.equals(AppPlatConstant.TOOL_TYPE_STRUCT_DESIGN)){
                 filePath = node.getNodeFilePath() + "/" + nodeId + ".json";
                 sceneDataString = allServices.getServerStoreService().getFileByString(filePath);
            }else if(toolType.equals(AppPlatConstant.TOOL_TYPE_ORBIT_DESIGN)){
                 filePath = node.getNodeFilePath() + "/";
                 sceneDataString = allServices.getOrbitFileService().getFileByString(filePath, nodeId);//nodeId是方案id
            }
            ToolInfo toolInfo = new ToolInfo(taskId,solutionId,toolType,nodeId,filePath);
            cachedSceneData.setToolInfo(toolInfo);
            //初始化collaUser
            CollaUser collaUser = new CollaUser(userId,true);
            List<CollaUser> collaUserList = new ArrayList<CollaUser>();
            collaUserList.add(collaUser);
            cachedSceneData.setCollaUserList(collaUserList);
//            JSONObject cachedSceneJsonData = null;
//            try {
//                cachedSceneJsonData = new JSONObject(sceneDataString);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            cachedSceneData.setCachedSceneJsonData(sceneDataString);
            cachedSceneDataList.add(cachedSceneData);
            return  sceneDataString;
        }
        return null;
    }

    public static String getLockIdList(String solutionId,String taskId,String nodeId){
        if(cachedSceneDataList.size() > 0){
            //遍历所有保存的设计场景
            for(int i = 0; i < cachedSceneDataList.size();i++){
                ToolInfo toolInfo = cachedSceneDataList.get(i).getToolInfo();
                //如果该场景是当前任务下的结构设计场景，需要把用户isoperating设置为false，如果发现是跳转的那个场景再改为true
                if(toolInfo.getSolutionId().equals(solutionId) && toolInfo.getTaskId().equals(taskId) && toolInfo.getNodeId().equals(nodeId)){
                    return cachedSceneDataList.get(i).getLockIdList();
                }
            }
        }
        return null;
    }

    public synchronized  static void refreshSceneData(String solutionId,String taskId,String nodeId,String data, String lockId){
        if(cachedSceneDataList.size() > 0) {
            //遍历所有保存的设计场景
            for (int i = 0; i < cachedSceneDataList.size(); i++) {
                ToolInfo toolInfo = cachedSceneDataList.get(i).getToolInfo();
                //如果该场景是当前任务下的结构设计场景，需要把用户isoperating设置为false，如果发现是跳转的那个场景再改为true
                if (toolInfo.getTaskId().equals(taskId) && toolInfo.getSolutionId().equals(solutionId) && toolInfo.getNodeId().equals(nodeId)) {
                    cachedSceneDataList.get(i).setCachedSceneJsonData(data);
                    cachedSceneDataList.get(i).setLockIdList(lockId);
                    cachedSceneDataList.get(i).setIsDirty(true);
                }
            }
        }
    }

    public void saveSceneDataByTimer(){
        if(cachedSceneDataList != null && cachedSceneDataList.size() > 0){
            for(int i = 0 ; i < cachedSceneDataList.size(); i++ ){
                    if(cachedSceneDataList.get(i).getIsDirty()){
                        ToolInfo toolInfo = cachedSceneDataList.get(i).getToolInfo();
                        if(toolInfo.getToolType().equals(AppPlatConstant.TOOL_TYPE_ORBIT_DESIGN)){
                            String orbitStringData = cachedSceneDataList.get(i).getCachedSceneJsonData().toString();
                            //利用solutionId查找该方案的所有孩子，在将场景数据保存到相应孩子的文件
                            String solutionId = toolInfo.getSolutionId();
                            allServices.getOrbitFileService().saveOrbitSceneFile(toolInfo.getNodeFilePath(), orbitStringData, solutionId);
                            cachedSceneDataList.get(i).setIsDirty(false);
                        }else if(toolInfo.getToolType().equals(AppPlatConstant.TOOL_TYPE_STRUCT_DESIGN)){
                            String orbitStringData = cachedSceneDataList.get(i).getCachedSceneJsonData().toString();
                            allServices.getServerStoreService().createFile(toolInfo.getNodeFilePath(),orbitStringData);
                            cachedSceneDataList.get(i).setIsDirty(false);
                        }
                       //如果场景信息被修改，那么需要更新数据库中任务的最近修改时间字段
                        Node node = allServices.getTaskService().getAllDAO().getNodeDAO().findById(toolInfo.getTaskId());
                        Task task = allServices.getTaskService().getAllDAO().getTaskDAO().findById(node.getDataId());
                        task.setRecentlyModified(new Timestamp(System.currentTimeMillis()));
                        allServices.getTaskService().getAllDAO().getTaskDAO().merge(task);
                    }
            }
        }
    }
}
