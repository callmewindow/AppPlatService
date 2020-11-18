package entity.databaseEntity;

import java.util.List;

/**
 * Created by winter on 2014/6/28.
 */
public class TaskUser {
    private String taskUserId;
    private Byte isCreater;
    private String userAccount;
    private Task taskByTaskId;

    public String getTaskUserId() {
        return taskUserId;
    }

    public void setTaskUserId(String taskUserId) {
        this.taskUserId = taskUserId;
    }

    public Byte getIsCreater() {
        return isCreater;
    }

    public void setIsCreater(Byte isCreater) {
        this.isCreater = isCreater;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskUser taskUser = (TaskUser) o;

        if (isCreater != null ? !isCreater.equals(taskUser.isCreater) : taskUser.isCreater != null) return false;
        if (taskUserId != null ? !taskUserId.equals(taskUser.taskUserId) : taskUser.taskUserId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = taskUserId != null ? taskUserId.hashCode() : 0;
        result = 31 * result + (isCreater != null ? isCreater.hashCode() : 0);
        return result;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public Task getTaskByTaskId() {
        return taskByTaskId;
    }

    public void setTaskByTaskId(Task taskByTaskId) {
        this.taskByTaskId = taskByTaskId;
    }
}
