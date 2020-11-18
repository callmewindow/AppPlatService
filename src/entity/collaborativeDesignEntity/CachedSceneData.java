package entity.collaborativeDesignEntity;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winter on 2014/11/28.
 */

public class CachedSceneData {
    List<CollaUser> collaUserList = new ArrayList<CollaUser>();
    ToolInfo toolInfo;
    Boolean isDirty = false;
    private String cachedSceneJsonData = new String();
    private String lockIdList = new String();

    public String getLockIdList() {
        return lockIdList;
    }

    public void setLockIdList(String lockIdList) {
        this.lockIdList = lockIdList;
    }
    public void CachedSceneData(String cachedSceneJsonData ,ToolInfo toolInfo){
        try {
            this.cachedSceneJsonData = cachedSceneJsonData;
            this.toolInfo = toolInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCachedSceneJsonData() {
        return cachedSceneJsonData;
    }

    public void setCachedSceneJsonData(String cachedSceneJsonData) {
        this.cachedSceneJsonData = cachedSceneJsonData;
    }

    public List<CollaUser> getCollaUserList() {
        return collaUserList;
    }

    public void setCollaUserList(List<CollaUser> collaUserList) {
        this.collaUserList = collaUserList;
    }

    public ToolInfo getToolInfo() {
        return toolInfo;
    }

    public void setToolInfo(ToolInfo toolInfo) {
        this.toolInfo = toolInfo;
    }

    public void setIsDirty(Boolean isDirty) {
        this.isDirty = isDirty;
    }

    public Boolean getIsDirty() {
        return isDirty;
    }
}