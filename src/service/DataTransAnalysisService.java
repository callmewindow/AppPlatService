package service;

import buaa.edu.global.AppPlatConstant;
import buaa.edu.global.OverStationCalculate;
import buaa.edu.global.SocketClient;
import dao.AllDAO;
import entity.Struct;
import entity.databaseEntity.Node;
import entity.NodeInfo;

import entity.databaseEntity.PersonalTask;
import org.eclipse.jetty.util.ajax.JSON;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by winter on 2014/9/20.
 */
public class DataTransAnalysisService {
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
        NodeInfo nodeInfo = new NodeInfo();
        String perTaskId =null;
        if(type.equals(AppPlatConstant.NODETYPE_TASK)){  //添加方案节点
            perTaskId = allServices.getPerTaskCommonService().addPerTask(userId, node.getName(), AppPlatConstant.PER_TASK_TYPE_DATATRANS_ANALYSIS);
            nodeInfo =  allServices.getPerTaskCommonService().addNode(userId, node, perTaskId);
            String dirPath = userId + "/" + AppPlatConstant.DATA_TRANS_ANALYSIS+ "/" + nodeInfo.getId();
            allServices.getPerTaskCommonService().mergePerTask(nodeInfo.getId(),dirPath);

        }else { //添加卫星节点
            nodeInfo =  allServices.getPerTaskCommonService().addNode(userId, node, perTaskId);
        }
        return nodeInfo;
    }

    public String channelComput(double[] param){
        double Pts = param[0];//卫星发射机输出功率PTS
        double Gts= param[1];//卫星发射天线最小增益GTS
        double Lines = param[2];//卫星馈线损失LlineS
        double zxsh = param[3];//指向损耗
        double Fdown = param[4];//下行载波频率fDOWN
        double ha = param[5];//卫星轨道远地点高度ha
        double Eg = param[6];//地面天线仰角Eg
        double bc = param[7];//波长
        double Ltp = param[8];//地面天线指向损失Ltp
        double La = param[9];//大气损失La
        double Lh = param[10];//极化损失LH
        double g_ts = param[11];//地面站(G/T)s值
        double Eb_no = param[12];//下行数据需要的(Eb/N0)req(Pe=1*10-5)
        double Leq = param[13];//设备解调损失LEQ
        double smsl = param[14];//数据码速率
        final double bezmcs = -228.6;//玻尔兹曼常数
        final double r_earth = 6378;//地球半径
        final double pi = Math.PI;//圆周率
        final double gs = 299292458;//光速

        String jsonstr = "{";

        //卫星发射机输出功率PTS
        double pis = 10*Math.log10(Pts);
        jsonstr += "\"pis\":"+pis+",";

        //卫星EIRP
        double eirp = pis + Gts + Lines +zxsh;
        jsonstr += "\"eirp\":"+eirp+",";

        //卫星对地张角α
        double a_for_earch = Math.asin(Math.sin(95/180*pi)/(r_earth+ha)*r_earth)*180/pi;
        jsonstr += "\"a_for_earch\":"+a_for_earch+",";

        //星地间距离R
        double r_xdjj = (r_earth+ha)/Math.sin((90+Eg)/180*pi)*Math.sin((90-Eg-a_for_earch)/180*pi);
        jsonstr += "\"r_xdjj\":"+r_xdjj+",";

        //自由空间损失LSDOWN
        double lsdown = 20*Math.log10(bc/4.0/pi/r_xdjj/1000.0);
        jsonstr += "\"lsdown\":"+lsdown+",";

        //地面站总接收信噪密度比（S/N0）DOWN
        double s_no_down = eirp + lsdown + Ltp + La + Lh +g_ts - bezmcs;
        jsonstr += "\"s_no_down\":"+s_no_down+",";

        //数据码速率
        double d_s = 10*Math.log10(smsl);
        jsonstr += "\"d_s\":"+d_s+",";

        //数据调节门限
        double d_sno_down = d_s + Eb_no - Leq;
        jsonstr += "\"d_sno_down\":"+d_sno_down+",";

        //下行数据解调裕量
        double xxday = s_no_down - d_sno_down;
        jsonstr += "\"xxday\":"+xxday+"}";

        return jsonstr;
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
    public String coverCompute(String jsonstr, String filepath){
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
//    public String coverCompute(String jsonstr, int old){
//        try{
//            // String str1 = OverStationCalculate.LoadDLL();
////            OverStationCalculate osc = new OverStationCalculate();
////            String str1 = osc.LoadDLL();
////            String str2 = osc.GetPath();
////            String jsonstrd = "[{\"ResourceId\":\"satellite0\",\"satellite\":\"SatelliteSA1\",\"groundstation\":\"GSTATION\",\"X\":\"10218.754169\",\"Y\":\"-166.522423\",\"Z\":\"0.0\",\"VX\":\"2.859956\",\"VY\":\"5.362549\",\"VZ\":\"3.122562\",\"START-DATE\":\"2454283\",\"END-DATE\":\"2454284\",\"HB\":\"60\",\"DLAT\":\"0.0\",\"DLON\":\"2.859956\",\"ALT\":\"0\",\"HM\":\"5\"}]";
////            String filepath = "S:\\Users\\xpzsoft\\Desktop\\coverjson\\data\\";
////            String datas = osc.CalculateCoverPath(jsonstrd, filepath);
//            JSONArray input_arr = new JSONArray(jsonstr);
//            JSONArray output_arr = new JSONArray();
//            for(int i = 0 ; i < input_arr.length(); i++){
//                JSONObject paramobj = (JSONObject) input_arr.get(i);
//                String file_name = paramobj.getString("satellite")+paramobj.getString("groundstation");
//                List<JSONObject> data = checkFileExist(file_name, paramobj);
//                if(data == null){
//                    callDLLforCoverCompute(0,
//                            paramobj.getDouble("DAY0"),
//                            new double [] {paramobj.getDouble("X"), paramobj.getDouble("Y"), paramobj.getDouble("Z"), paramobj.getDouble("VX"), paramobj.getDouble("VY"), paramobj.getDouble("VZ")},
//                            paramobj.getDouble("Hb"),
//                            paramobj.getDouble("DAY1"),
//                            paramobj.getInt("N"),
//                            paramobj.getDouble("DLAI"),
//                            paramobj.getDouble("DLON"),
//                            paramobj.getDouble("ALT"),
//                            paramobj.getDouble("HM"),
//                            paramobj.getString("NAMFILIN0"),
//                            paramobj.getString("NAMFILIN1"),
//                            file_name);
//                    data = checkFileExist(file_name, paramobj);
//                }
//                if(data!=null){
//                    for(int j = 0 ; j < data.size() ; j++){
//                        output_arr.put(data.get(j));
//                    }
//                }
//            }
//            return output_arr.toString();
//        }
//        catch (Exception e){
//            return null;
//        }
//    }
//    private List<JSONObject> checkFileExist(String filename, JSONObject obj){
//        File dir = new File(this.getClass().getClassLoader().getResource("/").getPath() + "/coverjson");
//        File files[] = dir.listFiles();
//        for(int i = 0 ; i < files.length ; i++){
//            if(files[i].getName().equals(filename+".dat")){
//                return readFile(files[i], obj);
//            }
//        }
//        return null;
//    }
//    private List<JSONObject> readFile(File file, JSONObject obj){
//        try{
//            List<JSONObject> onjlist = new ArrayList<JSONObject>();
//            FileReader reader = new FileReader(file);
//            BufferedReader br = new BufferedReader(reader);
//            String str = null;
//            int count = 0;
//            while((str = br.readLine()) != null) {
//                count++;
//                String start_time = datatimeFormat(str.substring(0, 26));
//                String end_time = datatimeFormat(str.substring(26,52));
//                //格式转换
//                try {
//                    JSONObject item = new JSONObject();
////                    if(count == 1){
////                        item.put("Id", obj.getString("Id"));
////                        item.put("Name", obj.getString("Name"));
////                    }
//                    item.put("ResourceId", obj.getString("ResourceId"));
//                    // item.put("Title", "第"+count+"次过站");
//                    item.put("StartDate", start_time);
//                    item.put("EndDate", end_time);
//                    item.put("Resizable", false);
//                    item.put("Draggable", false);
//                    onjlist.add(item);
//                }
//                catch (org.json.JSONException e){
//                    return null;
//                }
//
//            }
//            return onjlist;
//        }
//        catch (IOException e){
//            return null;
//        }
//    }

    public String getTaskTree(String perTaskId){
        try{
            JSONArray tree = new JSONArray();
            PersonalTask personalTask = allServices.getPerTaskCommonService().findPersonaltaskById(perTaskId);
            //每次访问该任务的时候修改最近修改时间这一个选项
            personalTask.setRecentlyModified(new Timestamp(System.currentTimeMillis()));
            allDAO.getPersonalTaskDAO().merge(personalTask);
            String userid = personalTask.getUserId();
            Node root = allServices.getTaskService().getPersonalTaskRootNode(perTaskId);
            String rootpath = userid+"/"+AppPlatConstant.NODETYPE_DATATRANS_ANALYSIS+"/"+root.getId()+"/"+root.getId()+".json";
            String rootresult = allServices.getServerStoreService().getFileByString(rootpath);
            JSONObject tree_root = new JSONObject(rootresult);
            tree.put(tree_root);
            List nodes = allDAO.getNodeDAO().findByParentId(root.getId());
            for(int i = 0 ; i < nodes.size(); i++){
                Node item = (Node)nodes.get(i);
                if(item.getNodeType().equals("Task"))
                    continue;
                String path = userid+"/"+AppPlatConstant.NODETYPE_DATATRANS_ANALYSIS+"/"+root.getId()+"/"+item.getId()+"/"+item.getId()+".json";
                String result = allServices.getServerStoreService().getFileByString(path);
                JSONObject obj = new JSONObject(result);
                tree.put(obj);
            }
            String json_result = tree.toString();
            return json_result;
        }
        catch (Exception e){
            return  null;
        }
    }
    public int compareJSONObject(JSONObject o1, JSONObject o2){
        try{
            if(o1.has("type")&&o2.has("type")){
                if(o1.getString("type").equals(o2.getString("type"))){
                    if(o1.getString("name").length()!= o2.getString("name").length()){
                        return o1.getString("name").length() - o2.getString("name").length();
                    }else{
                        return o1.getString("name").compareTo(o2.getString("name"));
                    }
                }else{
                    return getRank(o1)-getRank(o2);
                }
            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int getRank(JSONObject o1){
        String type = null;
        try {
            type = o1.getString("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(type.equals("satellite")){//卫星
            return 1;
        }
        if(type.equals("groundstation")){//地面站
            return 2;
        }
        if(type.equals("chainanaysis")){//传感器
            return 3;
        }
        return 0;
    }



    private void callDLLforCoverCompute(int PROFLAG, double DAY0, double [] VEC0, double Hb, double DAY1, int N , double DLAT, double DLON, double ALT, double HM, String NAMFILIN0, String NAMFILIN1, String filename){
        System.out.println("call dll to compute data that writed into PassStation.bat");
    }

    final String [] mm = new String [] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private String datatimeFormat(String time){
        String []splits = time.trim().split(" ");
        for(int i = 0 ; i < mm.length ; i++){
            if(mm[i].equals(splits[1])){
                if(i+1<10)
                    return splits[2]+"-0"+(i+1)+"-"+splits[0]+" "+splits[3].substring(0,splits[3].length() - 4);
                else
                    return splits[2]+"-"+(i+1)+"-"+splits[0]+" "+splits[3].substring(0,splits[3].length() - 4);
            }
        }
        return null;
    }

    public AllDAO getAllDAO() {
        return allDAO;
    }

    public void setAllDAO(AllDAO allDAO) {
        this.allDAO = allDAO;
    }
}
