package webService.opImpl;

import buaa.edu.global.AppPlatConstant;
import entity.databaseEntity.PersonalTask;
import webService.operator.IStructFileManage;
import entity.SaveNode;
import entity.Struct;
import service.AllServices;
import java.util.List;

/**
 * Created by dpy on 2014/7/15.
 */
public class StructFileManage implements IStructFileManage {
    private AllServices allServices;
    public AllServices getAllServices() {
        return allServices;
    }
    public void setAllServices(AllServices allServices) {
        this.allServices = allServices;
    }

    //创建文件夹
    @Override
    public Boolean createFolders(Struct s) {
        Boolean feedback = allServices.getStructFileService().createFolders(s.getParentPath());
        return feedback;
    }

    //创建图片文件
    @Override
    public Boolean createFile(Struct s) {
        Boolean feedback = allServices.getStructFileService().createFile(s.getParentPath(), s.getFileName(), s.getData());
        return feedback;
    }

    @Override
    public Boolean saveTaskToDB(Struct s) {
        Boolean isSaved = allServices.getStructFileService().newOrMergePersionalTask(s.getUserId(), s.getParentPath(),s.getFileName());
        return isSaved;
    }
    @Override
    public List<PersonalTask> getTaskNameByUserId(String userId) {
        List<PersonalTask> personalTasks = allServices.getPerTaskCommonService().findtaskNameByUserIdAndType(userId, AppPlatConstant.PER_TASK_TYPE_STRUCTURE_DESIGN);
        return personalTasks;
    }

    @Override
    public Boolean testStorerage(Struct s) {
        allServices.getiLocalStoreService().getDirInfo(AppPlatConstant.CLOUD_STORAGE_USER_ID,s.getParentPath());
        return null;
    }

    //获取存储区列表
    @Override
    public List<SaveNode> getSaveList(String path){
        List<SaveNode> feedback = allServices.getStructFileService().getSaveList(path);
        return feedback;
    }

    //获取文件内容
    @Override
    public String getFileByString(String path){
        String feedback = allServices.getServerStoreService().getFileByString(path);
        return feedback;
    }

    @Override
    public String exportFile(String path){
        String feedback = allServices.getStructFileService().exportFile(path);
        return feedback;
    }

    public List<SaveNode> getFilesNameWithoutExtension(String path){
        List<SaveNode> fileNameList = allServices.getStructFileService().getChildFileNameList(path);
        return fileNameList;
    }
}
