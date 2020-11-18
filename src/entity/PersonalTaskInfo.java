
package entity;

import java.sql.Timestamp;

/**
 * Created by winter on 2014/8/28.
 */
public class PersonalTaskInfo {
    private String perTaskId;
    private String perTaskName;
    private String taskType;
    private String recentlyModified;
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

    public String getRecentlyModified() {
        return recentlyModified;
    }

    public void setRecentlyModified(String recentlyModified) {
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
