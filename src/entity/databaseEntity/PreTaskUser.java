package entity.databaseEntity;

/**
 * Created by winter on 2014/6/28.
 */
public class PreTaskUser {
    private String preTaskUserId;
    private String userMailbox;
    private Integer isPassed;
    private Task taskByTaskId;

    public String getPreTaskUserId() {
        return preTaskUserId;
    }

    public void setPreTaskUserId(String preTaskUserId) {
        this.preTaskUserId = preTaskUserId;
    }

    public String getUserMailbox() {
        return userMailbox;
    }

    public void setUserMailbox(String userMailbox) {
        this.userMailbox = userMailbox;
    }

    public Integer getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(Integer isPassed) {
        this.isPassed = isPassed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PreTaskUser that = (PreTaskUser) o;

        if (isPassed != null ? !isPassed.equals(that.isPassed) : that.isPassed != null) return false;
        if (preTaskUserId != null ? !preTaskUserId.equals(that.preTaskUserId) : that.preTaskUserId != null)
            return false;
        if (userMailbox != null ? !userMailbox.equals(that.userMailbox) : that.userMailbox != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = preTaskUserId != null ? preTaskUserId.hashCode() : 0;
        result = 31 * result + (userMailbox != null ? userMailbox.hashCode() : 0);
        result = 31 * result + (isPassed != null ? isPassed.hashCode() : 0);
        return result;
    }

    public Task getTaskByTaskId() {
        return taskByTaskId;
    }

    public void setTaskByTaskId(Task taskByTaskId) {
        this.taskByTaskId = taskByTaskId;
    }
}
