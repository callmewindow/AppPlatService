package service;

import buaa.edu.global.AppPlatConstant;
import buaa.edu.global.CloudStoreThread;
import dao.AllDAO;
import entity.OperationResponse;
import entity.databaseEntity.PersonalTask;
import entity.SaveNode;
import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class StructFileService  {

    private AllDAO allDAO;
    private AllServices allServices;
    public AllServices getAllServices() {
        return allServices;
    }
    public void setAllServices(AllServices allServices) {
        this.allServices = allServices;
    }

    public AllDAO getAllDAO() {
        return allDAO;
    }

    public void setAllDAO(AllDAO allDAO) {
        this.allDAO = allDAO;
    }

    // 创建文件
    public Boolean createFolders(String path){
       Boolean localFlag = allServices.getServerStoreService().createFolder(path);
       CloudStoreThread.getCloudStore().createFolder(path);
        return localFlag;
    }
    public Boolean newOrMergePersionalTask(String userId, String perTaskDirPath, String taskName){
        Boolean isSaved = true;
        PersonalTask personalTask = new PersonalTask();
        List<PersonalTask> personalTasks = allDAO.getPersonalTaskDAO().findByPerTaskName(taskName);
        if(personalTasks!=null&&personalTasks.size()>0){
            personalTask = personalTasks.get(0);
            personalTask.setRecentlyModified(new Timestamp(System.currentTimeMillis()));
            try {
                allDAO.getPersonalTaskDAO().merge(personalTask);
            } catch (Exception e) {
                isSaved = false;
                e.printStackTrace();
            }
        }
        else{
            personalTask.setTaskType(AppPlatConstant.PER_TASK_TYPE_STRUCTURE_DESIGN);
            personalTask.setIsSaved(AppPlatConstant.IS_PER_TASK_SAVED);
            personalTask.setPerTaskName(taskName);
            personalTask.setUserId(userId);
            personalTask.setRecentlyModified(new Timestamp(System.currentTimeMillis()));
            personalTask.setTaskDirPath(perTaskDirPath);
            try {
                allDAO.getPersonalTaskDAO().save(personalTask);
            } catch (Exception e) {
                isSaved = false;
                e.printStackTrace();
            }

        }
        return isSaved;
    }

    // 创建文件
    public boolean createFile(String parentPath, String fileName, String data){
        boolean flag = true;
        OperationResponse operationResponse = new OperationResponse();
        File file = allServices.getServerStoreService().createFile(parentPath, fileName, data);
        if(file == null){
            flag = false;
        }else{
            flag = true;
            String filePath = parentPath +  "/" + fileName;
            CloudStoreThread.getCloudStore().uploadFile(file.getPath(),filePath);
        }
        return flag;
    }

    public String exportFile(String path){
        FileInputStream fis = null;
        String ans = null;
        try{
            fis = new FileInputStream(allServices.getServerStoreService().getFile(path));
            byte[]data = new byte[10240000];
            int i = 0;
            int n = fis.read();
            while(n!=-1){
                data[i]=(byte)n;
                i++;
                n=fis.read();
            }
            ans = new String(data, 0, i);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                fis.close();
            }catch(Exception e){}
        }
        return ans;
    };

    public List<SaveNode> getChildFileNameList(String path){
        List<SaveNode> fileNameList = new ArrayList<SaveNode>();
        File[] files = allServices.getServerStoreService().getChildFileList(path);
        if(files == null){
            return null;
        }
        else{
            for(int i =0 ;i < files.length;i ++){
                SaveNode sn = new SaveNode();
                sn.setName(files[i].getName());
                fileNameList.add(sn);
            }
            return  fileNameList;
        }
    };
    //获取存储区列表
    public List<SaveNode> getSaveList(String path){
        List<SaveNode> ans = new ArrayList<SaveNode>();
        createFolders(path);
        File file = allServices.getServerStoreService().getDirFile(path);
        for(String s: file.list()){
            SaveNode t = new SaveNode();
            t.setName(s);
            t.setData(allServices.getServerStoreService().getFileByString(path + "/" + s + "/" + s + ".map"));
            ans.add(t);
        }
        return ans;
    }
}