package entity.databaseEntity;

import java.lang.*;

/**
 * Created by winter on 2014/6/28.
 */
public class TaskProcess {
    private String taskProcessId;
    private Byte isSubmit;
    private entity.databaseEntity.Process processByProcessId;
    private Task taskByTaskId;

    public String getTaskProcessId() {
        return taskProcessId;
    }

    public void setTaskProcessId(String taskProcessId) {
        this.taskProcessId = taskProcessId;
    }

    public Byte getIsSubmit() {
        return isSubmit;
    }

    public void setIsSubmit(Byte isSubmit) {
        this.isSubmit = isSubmit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskProcess that = (TaskProcess) o;

        if (isSubmit != null ? !isSubmit.equals(that.isSubmit) : that.isSubmit != null) return false;
        if (taskProcessId != null ? !taskProcessId.equals(that.taskProcessId) : that.taskProcessId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = taskProcessId != null ? taskProcessId.hashCode() : 0;
        result = 31 * result + (isSubmit != null ? isSubmit.hashCode() : 0);
        return result;
    }

    public entity.databaseEntity.Process getProcessByProcessId() {
        return processByProcessId;
    }

    public void setProcessByProcessId(entity.databaseEntity.Process processByProcessId) {
        this.processByProcessId = processByProcessId;
    }

    public Task getTaskByTaskId() {
        return taskByTaskId;
    }

    public void setTaskByTaskId(Task taskByTaskId) {
        this.taskByTaskId = taskByTaskId;
    }
}
