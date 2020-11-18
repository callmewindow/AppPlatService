package webService.opImpl;

import buaa.edu.global.AppPlatConstant;
import entity.Struct;
import entity.databaseEntity.Node;
import entity.NodeInfo;
import entity.SaveNode;
import entity.databaseEntity.PersonalTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import service.AllServices;
import webService.operator.ICoverAnalysisManage;

import java.io.InputStream;
import java.io.Reader;
import java.util.List;

/**
 * Created by winter on 2014/9/20.
 */
public class CoverAnalysisManage implements ICoverAnalysisManage {
    private AllServices allServices;

    public AllServices getAllServices() {
        return allServices;
    }

    public void setAllServices(AllServices allServices) {
        this.allServices = allServices;
    }

    @Override
    public List<Node> getTreeNodes(String id, String targetNodeType) {
        return null;
    }

    @Override
    public NodeInfo addTreeNode(String userId, Node node) {
        NodeInfo nodeInfo = allServices.getCoverAnalysisService().addNode(userId,node);
        return nodeInfo;
    }

    @Override
    public Boolean updateTreeNode(Node node) {
        return null;
    }
    //TODO 保存文件 ，父路径、文件名称、数据
    @Override
    public Boolean saveDataTransFile(Struct s) {
        Boolean feedback = allServices.getCoverAnalysisService().saveDataTransFile(s);
        return feedback;
    }

    @Override
    public NodeInfo deleteTreeNode(String userId, Node node) {
        return null;
    }
    //TODO 获取所有任务
    @Override
    public List<PersonalTask> getTaskNameByUserId(String userId) {
        List<PersonalTask> personalTasks = allServices.getPerTaskCommonService().findtaskNameByUserIdAndType(userId, AppPlatConstant.PER_TASK_TYPE_COVER_ANALYSIS);
        return personalTasks;
    }

    @Override
    public String getTaskTree( String perTaskId){
        String tasktree = allServices.getCoverAnalysisService().getTaskTree(perTaskId);
        JSONArray dataList = null;
        try {
            dataList = new JSONArray(tasktree);
            JSONObject jsonObject = null;
            for(int i = 0;i < dataList.length();i++){
                for(int j = dataList.length()-1; j > i;j--){
                    if(allServices.getDataTransAnalysisService().compareJSONObject((JSONObject)dataList.get(j),(JSONObject)dataList.get(j-1)) < 0){
                        jsonObject = (JSONObject)dataList.get(j);
                        dataList.put(j,dataList.get(j-1));
                        dataList.put(j-1,jsonObject);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList.toString();
    }

    //获取文件内容
    @Override
    public String getFileByString(String path){
       return null;
    }

    @Override
    public List<SaveNode> getNameByParentId(String nodeId) {
        List<SaveNode> saveNodes = allServices.getPerTaskCommonService().findNameByParentId(nodeId);
        return saveNodes;
    }

    @Override
    public String tansferSchedulViewTable( String jsonstr){
        return  jsonstr;
    }

    @Override
    public String coverCompute( String jsonstr, int type){
        if(type == 1)
            return allServices.getCoverAnalysisService().coverCompute(jsonstr);
        else if(type == 2)
            return allServices.getCoverAnalysisService().skyCompute(jsonstr);

        return null;
    }

    @Override
    public String coverComputeExt(String jsonstr, int type){
        if(type == 1)
            return allServices.getCoverAnalysisService().coverCompute(jsonstr);
        else if(type == 2)
            return allServices.getCoverAnalysisService().skyCompute(jsonstr);

        return null;
    }

}
