package webService.opImpl;

import entity.collaborativeDesignEntity.*;
import entity.databaseEntity.Node;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import service.AllServices;
import webService.operator.ICollaDesignManage;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by winter on 2014/11/29.
 */
public class CollaDesignManage implements ICollaDesignManage {
    private AllServices allServices;
    @Override
     public String getCachedSceneData(String userId, String taskId, String solutionId,String nodeId, String toolType) {
        return allServices.getCollaDesignService().getCachedSceneData(userId,solutionId,taskId,nodeId,toolType);
    }

    @Override
    public String getLockIdList(String taskId, String solutionId,String nodeId) {
        return allServices.getCollaDesignService().getLockIdList(solutionId,taskId,nodeId);
    }

    @Override
    public void refreshSceneData(RefreshData data) {
        allServices.getCollaDesignService().refreshSceneData(data.getSolutionId(),data.getTaskId(),data.getNodeId(),data.getData(),data.getLockId());
    }

    @Override
    public String getGsAndSatData(String gsAndSatString) {//获取json数据
        try {
            JSONObject gsAndSatJson = new JSONObject(gsAndSatString);
            JSONArray dataList =  gsAndSatJson.getJSONArray("data");
            for(int i = 0; i < dataList.length(); i++){
                JSONObject jsonObject = (JSONObject)dataList.get(i);
                String parentPath = jsonObject .getString("parentPath");
                String id = jsonObject .getString("id");
                String filePath = "";
                boolean is_satellite = false;
                if(jsonObject.getString("type").equals("satellite")){
                     filePath = parentPath + "/" + id ;
                }
                else if(jsonObject.getString("type").equals("sensor")){
                    filePath = parentPath + "/" + id + ".json";
                    String fileContent = allServices.getServerStoreService().getFileByString(filePath);
                    JSONObject fileContentJson = new JSONObject(fileContent);
                    jsonObject.put("value",fileContent);
                    int lindex = parentPath.lastIndexOf("/");
                    parentPath = parentPath.substring(0, lindex);
                    filePath  = parentPath + "/" + fileContentJson.get("parentID");
                    is_satellite = true;
                }
                else
                {
                     filePath = parentPath + "/" + id + ".json";
                }

                String fileContent = allServices.getServerStoreService().getFileByString(filePath);
                JSONObject fileContentJson = new JSONObject(fileContent);
                if(is_satellite){
                    jsonObject.put("satellite",fileContent);
                }
                else{
                    jsonObject.put("value",fileContent);
                }
            }
            return gsAndSatJson.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void saveSceneDataByTimer() {
        allServices.getCollaDesignService().saveSceneDataByTimer();
    }

    public AllServices getAllServices() {
        return allServices;
    }
    public void setAllServices(AllServices allServices) {
        this.allServices = allServices;
    }

}
