package webService.opImpl;

import entity.*;
import entity.databaseEntity.Node;
import webService.operator.ITaskManage;
import service.AllServices;
import java.util.List;

/**
 * Created by winter on 2014/6/15.
 */
public class TaskManageImpl implements ITaskManage {
    private AllServices allServices;

    public AllServices getAllServices() {
        return allServices;
    }

    public void setAllServices(AllServices allServices) {
        this.allServices = allServices;
    }

    @Override
    public List<TaskInfo> getMyColTask(String userId) {
        List<TaskInfo> taskInfoList = allServices.getTaskService().getMyColTaskInfo(userId);
        return taskInfoList;
    }

    @Override
    public List<PersonalTaskInfo> getAllMyTask(String userId) {
        List<PersonalTaskInfo> personalTaskInfos = allServices.getTaskService().getAllMyTaskInfo(userId);
        return personalTaskInfos;
    }

    @Override
    public TaskInfo getTaskInfo(String taskId) {
        TaskInfo taskInfo = allServices.getTaskService().getTaskInfo(taskId);
        return taskInfo;
    }

    @Override
    public Node getPersonalTaskRootNode(String perTaskId) {

        Node rootNode = allServices.getTaskService().getPersonalTaskRootNode(perTaskId);
        return rootNode;
    }
    @Override
    //添加或者更新任务信息
    public TaskInfo addTaskInfo(String userId,TaskInfo taskInfo) {
        taskInfo = allServices.getTaskService().saveTask(userId,taskInfo);
        return taskInfo;
    }
    @Override
    public NodeInfo deleteTaskInfo(String userId ,String delTaskId) {
        NodeInfo nodeInfo = allServices.getTaskService().deleteTask(userId,delTaskId);
        return nodeInfo;
    }

    @Override
    public NodeInfo deletePersonalTaskInfo(String userId, String delTaskId) {
        NodeInfo nodeInfo = allServices.getTaskService().deletePersonalTask(userId,delTaskId);
        return nodeInfo;
    }

    @Override
    public boolean isTaskCreater(String userId, String taskId) {
        return allServices.getTaskService().isTaskCreater(userId,taskId);
    }
}
