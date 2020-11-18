package service;
/*
 *任务管理
 * Created by winter on 2014/6/15.
 */

import buaa.edu.global.AppPlatConstant;
import buaa.edu.global.AppPlatFunction;
import buaa.edu.global.IDGeneratorUtil;
import buaa.edu.sortEntity.CompTask;
import dao.AllDAO;
import entity.*;
import entity.databaseEntity.*;
import entity.databaseEntity.Process;
import java.sql.Timestamp;
import java.util.*;
import java.util.Collection;
import java.util.List;


public class TaskService {
    private AllDAO allDAO;
    private AllServices allServices;

    public AllDAO getAllDAO() {
        return allDAO;
    }

    public void setAllDAO(AllDAO allDAO) {
        this.allDAO = allDAO;
    }

    public AllServices getAllServices() {
        return allServices;
    }

    public void setAllServices(AllServices allServices) {
        this.allServices = allServices;
    }

    public List<Task> getMyColTask(String userId) {
        User user = allDAO.getUserDAO().findById(userId);
        List<Task> taskList = new ArrayList<Task>();
        List<TaskUser> taskUsers = (List<TaskUser>)allDAO.getTaskUserDAO().findByAccount(user.getUserAccount());
        for(int i = 0; i< taskUsers.size();i++){
            taskList.add(taskUsers.get(i).getTaskByTaskId());
        }
        CompTask cmt = new CompTask();
        Collections.sort(taskList, cmt);
        return taskList;
    }

    public List<TaskInfo> getMyColTaskInfo(String userId) {
        List<Task> taskList = getMyColTask(userId);
        List<TaskInfo> taskInfoList = new ArrayList<TaskInfo>();
        TaskInfo nullTaskInfo = new TaskInfo();//界面中列表的第一个不是任务信息，因此用空任务填充
        taskInfoList.add(nullTaskInfo);
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setTaskId(task.getTaskId());
            taskInfo.setStep(task.getStep());
            taskInfo.setDescription(task.getDescription());
            taskInfo.setTaskName(task.getTaskName());
            taskInfo.setStartTime(timeWithoutNano(task.getStartTime().toString()));
            taskInfo.setSimulationStartTime(timeWithoutNano(task.getSimulationStartTime().toString()));
            taskInfo.setSimulationEndTime(timeWithoutNano(task.getSimulationEndTime().toString()));
            Collection<TaskProcess> taskProcesses = task.getTaskProcessesByTaskId();
            Object[] taskProcessArray = taskProcesses.toArray();
            List<String> processString = new ArrayList<String>();
            if (taskProcesses != null && taskProcesses.size() != 0) {
                for (int j = 0; j < taskProcessArray.length; j++) {
                    TaskProcess taskProcess = (TaskProcess) taskProcessArray[j];
                    Process p = taskProcess.getProcessByProcessId();
                    processString.add(p.getProcessName());
                }
            }
            Collection<TaskUser> taskUserList = task.getTaskUsersByTaskId();
            Object[] taskUserArray = taskUserList.toArray();
            List<String> userString = new ArrayList<String>();
            if (taskUserList.size() > 0) {
                for (int j = 0; j < taskUserArray.length; j++) {
                    TaskUser taskUser = (TaskUser) taskUserArray[j];
                    if (taskUser.getIsCreater() == 1) {
                        List<User> users= (List<User>)allDAO.getUserDAO().findByUserAccount(taskUser.getUserAccount());
                        taskInfo.setCreator(users.get(0).getUserName());
                    }
                }
            }
            taskInfo.setProcesses(processString);
            taskInfoList.add(taskInfo);
        }
        return taskInfoList;
    }

    public List<Task> getAllTask() {
        List<Task> taskList = allDAO.getTaskDAO().findAll();
        return taskList;
    }

    public List<PersonalTaskInfo> getAllMyTaskInfo(String userId) {
        List<PersonalTaskInfo> personalTaskInfos = new ArrayList<PersonalTaskInfo>();
        //添加协同任务
        List<Task> colTasks = getMyColTask(userId);
        for(int i = 0; i < colTasks.size(); i++){
            PersonalTaskInfo anTaskInfo = new PersonalTaskInfo();
            Task task = colTasks.get(i);
            anTaskInfo.setPerTaskId(task.getTaskId());
            anTaskInfo.setTaskDirPath("");
            anTaskInfo.setIsSaved(AppPlatConstant.IS_PER_TASK_SAVED);
            anTaskInfo.setPerTaskName(task.getTaskName());
            anTaskInfo.setRecentlyModified(task.getRecentlyModified().toLocaleString());
            anTaskInfo.setTaskType(AppPlatConstant.TASK_TYPE_COLLABORATIVE_ARGUMENT);
            anTaskInfo.setUserId(userId);
            personalTaskInfos.add(anTaskInfo);
        }
        //添加单项任务
        List<PersonalTask> personalTasks = allDAO.getPersonalTaskDAO().findByUserId(userId);
        for (int i = 0; i < personalTasks.size(); i++) {
            PersonalTaskInfo personalTaskInfo = new PersonalTaskInfo();
            personalTaskInfo.setPerTaskId(personalTasks.get(i).getPerTaskId());
            personalTaskInfo.setTaskDirPath(personalTasks.get(i).getTaskDirPath());
            personalTaskInfo.setIsSaved(personalTasks.get(i).getIsSaved());
            personalTaskInfo.setPerTaskName(personalTasks.get(i).getPerTaskName());
            personalTaskInfo.setRecentlyModified(personalTasks.get(i).getRecentlyModified().toLocaleString());
            personalTaskInfo.setTaskType(personalTasks.get(i).getTaskType());
            personalTaskInfo.setUserId(personalTasks.get(i).getUserId());
            personalTaskInfos.add(personalTaskInfo);
        }
        return personalTaskInfos;
    }
    public TaskInfo getTaskInfo(String taskId) {
        Task task = allDAO.getTaskDAO().findById(taskId);
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTaskId(task.getTaskId());
        taskInfo.setStep(task.getStep());
        taskInfo.setDescription(task.getDescription());
        taskInfo.setTaskName(task.getTaskName());
        taskInfo.setStartTime(timeWithoutNano(task.getStartTime().toString()));
        taskInfo.setSimulationStartTime(timeWithoutNano(task.getSimulationStartTime().toString()));
        taskInfo.setSimulationEndTime(timeWithoutNano(task.getSimulationEndTime().toString()));
        Collection<TaskProcess> taskProcesses = task.getTaskProcessesByTaskId();
        Object[] taskProcessArray = taskProcesses.toArray();
        List<String> processString = new ArrayList<String>();
        if (taskProcesses != null && taskProcesses.size() != 0) {
            for (int i = 0; i < taskProcessArray.length; i++) {
                TaskProcess taskProcess = (TaskProcess) taskProcessArray[i];
                Process p = taskProcess.getProcessByProcessId();
                processString.add(p.getProcessName());
            }
        }
        taskInfo.setProcesses(processString);
        Collection<TaskUser> taskUserList = task.getTaskUsersByTaskId();
        Object[] taskUserArray = taskUserList.toArray();
        List<String> taskUserAccountString = new ArrayList<String>();
        if (taskUserList != null && taskUserList.size() != 0) {
            for (int i = 0; i < taskUserArray.length; i++) {
                TaskUser taskUser = (TaskUser) taskUserArray[i];
                if(taskUser.getIsCreater() == 1){
                    taskInfo.setCreatorAccount(taskUser.getUserAccount());
                }
                else if(taskUser.getIsCreater() != 1){
                    taskUserAccountString.add(taskUser.getUserAccount());
                }
            }
        }
        taskInfo.setTaskUserAccounts(taskUserAccountString);
        return taskInfo;
    }

    public Node getPersonalTaskRootNode(String perTaskId) {
        PersonalTask personalTask = allDAO.getPersonalTaskDAO().findById(perTaskId);
        if (personalTask != null) {
            List<Node> nodes = allDAO.getNodeDAO().findByDataId(personalTask.getPerTaskId());
            if (nodes.size() == 0) {//因为在结构设计中并没有任务树，因此这种情况说明这个任务是结构设计任务
                Node node = new Node();
                node.setId(personalTask.getPerTaskId());
                node.setName(personalTask.getPerTaskName());
                return node;
            }
            return nodes.get(0);
        }
        return null;
    }
    public List<String> getAllSensorsBySolutionId(String solutionId){
       List<Node> nodeList =  allDAO.getNodeDAO().findByParentId(solutionId);
       List<String> sensorNameList = new ArrayList<String>();
       if(nodeList.size() > 0){
           for(int i = 0; i < nodeList.size(); i++){
               if(nodeList.get(i).getNodeType().equals(AppPlatConstant.NODETYPE_SATELLITE)){
                   Node satelliteNode = nodeList.get(i);
                   List<Node> nodes = allDAO.getNodeDAO().findByParentId(satelliteNode.getId());
                   if(nodes.size() > 0){
                       for(int j = 0 ; j < nodes.size() ; j++){
                           if(nodes.get(j).getNodeType().equals(AppPlatConstant.NODETYPE_SENSOR)){
                               sensorNameList.add(nodes.get(j).getName());
                           }
                       }
                   }
               }
           }
       }
        return sensorNameList;
    };
    public List<Node> getCoverAnaNodeBySolutionId(String solutionId){
        List<Node> nodeList =  allDAO.getNodeDAO().findByParentId(solutionId);
        List<Node> targetNodeList = new ArrayList<Node>();
        if(nodeList.size() > 0){
            for(int i = 0; i < nodeList.size(); i++){
                if(nodeList.get(i).getNodeType().equals(AppPlatConstant.NODETYPE_SATELLITE)){
                    targetNodeList.add(nodeList.get(i));
                    Node satelliteNode = nodeList.get(i);
                    List<Node> nodes = allDAO.getNodeDAO().findByParentId(satelliteNode.getId());
                    if(nodes.size() > 0){
                        for(int j = 0 ; j < nodes.size() ; j++){
                            if(nodes.get(j).getNodeType().equals(AppPlatConstant.NODETYPE_SENSOR)){
                                targetNodeList.add(nodes.get(j));
                            }
                        }
                    }
                }else if(nodeList.get(i).getNodeType().equals(AppPlatConstant.NODETYPE_COVER_ANALYSIS)){
                    Node coverAnalysisNode = nodeList.get(i);
                    List<Node> nodes = allDAO.getNodeDAO().findByParentId(coverAnalysisNode.getId());
                    if(nodes.size() > 0){
                        for(int k = 0 ; k < nodes.size() ; k++){
                            if(nodes.get(k).getNodeType().equals(AppPlatConstant.NODETYPE_GROUNDSTATION)||nodes.get(k).getNodeType().equals(AppPlatConstant.NODETYPE_SKY_COVER_POINT)||nodes.get(k).getNodeType().equals(AppPlatConstant.NODETYPE_SKY_COVER_REGIN)){
                                targetNodeList.add(nodes.get(k));
                            }
                        }
                    }
                }
            }
        }
        return targetNodeList;
    }

    public String toTimeString(String str) {
        int dot1 = str.indexOf('T');
        String str1 = str.substring(0, dot1);
        String str2 = str.substring(dot1 + 1, str.length());
        return str1 + " " + str2;
    }

    public String timeWithoutNano(String timestamp) {
        int dot1 = timestamp.indexOf('.');
        return timestamp.substring(0, dot1);
    }

    public TaskInfo saveTask(String userId, TaskInfo taskInfo) {
        if (!AppPlatFunction.isNullOrEmpty(taskInfo.getTaskId())) {
            //更新任务信息
            Task task = allDAO.getTaskDAO().findById(taskInfo.getTaskId());
            task.setTaskName(taskInfo.getTaskName());
            task.setDescription(taskInfo.getDescription());
            Timestamp simulationEndTime = Timestamp.valueOf(toTimeString(taskInfo.getSimulationEndTime()));
            task.setSimulationEndTime(simulationEndTime);
            Timestamp simulationStartTime = Timestamp.valueOf(toTimeString(taskInfo.getSimulationStartTime()));
            task.setSimulationStartTime(simulationStartTime);
            Timestamp startTime = Timestamp.valueOf(toTimeString(taskInfo.getStartTime()));
            task.setStartTime(startTime);
            task.setStep(taskInfo.getStep());
            allDAO.getTaskDAO().merge(task);
            updateProcess(taskInfo, task);
            updateTaskUser(taskInfo, task);
        } else {//添加任务信息
            Task task = new Task();
            task.setTaskName(taskInfo.getTaskName());
            task.setDescription(taskInfo.getTaskName());
            String s = toTimeString(taskInfo.getSimulationEndTime());
            Timestamp simulationEndTime = Timestamp.valueOf(toTimeString(taskInfo.getSimulationEndTime()));
            task.setSimulationEndTime(simulationEndTime);
            Timestamp simulationStartTime = Timestamp.valueOf(toTimeString(taskInfo.getSimulationStartTime()));
            task.setSimulationStartTime(simulationStartTime);
            Timestamp startTime = Timestamp.valueOf(toTimeString(taskInfo.getStartTime()));
            task.setRecentlyModified(new Timestamp(System.currentTimeMillis()));
            task.setRecentlyModified(startTime);
            task.setStartTime(startTime);
            task.setStep(taskInfo.getStep());
            allDAO.getTaskDAO().save(task);
            //添加配置好的流程
            List<String> processesString = taskInfo.getProcesses();
            List<Process> processes = allDAO.getProcessDAO().findAll();
            List<Process> taskProcesses = addProcessIfNot(processesString);
            if(taskProcesses !=null){
                for (int i = 0; i < taskProcesses.size(); i++) {
                    TaskProcess taskProcess = new TaskProcess();
                    taskProcess.setProcessByProcessId(taskProcesses.get(i));
                    taskProcess.setTaskByTaskId(task);
                    taskProcess.setIsSubmit(AppPlatConstant.PROCESS_NOT_SUBMIT);
                    allDAO.getTaskProcessDAO().save(taskProcess);
                }
            }
            //添加用户
            List<String> taskUsersString = taskInfo.getTaskUserAccounts();
            if(taskUsersString !=null){
                for(int i =0; i < taskUsersString.size(); i++){
                    TaskUser taskUser = new TaskUser();
                    taskUser.setUserAccount(taskUsersString.get(i));
                    taskUser.setIsCreater(AppPlatConstant.IS_NOT_CREATER);
                    taskUser.setTaskByTaskId(task);
                    allDAO.getTaskUserDAO().save(taskUser);
                }
            }

            taskInfo.setTaskId(task.getTaskId());
            Boolean isAdd = addTaskNode(userId, task);//新建任务时在数据库中添加任务节点，在存储平台创建任务目录
            taskInfo.setIsOK(isAdd);
            //如果任务文件夹存储到云平台失败，那么删除之前存在数据库中的数据
            if (!taskInfo.getIsOK()) {
                allDAO.getTaskDAO().delete(task);
            } else {
                TaskUser taskUser = new TaskUser();
                User user = allDAO.getUserDAO().findById(userId);
                taskUser.setUserAccount(user.getUserAccount());
                taskUser.setTaskByTaskId(task);
                taskUser.setIsCreater(AppPlatConstant.IS_CREATER);
                allDAO.getTaskUserDAO().save(taskUser);
            }
        }
        return taskInfo;
    }

    public void updateProcess(TaskInfo taskInfo, Task task) {
        List<String> processesString = taskInfo.getProcesses();
        Collection<TaskProcess> taskProcessList = task.getTaskProcessesByTaskId();
        Object[] objectArray = taskProcessList.toArray();
        List<Process> processList = addProcessIfNot(processesString);
        //增加新添加的流程
        for (int i = 0; i < processList.size(); i++) {
            String processName = processList.get(i).getProcessName();
            Boolean isHas = false;
            for (int j = 0; j < objectArray.length; j++) {
                TaskProcess taskProcess = (TaskProcess) objectArray[j];
                if (taskProcess.getProcessByProcessId().getProcessName().equals(processName)) {
                    isHas = true;
                    break;
                }
            }
            if (!isHas) {
                TaskProcess tp = new TaskProcess();
                tp.setTaskByTaskId(task);
                tp.setProcessByProcessId(processList.get(i));
                tp.setIsSubmit(AppPlatConstant.PROCESS_NOT_SUBMIT);
                allDAO.getTaskProcessDAO().save(tp);
            }
        }
        //删除要删除的流程
        for (int i = 0; i < objectArray.length; i++) {
            TaskProcess taskProcess = (TaskProcess) objectArray[i];
            Boolean isHas = false;
            for (int j = 0; j < processList.size(); j++) {
                if (taskProcess.getProcessByProcessId().getProcessName().equals(processList.get(j).getProcessName())) {
                    isHas = true;
                    break;
                }
            }
            if (!isHas) {
                allDAO.getTaskProcessDAO().delete(taskProcess);
            }
        }
    }
    public void updateTaskUser(TaskInfo taskInfo, Task task) {
        List<String> taskUsersString = taskInfo.getTaskUserAccounts();
        Collection<TaskUser> taskUserList = task.getTaskUsersByTaskId();
        Object[] objectArray = taskUserList.toArray();
        if(taskUsersString !=null){
            for (int i = 0; i < taskUsersString.size(); i++) {
                String userAccount = taskUsersString.get(i);
                Boolean isHas = false;
                for (int j = 0; j < objectArray.length; j++) {
                    if (userAccount.equals(((TaskUser) objectArray[j]).getUserAccount())) {
                        isHas = true;
                        break;
                    }
                }
                if (!isHas) {
                    TaskUser taskUser = new TaskUser();
                    taskUser.setTaskByTaskId(task);
                    taskUser.setUserAccount(userAccount);
                    taskUser.setIsCreater(AppPlatConstant.IS_NOT_CREATER);
                    allDAO.getTaskUserDAO().save(taskUser);
                }
            }
            //删除要删除的用户
            for (int i = 0; i < objectArray.length; i++) {
                TaskUser tu = (TaskUser) objectArray[i];
               if(tu.getIsCreater() != 1){
                   Boolean isHas = false;
                   for (int j = 0; j < taskUsersString.size(); j++) {
                       if (tu.getUserAccount().equals(taskUsersString.get(j))) {
                           isHas = true;
                           break;
                       }
                   }
                   if (!isHas) {
                       allDAO.getTaskUserDAO().delete(tu);
                   }
               }
            }
        }
    }

    public List<Process> addProcessIfNot(List<String> processesString) {
        List<Process> processes = allDAO.getProcessDAO().findAll();
        List<Process> selectedProcesses = new ArrayList<Process>();
        for (int i = 0; i < processesString.size(); i++) {
            boolean hasProcess = false;
            for (int j = 0; j < processes.size(); j++) {
                if (processesString.get(i).equals(processes.get(j).getProcessName())) {
                    hasProcess = true;
                    selectedProcesses.add(processes.get(j));
                }
            }
            if (!hasProcess) {
                Process p = new Process();
                p.setProcessName(processesString.get(i));
                allDAO.getProcessDAO().save(p);
                selectedProcesses.add(p);
            }
        }
        return selectedProcesses;
    }

    /**
     * 向数据库中插入根节点，并在文件结构中创建根目录
     *
     * @param task
     * @return
     */
    public Boolean addTaskNode(String userId, Task task) {
        Node node = new Node();
        String nodeId = IDGeneratorUtil.getUUID(AppPlatConstant.TASK_PRE_ID);
        node.setId(nodeId);
        node.setName(task.getTaskName());
        node.setParentId(nodeId);
        node.setRootId(nodeId);
        node.setIsRoot(true);
        node.setLeaf(false);
        node.setDataId(task.getTaskId());
        node.setNodeOrder(0);
        node.setNodeType(AppPlatConstant.NODETYPE_TASK);
        node.setNodeFilePath(node.getId());
        allDAO.getNodeDAO().save(node);
        addDefaultSolutionToTask(userId, node);
        //本地存储
        String taskFileDiePath = node.getId();
        Boolean isSave = allServices.getServerStoreService().createFolder(taskFileDiePath);
        if (!isSave) {
            allDAO.getNodeDAO().delete(node);
        }
        return isSave;
    }

    public void addDefaultSolutionToTask(String userId, Node node) {
        Node solutionNode = new Node();
        solutionNode.setName("方案1");
        solutionNode.setRootId(node.getId());
        solutionNode.setParentId(node.getId());
        solutionNode.setNodeType(AppPlatConstant.NODETYPE_SOLUTION);
        solutionNode.setIsRoot(false);
        solutionNode.setLeaf(false);
        solutionNode.setNodeOrder(0);
        allServices.getSolutionService().addNode(userId, solutionNode);//添加一个方案节点
        //为该方案添加一个用来保存场景参数的文件
        String path = solutionNode.getParentId() + "/" + solutionNode.getId();
        String fileName = solutionNode.getId() + "";
        allServices.getOrbitFileService().initSolutionFile(path, fileName);
    }

    public Boolean updateTask(TaskInfo taskInfo) {
        Task task = allDAO.getTaskDAO().findById(taskInfo.getTaskId());
        task.setTaskName(taskInfo.getTaskName());
        task.setDescription(taskInfo.getDescription());
        Timestamp simulationEndTime = Timestamp.valueOf(taskInfo.getSimulationEndTime());
        task.setSimulationEndTime(simulationEndTime);
        Timestamp simulationStartTime = Timestamp.valueOf(taskInfo.getSimulationStartTime());
        task.setSimulationStartTime(simulationStartTime);
        Timestamp startTime = Timestamp.valueOf(taskInfo.getStartTime());
        task.setStartTime(startTime);
        task.setStep(taskInfo.getStep());
        allDAO.getTaskDAO().merge(task);
        return true;
    }
    public NodeInfo deleteTask(String userId, String delTaskId) {

        Task task = allDAO.getTaskDAO().findById(delTaskId);
        List<Object> objList = allDAO.getNodeDAO().findByDataId(delTaskId);
        Node delNode = (Node) objList.get(0);
        Boolean isDel = false;
        if (delNode != null) {
            isDel = allServices.getServerStoreService().deleteDir(delNode.getNodeFilePath());
            if (isDel) {
                allDAO.getTaskDAO().delete(task);
                allDAO.getNodeDAO().delete(delNode);
            }
        }
        NodeInfo nodeInfo = new NodeInfo();
        nodeInfo.setNode(delNode);
        nodeInfo.setIsOK(isDel);
        return nodeInfo;
    }

    public NodeInfo deletePersonalTask(String userId, String delTaskId) {
        PersonalTask ptask = allDAO.getPersonalTaskDAO().findById(delTaskId);
        List<Object> objList = allDAO.getNodeDAO().findByDataId(delTaskId);
        Node delNode = (Node) objList.get(0);
        Boolean isDel = false;
        isDel = allServices.getServerStoreService().deleteDir(ptask.getTaskDirPath());
        if (isDel) {
            allDAO.getPersonalTaskDAO().delete(ptask);
            allDAO.getNodeDAO().delete(delNode);
        }
        NodeInfo nodeInfo = new NodeInfo();
        nodeInfo.setIsOK(isDel);
        return nodeInfo;
    }


    public boolean isTaskCreater(String userId, String taskId) {
        Task task = allDAO.getTaskDAO().findById(taskId);
        List<TaskUser> taskUserList = allDAO.getTaskUserDAO().findByTask(task);
        if (taskUserList != null) {
            for (int i = 0; i < taskUserList.size(); i++) {
                User user =((List<User>)allDAO.getUserDAO().findByUserAccount(taskUserList.get(i).getUserAccount())).get(i);
                if (user.getUserId().equals(userId)) {
                    return true;
                }
            }
        }
        return false;
    }
}
