package entity;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by winter on 2014/6/15.
 */
//返回给前台的任务信息类
public class TaskInfo {
    private String taskId;
    private String taskName;
    private String startTime;
    private String simulationStartTime;
    private String simulationEndTime;
    private String description;
    private String creator;
    private String creatorAccount;
    private List<String> taskUserAccounts;
    private List<String> processes;
    private int step;
    private boolean isOK ;
    private String errMsg;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getSimulationStartTime() {
        return simulationStartTime;
    }

    public void setSimulationStartTime(String simulationStartTime) {
        this.simulationStartTime = simulationStartTime;
    }

    public String getSimulationEndTime() {
        return simulationEndTime;
    }

    public void setSimulationEndTime(String simulationEndTime) {
        this.simulationEndTime = simulationEndTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<String> getProcesses() {
        return processes;
    }

    public void setProcesses(List<String> processes) {
        this.processes = processes;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorAccount() {
        return creatorAccount;
    }

    public void setCreatorAccount(String creatorAccount) {
        this.creatorAccount = creatorAccount;
    }

    public List<String> getTaskUserAccounts() {
        return taskUserAccounts;
    }

    public void setTaskUserAccounts(List<String> taskUserAccounts) {
        this.taskUserAccounts = taskUserAccounts;
    }

    public boolean getIsOK() {
        return isOK;
    }
    public void setIsOK(boolean isOK) {
        this.isOK = isOK;
    }

    public String getErrMsg() {
        return errMsg;
    }
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
