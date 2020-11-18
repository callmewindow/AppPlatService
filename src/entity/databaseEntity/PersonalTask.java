
package entity.databaseEntity;

import java.sql.Timestamp;

/**
 * Created by winter on 2014/8/27.
 */
public class PersonalTask {
    private String perTaskId;
    private String perTaskName;
    private String taskType;
    private Timestamp recentlyModified;
    private Byte isSaved;
    private String userId;
    private String taskDirPath;

    public String getPerTaskId() {
        return perTaskId;
    }

    public void setPerTaskId(String perTaskId) {
        this.perTaskId = perTaskId;
    }

    public String getPerTaskName() {
        return perTaskName;
    }

    public void setPerTaskName(String perTaskName) {
        this.perTaskName = perTaskName;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Timestamp getRecentlyModified() {
        return recentlyModified;
    }

    public void setRecentlyModified(Timestamp recentlyModified) {
        this.recentlyModified = recentlyModified;
    }

    public Byte getIsSaved() {
        return isSaved;
    }

    public void setIsSaved(Byte isSaved) {
        this.isSaved = isSaved;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskDirPath() {
        return taskDirPath;
    }

    public void setTaskDirPath(String taskDirPath) {
        this.taskDirPath = taskDirPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonalTask that = (PersonalTask) o;

        if (isSaved != null ? !isSaved.equals(that.isSaved) : that.isSaved != null) return false;
        if (perTaskId != null ? !perTaskId.equals(that.perTaskId) : that.perTaskId != null) return false;
        if (perTaskName != null ? !perTaskName.equals(that.perTaskName) : that.perTaskName != null) return false;
        if (recentlyModified != null ? !recentlyModified.equals(that.recentlyModified) : that.recentlyModified != null)
            return false;
        if (taskType != null ? !taskType.equals(that.taskType) : that.taskType != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = perTaskId != null ? perTaskId.hashCode() : 0;
        result = 31 * result + (perTaskName != null ? perTaskName.hashCode() : 0);
        result = 31 * result + (taskType != null ? taskType.hashCode() : 0);
        result = 31 * result + (recentlyModified != null ? recentlyModified.hashCode() : 0);
        result = 31 * result + (isSaved != null ? isSaved.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}