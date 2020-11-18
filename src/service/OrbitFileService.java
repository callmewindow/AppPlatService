package service;

import buaa.edu.global.AppPlatConstant;
import buaa.edu.global.IDGeneratorUtil;
import buaa.edu.global.OrbitCalculate;
import buaa.edu.global.OrbitTransfer;
import buaa.edu.sortEntity.CompAsyncNode;
import dao.AllDAO;
import entity.*;
import entity.databaseEntity.Node;
import entity.databaseEntity.PersonalTask;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/**
 * Created by winter on 2014/8/23.
 */
public class OrbitFileService {
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
    //保存轨道场景到文件中
    public boolean saveOrbitSceneFile(String path, String data, String solutionId){
        try {
            JSONObject sceneData = new JSONObject(data);
            String solutionData = "{\"t_start\":\"" + sceneData.getString("t_start") +
                                   "\",\"t_stop\":\"" + sceneData.getString("t_stop") +
                                    "\",\"step\":" + sceneData.getString("step") +
                                    ",\"speedscalar\":" + sceneData.getString("speedscalar") +
                                    ",\"t_simu\":\"" + sceneData.getString("t_simu") + "\"";
            //保存方案文件
            saveFile(path.substring(0, path.length() - 1), solutionData, solutionId);

            JSONArray satArray = sceneData.getJSONArray("orbitData");
            for(int i = 0; i < satArray.length(); i++){
                JSONObject satData = satArray.getJSONObject(i);
                String satelliteId = getSatIdByName(satData.remove("id").toString(), solutionId);
                //保存卫星文件
                saveFile(path + satelliteId, satData.toString(), satelliteId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }
    //在指定的方案中，根据卫星名字获取其Id
    public String getSatIdByName(String sateName, String solutionId){
        List<Node> childNodes = allDAO.getNodeDAO().findByParentId(solutionId);
        for(int i = 0; i < childNodes.size(); i++){
            if(childNodes.get(i).getName().equals(sateName)){
                return childNodes.get(i).getId();
            }
        }
        return null;
    }
    //根据path，data, fileName 保存文件
    public boolean saveFile(String path, String data, String fileName){
        Struct s = new Struct();
        s.setParentPath(path);
        s.setFileName(fileName);
        s.setData(data);
        //保存方案文件
        return saveOrbit(s);
    }
    // 创建文件
    public boolean saveOrbit(Struct s){
        Boolean localFlag = allServices.getServerStoreService().createFolder(s.getParentPath());
//        OperationResponse operationResponse = allServices.getEnCloudStoreService().createAllPathDirs(AppPlatConstant.CLOUD_STORAGE_USER_ID,s.getParentPath());
//        if(localFlag&&operationResponse.getIsOK()){
//            boolean flag = true;
//            File file = allServices.getServerStoreService().createFile(s.getParentPath(), s.getFileName(), s.getData());
//            if(file == null){
//                flag = false;
//            }else{
//                flag = true;
//                String filePath = s.getParentPath() +  "/" + s.getFileName();
//                operationResponse = allServices.getiLocalStoreService().uploadFile(AppPlatConstant.CLOUD_STORAGE_USER_ID,file.getPath(),filePath);
//                flag = operationResponse.getIsOK();
//            }
//            return flag;
//        }
//        else{
//            return false;
//        }
        if(localFlag){
            boolean flag = true;
            File file = allServices.getServerStoreService().createFile(s.getParentPath(), s.getFileName(), s.getData());
            if(file == null){
                flag = false;
            }else{
                flag = true;
//                String filePath = s.getParentPath() +  "/" + s.getFileName();
//                operationResponse = allServices.getiLocalStoreService().uploadFile(AppPlatConstant.CLOUD_STORAGE_USER_ID,file.getPath(),filePath);
//                flag = operationResponse.getIsOK();
            }
            return flag;
        }
        else{
            return false;
        }
    }
    //初始化方案文件
    public boolean initSolutionFile(String path, String fileName){
        String startTime = "Sun Jul 15 2012 04:00:00 GMT+0800 (中国标准时间)";
        String endTime = "Fri Jul 15 2022 04:00:00 GMT+0800 (中国标准时间)";
        int step = 3600;
        String scenData = "{\"t_start\":\"" + startTime + "\",\n";
        scenData += "\"t_stop\":\"" + endTime + "\",\n";
        scenData += "\"step\":" + step + ",\n";
        scenData += "\"speedscalar\":" + 1 + ",\n";
        scenData += "\"t_simu\":\"" + startTime + "\"\n";
        Struct s = new Struct();
        s.setData(scenData);
        s.setParentPath(path);
        s.setFileName(fileName);
        return saveOrbit(s);
    }
    public boolean deleteOrbit(Struct s){
        boolean flag = false;
            flag = allServices.getServerStoreService().deleteFile(s.getParentPath()  + "/" + s.getFileName());
        return flag;
    }
    public boolean deleteSceneDir(Struct s){
        boolean flag = false;
        flag = allServices.getServerStoreService().deleteDir(s.getParentPath());
        return flag;
    }
    public NodeInfo deleteSatelliteNode(String userId, Node node) {
        if(node.getNodeType().equals(AppPlatConstant.NODETYPE_SATELLITE)){
            Node newNode = allDAO.getNodeDAO().findById(node.getId());

            if(newNode != null){
                allDAO.getNodeDAO().delete(newNode);
            }
            NodeInfo nodeInfo = new NodeInfo();
            nodeInfo.setIsOK(true);
            return nodeInfo;
        }
        else{
            Node newNode = allDAO.getNodeDAO().findById(node.getId());

            if(newNode != null){
                //删除该节点的所有子节点
                List<Node> nodes = allDAO.getNodeDAO().findByParentId(newNode.getId());
                for( int i = 0; i < nodes.size(); i++){
                    allDAO.getNodeDAO().delete(nodes.get(i));
                }
                //删除自己
                allDAO.getNodeDAO().delete(newNode);
                //将该节点在person_task表中删除
                List<PersonalTask> perTasks = allDAO.getPersonalTaskDAO().findByPerTaskName(newNode.getName());
                for( int i = 0; i < perTasks.size(); i++){
                    allDAO.getPersonalTaskDAO().delete(perTasks.get(i));
                }
            }
            NodeInfo nodeInfo = new NodeInfo();
            nodeInfo.setIsOK(true);
            return nodeInfo;
        }
    }

//    /**
//     * 向数据库中插入根节点，并在文件结构中创建根目录
//     * @param userId
//     * @return
//     */
//    public Node getUserNode(String userId){
//        //如果表中没有这个userId为dataId的节点，那么添加一个节点到数据库中
//        Boolean isHas =false;
//        List<Node> nodeList = allDAO.getNodeDAO().findAll();
//        for(int i =0;i<nodeList.size();i++){
//            if(nodeList.get(i).getDataId()!=null&&nodeList.get(i).getDataId().equals(userId)){
//                isHas = true;
//                return nodeList.get(i);
//            }
//        }
//        if(!isHas){
//            Node node = new Node();
//            String nodeId = IDGeneratorUtil.getUUID(AppPlatConstant.TASK_PRE_ID);
//            node.setId(nodeId);
//            node.setParentId(nodeId);
//            node.setRootId(nodeId);
//            node.setIsRoot(true);
//            node.setLeaf(false);
//            node.setDataId(userId);
//            node.setNodeOrder(0);
//            node.setNodeType(AppPlatConstant.NODETYPE_TASK);
//            node.setNodeFilePath(node.getId());
//            allDAO.getNodeDAO().save(node);
//            return node;
//        }
//      return null;
//    };
    /**
     * 向树中添加+对应的节点，并且在文件结构中创建对应的文件夹
     * @param node
     * @return
     */
    public NodeInfo addNode( String userId, Node node){
        String type = node.getNodeType();
        if(type.equals(AppPlatConstant.NODETYPE_TASK)){  //添加方案节点
            String perTask = addTaskToDB(userId,node.getName());
            String nodeId = IDGeneratorUtil.getUUID(AppPlatConstant.TASK_PRE_ID);
            node.setId(nodeId);
            node.setParentId(nodeId);
            node.setRootId(nodeId);
            node.setDataId(perTask);//标识这个树中的根节点所属的个人任务
        }else if(type.equals(AppPlatConstant.NODETYPE_SATELLITE)){ //添加卫星节点
            node.setId(IDGeneratorUtil.getUUID(AppPlatConstant.SATELLITE_PRE_ID));
        }
        allDAO.getNodeDAO().save(node);
        NodeInfo nodeInfo = new NodeInfo();
        nodeInfo.setNode(node);
        return nodeInfo;
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
    public String addTaskToDB(String userId,String taskName){
        PersonalTask personalTask = new PersonalTask();
        personalTask.setTaskType(AppPlatConstant.PER_TASK_TYPE_ORBIT_DESIGN);
        personalTask.setIsSaved(AppPlatConstant.IS_PER_TASK_SAVED_NOT);
        personalTask.setPerTaskName(taskName);
        personalTask.setUserId(userId);
        personalTask.setRecentlyModified(new Timestamp(System.currentTimeMillis()));
        //暂时不保存路径
        try {
            allDAO.getPersonalTaskDAO().save(personalTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  personalTask.getPerTaskId();
    }

//    public Boolean deletePerTaskInDB(String userId, String perTaskName) {
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
//                 node = nodes.get(0);
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
//    return  isSaved;
//    }
    //类型是方案，但是

    //从DLL文件中获取行星位置
    public String getPlanetPosition(int y, int m, int d, int h, int mi, int sec, int a, int aT, int b, int bT){
        Calendar currentTime = Calendar.getInstance();
        currentTime.set(y, m, d, h, mi, sec);
        OrbitCalculate.getInstance().setMdateCurrentTime(currentTime);

        double[] p_position = new double[3];
        double[] m_matrixFixed = new double[9], m_matrixJ2000 = new double[9], m_matrixMagnetic = new double[9], m_matrixGSM = new double[9], m_matrixGSE = new double[9], m_matrixSM = new double[9];
        if(bT != 10){
            if(bT == 3){
                OrbitCalculate.getInstance().computeEarthSpecial(p_position, m_matrixMagnetic, m_matrixGSM, m_matrixGSE, m_matrixSM, bT, 11);
            }
            OrbitCalculate.getInstance().computePlanet(p_position, m_matrixFixed, m_matrixJ2000, bT, 11);
        }
        else{
            OrbitCalculate.getInstance().computeMoon(p_position, m_matrixFixed, m_matrixJ2000, bT, 3);
        }
        String pos_mat = null;
        pos_mat = "{\"position\":[{\"x\":" + p_position[0] + ",\"y\":" + p_position[1] + ",\"z\":" + p_position[2] + "}]," +
                "\"matrixFixed\":[{\"0\":" + m_matrixFixed[0] + ",\"1\":" + m_matrixFixed[1]
                + ",\"2\":" + m_matrixFixed[2] + ",\"3\":" + m_matrixFixed[3]
                + ",\"4\":" + m_matrixFixed[4] + ",\"5\":" + m_matrixFixed[5]
                + ",\"6\":" + m_matrixFixed[6] + ",\"7\":" + m_matrixFixed[7]
                + ",\"8\":" + m_matrixFixed[8] + "}]," +
                "\"matrixJ2000\":[{\"0\":" + m_matrixJ2000[0] + ",\"1\":" + m_matrixJ2000[1]
                + ",\"2\":" + m_matrixJ2000[2] + ",\"3\":" + m_matrixJ2000[3]
                + ",\"4\":" + m_matrixJ2000[4] + ",\"5\":" + m_matrixJ2000[5]
                + ",\"6\":" + m_matrixJ2000[6] + ",\"7\":" + m_matrixJ2000[7]
                + ",\"8\":" + m_matrixJ2000[8] +  "}]";
        if(bT == 3){
            pos_mat += "," +
                    "\"matrixGSM\":[{\"0\":" + m_matrixGSM[0] + ",\"1\":" + m_matrixGSM[1]
                    + ",\"2\":" + m_matrixGSM[2] + ",\"3\":" + m_matrixGSM[3]
                    + ",\"4\":" + m_matrixGSM[4] + ",\"5\":" + m_matrixGSM[5]
                    + ",\"6\":" + m_matrixGSM[6] + ",\"7\":" + m_matrixGSM[7]
                    + ",\"8\":" + m_matrixGSM[8] +  "}]";
        }
        pos_mat += "}";

        return pos_mat;
//        String position = null;
//        double[] p_mat = new double[9], p_dis = new double[3];
//
//        p_dis[0] = 1;
//        p_dis[1] = 2;
//        p_dis[2] = 3;
//        String filePath = new String("E:\\ZBZHEphemerisData");
//        int pathlen = filePath.length();
//        OrbitCalculate.getInstance().CorTransCalc(y, m, d, h, mi, sec, a, aT, b, bT, p_mat, p_dis, filePath, pathlen);
//
//        position = "{\"position\":[{\"x\":" + (-p_dis[1]) + ",\"y\":" + (-p_dis[2]) + ",\"z\":" + (-p_dis[0]) + "}]," +
//                "\"mat\":[{\"0\":" + p_mat[0] + ",\"1\":" + p_mat[1]
//                + ",\"2\":" + p_mat[2] + ",\"3\":" + p_mat[3]
//                + ",\"4\":" + p_mat[4] + ",\"5\":" + p_mat[5]
//                + ",\"6\":" + p_mat[6] + ",\"7\":" + p_mat[7]
//                + ",\"8\":" + p_mat[8] + "}]}";
//        return position;
    }
    public String getSatTransferVelocity(double [] R1, double [] R2, double FTIME){
        double [] V1 = new double[3];
        double [] V2 = new double[3];
        long [] INFO = new long[1];
        OrbitTransfer.getInstance().OrbitLambert(R1, R2, FTIME, V1, V2, INFO);
        String velocity = null;
        velocity = "{\"INFO\":" + INFO[0] +
                    ",\"V1\":[{\"x\":" + V1[0] +
                            ",\"y\":" + V1[1] +
                            ",\"z\":" + V1[2] + "}]" +
                    ",\"V2\":[{\"x\":" + V2[0] +
                            ",\"y\":" + V2[1] +
                            ",\"z\":" + V2[2] + "}]}";
        return velocity;
    }
    //获取文件内容
    private String getFileByPath(String path){
        FileInputStream fis = null;
        String dataAns = null;
        try{
            fis = new FileInputStream(allServices.getServerStoreService().getFile(path));
            byte[]data = new byte[102400];
            int i = 0;
            int n = fis.read();
            while(n!=-1){
                data[i]=(byte)n;
                i++;
                n=fis.read();
            }
            dataAns = new String(data, 0, i);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                fis.close();
            }catch(Exception e){}
        }
        return dataAns;
    }
    //获取场景文件内容
    public String getFileByString(String path, String nodeId){
        List<Node> childNodes = allDAO.getNodeDAO().findByParentId(nodeId);
        for( int i = 0;i < childNodes.size(); i++){
            if(!childNodes.get(i).getNodeType().equals(AppPlatConstant.NODETYPE_SATELLITE)){
                childNodes.remove(i);
                i--;
            }
        }
        String dataAns = null;
        //读取场景文件的内容
        String solution = nodeId.substring(0, 8);
        dataAns = getFileByPath(path + nodeId);
        for( int i = 0;i < childNodes.size(); i++){
            if(i == 0){
                dataAns += ",\"orbitData\":[";
            }
            if("solution".equals(solution)){
                dataAns += getFileByPath(path + childNodes.get(i).getId() + "/" + childNodes.get(i).getId());
            }
            else{
                dataAns += getFileByPath(path + childNodes.get(i).getId());
            }
            if(dataAns.length() < 1){
                return new Exception("卫星文件中没数据").toString();
            }
            dataAns = dataAns.substring(0, dataAns.length() - 1);

            dataAns += ",\"id\":\"" + childNodes.get(i).getName() + "\"}";
            if(i == childNodes.size() - 1){
                dataAns += "]";
            }
            else{
                dataAns += ",";
            }
        }
        dataAns += "}";
        return dataAns;

//        String ans = null;
//        try{
//            fis = new FileInputStream(allServices.getServerStoreService().getFile(path));
//            byte[]data = new byte[102400];
//            int i = 0;
//            int n = fis.read();
//            while(n!=-1){
//                data[i]=(byte)n;
//                i++;
//                n=fis.read();
//            }
//            ans = new String(data, 0, i);
//        }catch(Exception e){
//            e.printStackTrace();
//        }finally{
//            try{
//                fis.close();
//            }catch(Exception e){}
//        }
//        return ans;
    };
    //把字节数组转换为Float
    float convertToFloatFrom(byte [] data) throws Exception {
        int num = 0;
        if(data.length != 4) {
            throw new Exception("data's length is not equal to 4");
        }

        num |= 0xff000000 & (data[3] << 24);
        num |= 0xff0000 & (data[2] << 16);
        num |= 0xff00 & (data[1] << 8);
        num |= 0xff & data[0];
        return Float.intBitsToFloat(num);
    }
    //把字节数组转换为Double
    double convertToDoubleFrom(byte [] data) throws Exception {
        long num = 0;
        if(data.length != 8) {
            throw new Exception("data's length is not equal to 8");
        }
        num |= 0xff00000000000000l & ((long)data[7] << 56);
        num |= 0xff000000000000l & ((long)data[6] << 48);
        num |= 0xff0000000000l & ((long)data[5] << 40);
        num |= 0xff00000000l & ((long)data[4] << 32);
        num |= 0xff000000l & ((long)data[3] << 24);
        num |= 0xff0000l & ((long)data[2] << 16);
        num |= 0xff00l & ((long)data[1] << 8);
        num |= 0xffl & (long)data[0];
        return Double.longBitsToDouble(num);
    }
    //获取辐射带的点
    public String getRadBeltPoint(){
        String point = "{\"longitude\":[";

        FileInputStream fis = null;
        try{
            fis = new FileInputStream(getRootPath() + "../resources/data/RadBeltData.bin");
            byte[]bytes4 = new byte[4];
            int i = 0;
            for(i = 0; i < 73; i++){
                fis.read(bytes4);
                point += convertToFloatFrom(bytes4);
                if(i < 72){
                    point += ",";
                }
                else{
                    point += "],";
                }
            }
            point += "\"latitude\":[";
            for(i = 0; i < 141; i++){
                fis.read(bytes4);
                point += convertToFloatFrom(bytes4);
                if(i < 140){
                    point += ",";
                }
                else{
                    point += "],";
                }
            }
            point += "\"high\":[";
            for(i = 0; i < 100; i++){
                fis.read(bytes4);
                point += convertToFloatFrom(bytes4);
                if(i < 99){
                    point += ",";
                }
                else{
                    point += "]}";
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                fis.close();
            }catch(Exception e){}
        }
        return point;
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
    //获取磁力线的点
    private final  String[]  json_file_names= {
            "trackjson_01.txt",
            "trackjson_02.txt",
            "trackjson_03.txt",
            "trackjson_04.txt",
            "trackjson_05.txt",
            "trackjson_06.txt",
            "trackjson_07.txt",
            "trackjson_08.txt",
            "trackjson_09.txt",
            "trackjson_10.txt",
            "trackjson_11.txt",
            "trackjson_12.txt"};
    public String getTrackPoints(int lineId){
        FileInputStream fis = null;
        String text = "";
        try{
            fis = new FileInputStream(getRootPath() + "../resources/data/trackjson/" +json_file_names[lineId]);
            byte[] byteInData = new byte[1024];
            int length = 0;
            while ((length = fis.read(byteInData)) != -1) {
                text += new String(byteInData, 0, length);
            }
            fis.close();
            fis = null;
            return text;
        }
        catch(Exception e){
            return null;
        }
    }
    private final String zip(String str) {
        if (str == null)
            return null;
        byte[] compressed;
        ByteArrayOutputStream out = null;
        ZipOutputStream zout = null;
        String compressedStr = null;
        try {
            out = new ByteArrayOutputStream();
            zout = new ZipOutputStream(out);
            zout.putNextEntry(new ZipEntry("0"));
            zout.write(str.getBytes());
            zout.closeEntry();
            compressed = out.toByteArray();
            compressedStr = new sun.misc.BASE64Encoder().encodeBuffer(compressed);
        } catch (IOException e) {
            compressed = null;
        } finally {
            if (zout != null) {
                try {
                    zout.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
        return compressedStr;
    }
    //获取指定能级的辐射带粒子通量
    public double[][][] getRadBeltData(int degree){
        double [][][]data = new double[100][141][73];
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(getRootPath() + "../resources/data/RadBeltData.bin");
            byte []bytes8 = new byte[8];
            fis.skip((73 + 141 + 100) * 4 - 72);
            fis.skip(degree * 8);
            for(int altIndex = 0; altIndex < 100; altIndex++){
                for(int latIndex = 0; latIndex < 141; latIndex++){
                    for(int lonIndex = 0; lonIndex < 73; lonIndex++){
                        fis.skip(72);
                        fis.read(bytes8);
                        data[altIndex][latIndex][lonIndex] = convertToDoubleFrom(bytes8);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                fis.close();
            }catch(Exception e){}
        }
        return data;
    }
    public List<SaveNode> getChildFileNameList(String userId){
        List<SaveNode> fileNameList = new ArrayList<SaveNode>();
        List<PersonalTask> personalTaskList= allServices.getOrbitFileService().findPersonalTaskByUserId(userId);
        for(int i =0 ;i < personalTaskList.size();i ++){
            if(personalTaskList.get(i).getTaskType().equals(AppPlatConstant.PER_TASK_TYPE_ORBIT_DESIGN)){
                SaveNode sn = new SaveNode();
                sn.setName(personalTaskList.get(i).getPerTaskName());
                fileNameList.add(sn);
            }
        }
        return  fileNameList;
    }
    public Boolean mergeTaskInfo(Struct s){
        Boolean isSavedSuccess = true;

        Node node = allDAO.getNodeDAO().findById(s.getFileName());
        PersonalTask personalTask = allDAO.getPersonalTaskDAO().findById(node.getDataId());

            personalTask.setIsSaved(AppPlatConstant.IS_PER_TASK_SAVED);
            personalTask.setTaskDirPath(s.getParentPath());
            personalTask.setRecentlyModified(new Timestamp(System.currentTimeMillis()));
            try {
                allDAO.getPersonalTaskDAO().merge(personalTask);
            } catch (Exception e) {
                isSavedSuccess = false;
                e.printStackTrace();
            }


        return isSavedSuccess;
    }
    public List<SaveNode> findtaskNameByUserId(String userId) {
        List<PersonalTask> personalTasks = allDAO.getPersonalTaskDAO().findByUserId(userId);
        List<SaveNode> saveNodes = new ArrayList<SaveNode>();
        for( int i = 0;i < personalTasks.size(); i++){
            SaveNode saveNode = new SaveNode();
            saveNode.setName(personalTasks.get(i).getPerTaskName());
            saveNodes.add(saveNode);
        }
        return saveNodes;
    }
    public List<PersonalTask> findPersonalTaskByUserId(String userId) {
        List<PersonalTask> personalTasks = allDAO.getPersonalTaskDAO().findByUserId(userId);
        return personalTasks;
    }

    public List<SaveNode> findSatelliteNameByTaskId(String nodeId) {
        List<Node> childNodes = allDAO.getNodeDAO().findByParentId(nodeId);
        List<SaveNode> saveNodes = new ArrayList<SaveNode>();
        for( int i = 0;i < childNodes.size(); i++){
            SaveNode saveNode = new SaveNode();
            saveNode.setName(childNodes.get(i).getName());
            saveNodes.add(saveNode);
        }
        return saveNodes;
    }
    public List<Node> getAllNodesByRootId(String rootId) {
        List<Node> targetsNodes = new ArrayList<Node>();
        List<Node> nodes = allDAO.getNodeDAO().findByParentId(rootId);
            for(int i = 0;i<nodes.size();i++){
                if(!nodes.get(i).getId().equals(nodes.get(i).getParentId())){
                    targetsNodes.add(nodes.get(i));
                    List<Node> childNodes = allDAO.getNodeDAO().findByParentId(nodes.get(i).getId());
                    for(int j = 0;j<childNodes.size();j++) {
                        targetsNodes.add(childNodes.get(j));
                    }
                }
            }
        CompAsyncNode cmt = new CompAsyncNode();
        Collections.sort(targetsNodes, cmt);
        return targetsNodes;
    }
}
