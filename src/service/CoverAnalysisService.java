package service;

import buaa.edu.global.AppPlatConstant;
import buaa.edu.global.SocketClient;
import dao.AllDAO;
import entity.Struct;
import entity.databaseEntity.Node;
import entity.NodeInfo;
import entity.databaseEntity.PersonalTask;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by winter on 2014/9/20.
 */
public class CoverAnalysisService {
    private AllServices allServices;
    private AllDAO allDAO;
    public AllServices getAllServices() {
        return allServices;
    }
    public void setAllServices(AllServices allServices) {
        this.allServices = allServices;
    }

    // 创建文件
    public boolean saveDataTransFile(Struct s){
        allServices.getServerStoreService().createFolder(s.getParentPath());
        allServices.getServerStoreService().createFile(s.getParentPath(), s.getFileName(), s.getData());
        return true;
    }

    public NodeInfo addNode( String userId, Node node){
        String type = node.getNodeType();
        NodeInfo nodeInfo;
        String perTaskId = null;
        if(type.equals(AppPlatConstant.NODETYPE_TASK)){  //添加方案节点
            perTaskId = allServices.getPerTaskCommonService().addPerTask(userId, node.getName(), AppPlatConstant.PER_TASK_TYPE_COVER_ANALYSIS);
            nodeInfo =  allServices.getPerTaskCommonService().addNode(userId,node,perTaskId);
            String dirPath = userId + "/" + AppPlatConstant.COVER_ANALYSIS+ "/" + nodeInfo.getId();
            allServices.getPerTaskCommonService().mergePerTask(nodeInfo.getId(),dirPath);
        }else { //添加卫星节点
            nodeInfo =  allServices.getPerTaskCommonService().addNode(userId,node,perTaskId);
        }
        return nodeInfo;
    }
    String getRootPath(){
        String path = "";
        try {
            path = this.getClass().getClassLoader().getResource("/").toURI().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return path;
    }
    public String coverCompute(String jsonstr){
        String result = SocketClient.getSocketData(jsonstr, getRootPath());
        if(result.length() < 10)
            return "";
        int index0 = result.indexOf('[');
        int index1 = result.indexOf(']');
        result = result.substring(index0, index1+1);
        result = result.replaceAll("  ", "");
        result = result.replaceAll("\n", "");
        try{
            JSONArray input_arr = new JSONArray(result);
            for(int i = 0 ; i < input_arr.length(); i++){
                JSONObject item = input_arr.getJSONObject(i);
                String start_time = item.getString("StartDate").substring(0, item.getString("StartDate").length() - 3);
                String end_time = item.getString("EndDate").substring(0, item.getString("EndDate").length() - 3);
                item.put("StartDate", start_time);
                item.put("EndDate", end_time);
            }
            result = input_arr.toString();
        }
        catch (Exception e){
            result = "";
        }
        return result;
    }

    public String skyCompute(String jsonstr){
        String result = SocketClient.getSocketData(jsonstr);
        if(result.length() < 10)
            return "";
        int index0 = result.indexOf('[');
        int index1 = result.indexOf(']');
        result = result.substring(index0, index1+1);
        result = result.replaceAll("  ", "");
        result = result.replaceAll("\n", "");
        return result;
    }

    public String getTaskTree(String perTaskId){
        try{
            JSONArray tree = new JSONArray();
            PersonalTask personalTask = allServices.getPerTaskCommonService().findPersonaltaskById(perTaskId);
            //每次访问该任务的时候修改最近修改时间这一个选项
            personalTask.setRecentlyModified(new Timestamp(System.currentTimeMillis()));
             allDAO.getPersonalTaskDAO().merge(personalTask);
            String userid = personalTask.getUserId();
            Node root = allServices.getTaskService().getPersonalTaskRootNode(perTaskId);
            String rootpath = userid+"/"+AppPlatConstant.NODETYPE_COVER_ANALYSIS+"/"+root.getId()+"/"+root.getId()+".json";
            String rootresult = allServices.getServerStoreService().getFileByString(rootpath);
            JSONObject tree_root = new JSONObject(rootresult);
            tree.put(tree_root);
            List nodes = allDAO.getNodeDAO().findByParentId(root.getId());
            for(int i = 0 ; i < nodes.size(); i++){
                Node item = (Node)nodes.get(i);
                if(item.getNodeType().equals("Task"))
                    continue;
                String path = userid+"/"+AppPlatConstant.NODETYPE_COVER_ANALYSIS+"/"+root.getId()+"/"+item.getId()+"/"+item.getId()+".json";
                String result = allServices.getServerStoreService().getFileByString(path);
                JSONObject obj = new JSONObject(result);
                tree.put(obj);
                //获取子节点值
                if(item.getNodeType().equals("Satellite")){
                    List sensors = allDAO.getNodeDAO().findByParentId(item.getId());
                    for(int j = 0 ; j < sensors.size() ; j++){
                        Node sensor = (Node)sensors.get(j);
                        String s_path = userid+"/"+AppPlatConstant.NODETYPE_COVER_ANALYSIS+"/"+root.getId()+"/"+sensor.getId()+"/"+sensor.getId()+".json";
                        String s_result = allServices.getServerStoreService().getFileByString(s_path);
                        JSONObject s_obj = new JSONObject(s_result);
                        tree.put(s_obj);
                    }
                }
            }
            String json_result = tree.toString();
            return json_result;
        }
        catch (Exception e){
            return  null;
        }
    }
    public AllDAO getAllDAO() {
        return allDAO;
    }

    public void setAllDAO(AllDAO allDAO) {
        this.allDAO = allDAO;
    }
}
