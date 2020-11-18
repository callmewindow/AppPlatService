package entity.databaseEntity;

import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by winter on 2014/6/28.
 */
public class Task {
    private String taskId;
    private String taskName;
    private Timestamp startTime;
    private Timestamp simulationStartTime;
    private Timestamp simulationEndTime;
    private String description;
    private Integer step;
    private Timestamp recentlyModified;
    private Collection<PreTaskUser> preTaskUsersByTaskId;
    private Collection<TaskProcess> taskProcessesByTaskId;
    private Collection<TaskUser> taskUsersByTaskId;

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

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getSimulationStartTime() {
        return simulationStartTime;
    }

    public void setSimulationStartTime(Timestamp simulationStartTime) {
        this.simulationStartTime = simulationStartTime;
    }

    public Timestamp getSimulationEndTime() {
        return simulationEndTime;
    }

    public void setSimulationEndTime(Timestamp simulationEndTime) {
        this.simulationEndTime = simulationEndTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Timestamp getRecentlyModified() {
        return recentlyModified;
    }

    public void setRecentlyModified(Timestamp recentlyModified) {
        this.recentlyModified = recentlyModified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (description != null ? !description.equals(task.description) : task.description != null) return false;
        if (simulationEndTime != null ? !simulationEndTime.equals(task.simulationEndTime) : task.simulationEndTime != null)
            return false;
        if (simulationStartTime != null ? !simulationStartTime.equals(task.simulationStartTime) : task.simulationStartTime != null)
            return false;
        if (startTime != null ? !startTime.equals(task.startTime) : task.startTime != null) return false;
        if (step != null ? !step.equals(task.step) : task.step != null) return false;
        if (taskId != null ? !taskId.equals(task.taskId) : task.taskId != null) return false;
        if (taskName != null ? !taskName.equals(task.taskName) : task.taskName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = taskId != null ? taskId.hashCode() : 0;
        result = 31 * result + (taskName != null ? taskName.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (simulationStartTime != null ? simulationStartTime.hashCode() : 0);
        result = 31 * result + (simulationEndTime != null ? simulationEndTime.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (step != null ? step.hashCode() : 0);
        return result;
    }

    public Collection<PreTaskUser> getPreTaskUsersByTaskId() {
        return preTaskUsersByTaskId;
    }

    public void setPreTaskUsersByTaskId(Collection<PreTaskUser> preTaskUsersByTaskId) {
        this.preTaskUsersByTaskId = preTaskUsersByTaskId;
    }

    public Collection<TaskProcess> getTaskProcessesByTaskId() {
        return taskProcessesByTaskId;
    }

    public void setTaskProcessesByTaskId(Collection<TaskProcess> taskProcessesByTaskId) {
        this.taskProcessesByTaskId = taskProcessesByTaskId;
    }

    public Collection<TaskUser> getTaskUsersByTaskId() {
        return taskUsersByTaskId;
    }

    public void setTaskUsersByTaskId(Collection<TaskUser> taskUsersByTaskId) {
        this.taskUsersByTaskId = taskUsersByTaskId;
    }
}
