package entity.collaborativeDesignEntity;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * Created by winter on 2014/11/28.
 */
public class CollaUser {
    private String userId;
    private Boolean isOperating;

    public CollaUser(String userId, Boolean isOperating){
        this.userId = userId;
        this.isOperating = isOperating;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getIsOperating() {
        return isOperating;
    }

    public void setIsOperating(Boolean isOperating) {
        this.isOperating = isOperating;
    }
}
