package entity.databaseEntity;

import java.util.Collection;

/**
 * Created by winter on 2014/6/28.
 */
public class User {
    private String userId;
    private String userAccount;
    private String userDepartment;
    private String userDomain;
    private String userMailbox;
    private String userName;
    private String userPermission;
    private String userTelephone;
    private Collection<TaskUser> taskUsersByUserId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserDepartment() {
        return userDepartment;
    }

    public void setUserDepartment(String userDepartment) {
        this.userDepartment = userDepartment;
    }

    public String getUserDomain() {
        return userDomain;
    }

    public void setUserDomain(String userDomain) {
        this.userDomain = userDomain;
    }

    public String getUserMailbox() {
        return userMailbox;
    }

    public void setUserMailbox(String userMailbox) {
        this.userMailbox = userMailbox;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPermission() {
        return userPermission;
    }

    public void setUserPermission(String userPermission) {
        this.userPermission = userPermission;
    }

    public String getUserTelephone() {
        return userTelephone;
    }

    public void setUserTelephone(String userTelephone) {
        this.userTelephone = userTelephone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userAccount != null ? !userAccount.equals(user.userAccount) : user.userAccount != null) return false;
        if (userDepartment != null ? !userDepartment.equals(user.userDepartment) : user.userDepartment != null)
            return false;
        if (userDomain != null ? !userDomain.equals(user.userDomain) : user.userDomain != null) return false;
        if (userId != null ? !userId.equals(user.userId) : user.userId != null) return false;
        if (userMailbox != null ? !userMailbox.equals(user.userMailbox) : user.userMailbox != null) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        if (userPermission != null ? !userPermission.equals(user.userPermission) : user.userPermission != null)
            return false;
        if (userTelephone != null ? !userTelephone.equals(user.userTelephone) : user.userTelephone != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (userAccount != null ? userAccount.hashCode() : 0);
        result = 31 * result + (userDepartment != null ? userDepartment.hashCode() : 0);
        result = 31 * result + (userDomain != null ? userDomain.hashCode() : 0);
        result = 31 * result + (userMailbox != null ? userMailbox.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userPermission != null ? userPermission.hashCode() : 0);
        result = 31 * result + (userTelephone != null ? userTelephone.hashCode() : 0);
        return result;
    }

    public Collection<TaskUser> getTaskUsersByUserId() {
        return taskUsersByUserId;
    }

    public void setTaskUsersByUserId(Collection<TaskUser> taskUsersByUserId) {
        this.taskUsersByUserId = taskUsersByUserId;
    }
}
