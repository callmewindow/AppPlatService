package webService.opImpl;

import buaa.edu.global.AppPlatConstant;
import entity.CoTask;
import entity.collaborativeDesignEntity.CollaUser;
import entity.databaseEntity.PersonalTask;
import entity.databaseEntity.Task;
import org.omg.IOP.TAG_ALTERNATE_IIOP_ADDRESS;
import webService.operator.IInterfaceManage;
import entity.OperationResponse;
import service.AllServices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by winter on 2014/7/19.
 */
public class InterfaceManage implements IInterfaceManage {
    private AllServices allServices;
    public AllServices getAllServices() {
        return allServices;
    }
    public void setAllServices(AllServices allServices) {
        this.allServices = allServices;
    }
    @Override
    public OperationResponse verifyDigitalSignature(String signature, String userId, String account, String domain, String permission, String name) {
        OperationResponse operationResponse =allServices.getiDigitalSignatureService().digitalSignatures(signature);
        Boolean isSaved =allServices.getiDigitalSignatureService().insertUserToDB(userId,account,domain,permission,name);
        return operationResponse;
    }

    @Override
    public OperationResponse insertAccessInfo(String account, String name, String domain, String ip, String dateTime, String service, String url, String describe) {
        OperationResponse operationResponse =allServices.getiLocalStoreService().insertAccessInfo(account, name, domain, ip, dateTime, service, url, describe);
        return operationResponse;
    }

    public List<CoTask> getMyTaskInfo(String userId){
        List<CoTask> coTaskList = new ArrayList<CoTask>();
        List<PersonalTask> personalTasks = allServices.getPerTaskCommonService().findtaskNameByUserId(userId);
        for(int i = 0; i < personalTasks.size(); i++){
            CoTask coTask = new CoTask();
            PersonalTask personalTask = personalTasks.get(i);
            coTask.setTaskId(personalTask.getPerTaskId());
            coTask.setRecentlyModified(personalTask.getRecentlyModified().toLocaleString());
            coTask.setTaskName(personalTask.getPerTaskName());
            coTask.setTaskType(personalTask.getTaskType());
            coTask.setModelId(personalTask.getTaskType());
            coTaskList.add(coTask);
        }
        List<Task> tasks = allServices.getTaskService().getMyColTask(userId);
        for(int i = 0; i < tasks.size(); i++){
            CoTask coTask = new CoTask();
            Task task = tasks.get(i);
            coTask.setTaskId(task.getTaskId());
            coTask.setRecentlyModified(task.getRecentlyModified().toLocaleString());
            coTask.setTaskName(task.getTaskName());
            coTask.setTaskType(AppPlatConstant.TASK_TYPE_COLLABORATIVE_ARGUMENT);
            coTask.setModelId(AppPlatConstant.TASK_TYPE_COLLABORATIVE_ARGUMENT);
            coTaskList.add(coTask);
        }
        return coTaskList;
    }
    @Override
    public OperationResponse insertAccessStatic(String userId, String account, String domain, String url, String describe, String ip) {
        return allServices.getiAccessStatisic().insertAccessStatic(userId, account,domain,url,describe,ip);
    }

    @Override
    public OperationResponse fileUpload( String fileName, byte[] byteStream) {
        File file = new File("E:\\"+fileName);
        try {
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(byteStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
